package dataProvider;

import listenerUtils.ReporterManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;


public class ExcelDataProvider extends ReporterManager {

    /*private static final Logger logger = LogManager.getLogger(ExcelDataProvider.class);
    private static final String FILE_PATH = "path/to/your/excel/file.xlsx";
    private static final String SHEET_NAME = "Sheet1";

    public Object[][] getExcelData() {
        Object[][] data = null;
        try (FileInputStream file = new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            int rowCount = sheet.getPhysicalNumberOfRows();
            data = new Object[rowCount - 1][];
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                int colCount = row.getLastCellNum();
                data[i - 1] = new Object[colCount];
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (cell.getCellType()) {
                        case STRING:
                            data[i - 1][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                data[i - 1][j] = cell.getDateCellValue();
                            } else {
                                data[i - 1][j] = cell.getNumericCellValue();
                            }
                            break;
                        case BOOLEAN:
                            data[i - 1][j] = cell.getBooleanCellValue();
                            break;
                        default:
                            data[i - 1][j] = cell.getStringCellValue();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }*/
}
