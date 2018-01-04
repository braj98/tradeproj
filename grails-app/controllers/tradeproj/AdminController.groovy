package tradeproj

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory


class AdminController {
    def exportService
    def adminService
    static Map map = new HashMap()

    def index() {}

    def downLoadTradeExcel = {

        /**
         *
         * <th>trade Date</th>
         <th>exchange</th>
         <th>type</th>
         <th>qty</th>
         <th>rate</th>
         <th>rate</th>
         <th>order</th>
         <th>trade</th>
         <th>comment</th>
         <th>avgPrice</th>
         */

        List fields=['tradeDate', 'exchange','type','qty','rate', 'orderNo', 'tradeNo','comment', 'avgPrice']
        //Map labels=['tradeDate', 'exchange','type','qty','rate', 'order', 'trade','comment', 'avgPrice']
        List<Trade> data= map.get("map")
        response.setHeader("Content-disposition", "attachment; filename=TestReport.csv")
        exportService.export('csv', response.outputStream, data, fields, null, new HashMap(), new HashMap())
    }

    def uploadTradeExcel = {
        try {
            def file = request.getFile('file')
            List<Trade> tradeList = adminService.processTradeData(file)
            map.put("map", tradeList)
            //render(view: "trade",  model: [tradeList:tradeList])
            redirect(action: "create")
            return false
            //redirect(action: "edit")
            //return false
        } catch (Exception e) {
            e.printStackTrace()
            println("exiting")
        }

        redirect(action: 'index')
        return false
    }

    def create ={
        List<Trade> tradeList = map.get("map")
        return [tradeList:map.get("map")]
    }

    def showUpload = {
        [data: [:]]
    }
}
