package tradeproj

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory


class AdminController {
    def exportService
    def adminService

    def index() {}

    def downLoadTradeExcel = {

        List fields = ["author", "title"]
        Map labels = ["author": "Author", "title": "Title"]
        Map dataMap = ["author": "a", "title": "ttttt"]
        List data = []
        data.add(dataMap)
        response.setHeader("Content-disposition", "attachment; filename=TestReport.csv")
        exportService.export('csv', response.outputStream, data, fields, labels, new HashMap(), new HashMap())
    }

    def uploadTradeExcel = {
        try {
            def file = request.getFile('file')
            List<Trade> trades = adminService.processTradeData(file)
            render(view: "trade",  model: [tradeList:trades])
            return false
        } catch (Exception e) {
            e.printStackTrace()
            println("exiting")
        }

        //redirect(action: 'index')
        return false
    }

    def showUpload = {
        [data: [:]]
    }
}
