package dBUtility;

import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionSimulation {
    public static void main(String[] args) throws IOException, InterruptedException {
        String excelFilePath = "C:\\Users\\USER1\\Desktop\\Simulation\\Transaction Details.xlsx";
        String sheetName = "Transaction Details";
        //String staticJsonFilePath = "C:\\Users\\USER1\\Desktop\\Simulation\\Transaction Details.txt";

        generateAndWriteDataToExcel(excelFilePath, sheetName);

        List<String> listOfJson = createJsonListFromExcel(excelFilePath, sheetName);
        String txtFilePath = writeTextToFile(listOfJson.toString());

        System.out.println(txtFilePath);
        System.out.println(postRequest(txtFilePath));
    }

    private static void generateAndWriteDataToExcel(String filePath, String sheetName) throws IOException, InterruptedException {
        try (InputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            int tatDateColumn = findColumnIndex(sheet, "TAT DATE");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String tatDate = formatDateCellValue(row.getCell(tatDateColumn));
                String transactionId = "TR" + generateDateWithOffset(tatDate, "yyyyMMddhhmmssSSS");
                String transactionDate = generateDateWithOffset(tatDate, "dd-MM-yyyy");
                String transactionTime = generateDateWithOffset(tatDate, "hh:mm:ss:SSS");

                for (int j = 0; j < row.getLastCellNum(); j++) {
                    String cellData = formatDateCellValue(sheet.getRow(0).getCell(j));

                    switch (cellData.toUpperCase()) {
                        case "TRANSACTION ID":
                            row.getCell(j).setCellValue(transactionId);
                            break;
                        case "TRANSACTION DATE":
                            row.getCell(j).setCellValue(transactionDate);
                            break;
                        case "TRANSACTION TIME":
                            row.getCell(j).setCellValue(transactionTime);
                            break;
                    }
                }
                Thread.sleep(100);
            }

            try (OutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        }
    }

    private static List<String> createJsonListFromExcel(String excelPath, String sheetName) throws IOException {
        List<String> listOfJson = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(excelPath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Map<String, String> rowData = new LinkedHashMap<>();
                Row row = sheet.getRow(i);

                for (int j = 0; j < row.getLastCellNum(); j++) {
                    rowData.put(formatDateCellValue(sheet.getRow(0).getCell(j)), formatDateCellValue(row.getCell(j)));
                }

                String json = readTextFile(new FileInputStream("C:\\Users\\USER1\\Desktop\\Simulation\\Transaction Details.txt"));
                for (Map.Entry<String, String> entry : rowData.entrySet()) {
                    json = json.replaceAll(entry.getKey().trim(), entry.getValue().trim());
                }
                listOfJson.add(json);
            }
        }
        return listOfJson;
    }

    private static String formatDateCellValue(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    private static int findColumnIndex(Sheet sheet, String columnName) {
        Row headerRow = sheet.getRow(0);
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            if (formatDateCellValue(headerRow.getCell(i)).equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }

    private static String readTextFile(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        }
    }

    private static String writeTextToFile(String data) throws IOException {
        String directoryPath = System.getProperty("user.home") + File.separator + "Updated Json Files";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "Product_Details_JsonData_" + new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date()) + ".txt";
        File file = new File(directory, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(data);
        }
        return file.getAbsolutePath();
    }

    private static String postRequest(String filePath) {
        // Assuming these lines are somewhere in your class
        // baseURI = "http://49.249.29.5:8091";
        // return given().multiPart(new File(filePath)).post("/transactions").prettyPrint();
        return "";
    }

    private static String generateDateWithOffset(String offset, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(offset));
        return new SimpleDateFormat(format).format(calendar.getTime());
    }
}

