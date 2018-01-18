package tradeproj

import grails.transaction.Transactional
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory

@Transactional
class AdminService {
    def excelImportService

    static Map CONFIG_BOOK_COLUMN_MAP = [
            sheet    : 'TRADEBOOK',
            startRow : 11,
            columnMap: [
                    'B': 'tradeDate',
                    'C': 'tradeTime',
                    'D': 'exchange',
                    'E': 'symbol',
                    'F': 'type',
                    'G': 'qty',
                    'H': 'rate',
                    'I': 'orderNo',
                    'J': 'tradeNo'
            ]
    ]

    def processTradeData(def file) {
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream())
            List tradeListFromFile = excelImportService.columns(workbook, CONFIG_BOOK_COLUMN_MAP)

            Map columnMap1 = tradeListFromFile.remove(0)

            println("\n-------------------------------\n")
            println("\ncolumnMap=$columnMap1 \n")
            List<Trade> tradeObjList = new ArrayList<>()
            tradeListFromFile.each {
                println(it)
                tradeObjList.add(new Trade(it))
            }
            println("\n-------------------------------\n")
            println(tradeObjList)
            println("\n-------------------------------\n")
            processTrades(tradeObjList)
        } catch (Exception e) {
            e.printStackTrace()
            println("exiting")
        }
    }

    private void processTrades(ArrayList<Trade> tradeObjList) {
        def tradeObjectGroupByDate = tradeObjList.groupBy { it.tradeDate }
        List<Trade> eqTradeObjList = new ArrayList<>()
        tradeObjectGroupByDate.each { tDate, tObjectList ->
            List<Trade> tempList = tObjectList
            if (tObjectList.size() > 1) {
                tempList = getEquivalentTradeForTheDay(tObjectList)
            }
            eqTradeObjList.addAll(tempList)
        }
        println("$tradeObjectGroupByDate\n--------------GroupByDate above-----------------\n")
        //generateReport(tradeList)
        generateReport(eqTradeObjList)
    }

    private List<Trade> getEquivalentTradeForTheDay(List<Trade> tradeObjList) {
        int buyQuantity = tradeObjList.sum {
            if (it.type == Trade.TRADETYPE.BUY.toString()) {
                it.qty
            } else {
                0
            }
        }

        int sellQuantity = tradeObjList.sum {
            if (it.type == Trade.TRADETYPE.BUY.toString()) {
                0
            } else {
                it.qty
            }
        }
        println(buyQuantity)
        println(sellQuantity)

        if (buyQuantity > sellQuantity) {
            return createList(buyQuantity - sellQuantity, 'B', tradeObjList)
        } else if (sellQuantity > buyQuantity) {
            return createList(sellQuantity - buyQuantity, 'S', tradeObjList)
        } else {
            return new ArrayList<Trade>()
        }
    }

    private List<Trade> createList(int quantityToCollect, String type, List<Trade> tradeObjList) {
        List<Trade> resultList = new ArrayList<>()
        for (int i = tradeObjList.size() - 1; i >= 0 && quantityToCollect > 0; i--) {
            Trade t = tradeObjList.get(i)

            if (t.type == type) {
                if (quantityToCollect > t.qty) {

                    quantityToCollect = quantityToCollect - t.qty
                } else {
                    t.qty = quantityToCollect
                    quantityToCollect = 0
                }
                resultList.add(t)
            }
        }
        return resultList
    }

    private def generateReport(List<Trade> tradeObjList) {
        LinkedList<Trade> buyTradeList = new LinkedList<>()
        int actualQuantity = 0
        double totalPrice = 0
        def curTradeDate = null
        tradeObjList.each {
            if (curTradeDate != null && curTradeDate != it.tradeDate) {

            }
            if (it.type == Trade.TRADETYPE.BUY.toString()) {
                buyTradeList.add(it.clone())
                totalPrice = totalPrice + (it.qty * it.rate)
                actualQuantity = actualQuantity + it.qty
            } else {
                int remainingSellQty = it.qty
                Iterator itr = buyTradeList.iterator()
                while (buyTradeList.size() > 0 && remainingSellQty > 0) {
                    Trade t = buyTradeList.get(0)
                    if (t.qty <= remainingSellQty) {
                        actualQuantity = actualQuantity - t.qty
                        totalPrice = totalPrice - (t.qty * t.rate)
                        remainingSellQty = remainingSellQty - t.qty
                        buyTradeList.remove(0)
                        println("$actualQuantity $remainingSellQty $totalPrice")
                    } else {
                        println("before: $actualQuantity $remainingSellQty $totalPrice")
                        actualQuantity = actualQuantity - remainingSellQty
                        totalPrice = totalPrice - (remainingSellQty * t.rate)

                        t.setQty(t.qty - remainingSellQty)
                        buyTradeList.set(0, t)
                        remainingSellQty = remainingSellQty - remainingSellQty
                        println("$actualQuantity $remainingSellQty $totalPrice")
                    }
                }
            }
            it.avgPrice = actualQuantity != 0 ? totalPrice / actualQuantity : 0
            it.comment = "net qty=$actualQuantity, net price=$totalPrice, avg price=${it.avgPrice}"
            println("avg=${it.avgPrice} actqty = ${it.netQuantity}")
        }
        println("\n-------------Report Summary------------------\n")
        tradeObjList.each { println(it) }

        println("\n-------------------------------\n")
        return tradeObjList
    }
}

/*

static Map CONFIG_BOOK_COLUMN_MAP = [
        sheet    : 'TRADEBOOK',
        startRow : 11,
        columnMap: [
                'B': 'Trade date',
                'C': 'Trade time',
                'D': 'Exchange',
                'E': 'Symbol',
                'F': 'Type',
                'G': 'Qty',
                'H': 'Rate',
                'I': 'Order no',
                'J': 'Trade no'
        ]
]*/
