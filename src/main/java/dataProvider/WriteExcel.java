package dataProvider;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Random;

public class WriteExcel {

    //public String writeDatafromexcel() {
        public static void main(String[] args) throws Exception {
        String projectName = null;
        try {
            // Create a new Workbook
            Workbook workbook = WorkbookFactory.create(new FileInputStream("./dataSheet/getDataExcel.xlsx"));

            // Create a new Sheet
            Sheet sheet = workbook.getSheet("Test Data");

            // Create a new Row
            Row row1 = sheet.getRow(1);

            // Create new Cells
            String Project_Prefix = row1.getCell(0).getStringCellValue();

            Cell cell4 = row1.createCell(1);

            String randomNumber = RandomStringUtils.randomNumeric(5);
            projectName = Project_Prefix + randomNumber;
            // Set values for the Cells
            cell4.setCellValue(projectName);

            // Write the Workbook to a File
            FileOutputStream fileOut = new FileOutputStream(new File(".\\dataSheet\\getDataExcel.xlsx"));
            workbook.write(fileOut);
            fileOut.close();

            // Close the Workbook
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //return projectName;
    }
}