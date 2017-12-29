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
            List tradeList = excelImportService.columns(workbook, CONFIG_BOOK_COLUMN_MAP)

            Map columnMap1 = tradeList.remove(0)

            println("\n-------------------------------\n")
            println("\ncolumnMap=$columnMap1 \n")
            List<Trade> tradeObjList = new ArrayList<>()
            tradeList.each {
                println(it)
                tradeObjList.add(new Trade(it))
            }
            println("\n-------------------------------\n")
            println(tradeObjList)
            println("\n-------------------------------\n")

            generateReport(tradeList)
        } catch (Exception e) {
            e.printStackTrace()
            println("exiting")
        }
    }

    def generateReport(List<Trade> tradeObjList) {
        LinkedList<Trade> buyTradeList = new LinkedList<>()
        int actualQuantity = 0
        double totalPrice = 0
        def curTradeDate = null
        tradeObjList.each {
            if (it.type == Trade.TRADETYPE.BUY.toString()) {
                buyTradeList.add(it.clone())
                totalPrice = totalPrice + (it.qty * it.rate)
                actualQuantity = actualQuantity + it.qty
            } else {
                int remainingSellQty = it.qty
                Iterator itr = buyTradeList.iterator()
                while (buyTradeList.size()>0 && remainingSellQty > 0) {
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
                        buyTradeList.set(0,t)
                        remainingSellQty = remainingSellQty - remainingSellQty
                        println("$actualQuantity $remainingSellQty $totalPrice")
                    }
                }
            }
            it.avgPrice = totalPrice / actualQuantity
            it.comment = "net qty=$actualQuantity, net price=$totalPrice, avg price=${it.avgPrice}"
            println("avg=${it.avgPrice} actqty = ${it.netQuantity}")
        }
        println("\n-------------Report Summary------------------\n")
        tradeObjList.each { println(it) }
        println("\n-------------------------------\n")
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
