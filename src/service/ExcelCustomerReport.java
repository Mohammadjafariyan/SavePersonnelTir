package service;

import main.java.models.ExportTirTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExcelCustomerReport {


    private String path = "";


    public ExcelCustomerReport(String path) {

        this.path = path;//System.getProperty("user.dir")+File.pathSeparator+"ExcelFiles";
    }

    public void exportTable(JTable table, String fileName) throws IOException {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) file = file;
        else
            file.createNewFile();

       /* ExportTirTableModel model = (ExportTirTableModel)table.getModel();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        Object[][] datatypes = model.rowData;

        int rowNum = 0;
        System.out.println("Creating excel");

        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        System.out.println("Done");



    }
}
