package tradeproj

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory


class AdminController {
    def exportService
    def adminService
    //It is a placeholder for the file as there is no unique identity to the file.
    //will be changed at a later point of time
    static Map map = new HashMap()

    def index() {}

    def downLoadTradeExcel = {
        List fields=['tradeDate', 'exchange','type','qty','rate', 'orderNo', 'tradeNo','comment', 'avgPrice']

        List<Trade> data= map.get("map")
        response.setHeader("Content-disposition", "attachment; filename=TestReport.csv")
        exportService.export('csv', response.outputStream, data, fields, null, new HashMap(), new HashMap())
    }

    def uploadTradeExcel = {
        try {
            def file = request.getFile('file')
            List<Trade> tradeList = adminService.processTradeData(file)
            map.put("map", tradeList)
            redirect(action: "create")
            return false
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
