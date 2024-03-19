package dataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class DataInputProvider {

    public static String[][] getSheet(String dataSheetName) {

        String[][] data = null;
        writeDatafromexcel();
        try {
            FileInputStream fis = new FileInputStream(".\\dataSheet\\"+dataSheetName+".xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // get the number of rows
            int rowCount = sheet.getLastRowNum();

            // get the number of columns
            int columnCount = sheet.getRow(0).getLastCellNum();
            data = new String[rowCount][columnCount];


            // loop through the rows
            for(int i=1; i <rowCount+1; i++){
                try {
                    XSSFRow row = sheet.getRow(i);
                    for(int j=0; j <columnCount; j++){ // loop through the columns
                        try {
                            String cellValue = "";
                            try{
                                cellValue = row.getCell(j).getStringCellValue();
                            }catch(NumberFormatException e){
                                cellValue = row.getCell(j).getNumericCellValue()+ "";
                            }
                            data[i-1][j]  = cellValue; // add to the data array
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fis.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void writeDatafromexcel() {
        // public static void main(String[] args) throws Exception {
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

            String randomNumber =  RandomStringUtils.randomNumeric(5);
            String projectName=Project_Prefix + randomNumber;
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
    }
}
