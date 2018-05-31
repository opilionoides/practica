package practica;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Practica {

    public static void main(String[] args) throws IOException {
        listf("C:/Profesores");
        //readXLSXFile("C:/Profesores/Notas.xlsx");
    }

    public static void readXLSXFile(String path) throws IOException {
        InputStream ExcelFileToRead = new FileInputStream(path);
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
        XSSFRow row;
        XSSFCell cell;
        XSSFSheet sheet;
        //XSSFSheet sheet = wb.getSheetAt(0);
        //wb.getNumberOfSheets();
        Iterator sheets = wb.sheetIterator();
        while (sheets.hasNext()) {
            sheet = (XSSFSheet) sheets.next();
            System.out.println(sheet.getSheetName());
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                while (cells.hasNext()) {
                    cell = (XSSFCell) cells.next();
                    if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        System.out.print(cell.getStringCellValue() + " ");
                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        System.out.print(cell.getNumericCellValue() + " ");
                    } else {
                    }
                    System.out.println("");
                }
            }
        }
    }

    public static void listf(String directoryName) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                try {
                    readXLSXFile(file.getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(Practica.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath());
            }
            System.out.println("");
        }
    }

    public static void getvalue_1() {
        XSSFRow row;
        XSSFCell cell;
        String[][] value = null;
        double[][] nums = null;
        try {
            FileInputStream inputStream = new FileInputStream("TEST.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            // get sheet number
            int sheetCn = workbook.getNumberOfSheets();
            for (int cn = 0; cn < sheetCn; cn++) {
                // get 0th sheet data
                XSSFSheet sheet = workbook.getSheetAt(cn);
                // get number of rows from sheet
                int rows = sheet.getPhysicalNumberOfRows();
                // get number of cell from row
                int cells = sheet.getRow(cn).getPhysicalNumberOfCells();
                value = new String[rows][cells];
                for (int r = 0; r < rows; r++) {
                    row = sheet.getRow(r); // bring row
                    if (row != null) {
                        for (int c = 0; c < cells; c++) {
                            cell = row.getCell(c);
                            nums = new double[rows][cells];
                            if (cell != null) {
                                switch (cell.getCellType()) {
                                    case XSSFCell.CELL_TYPE_FORMULA:
                                        value[r][c] = cell.getCellFormula();
                                        break;
                                    case XSSFCell.CELL_TYPE_NUMERIC:
                                        value[r][c] = ""
                                                + cell.getNumericCellValue();
                                        break;
                                    case XSSFCell.CELL_TYPE_STRING:
                                        value[r][c] = ""
                                                + cell.getStringCellValue();
                                        break;
                                    case XSSFCell.CELL_TYPE_BLANK:
                                        value[r][c] = "[BLANK]";
                                        break;
                                    case XSSFCell.CELL_TYPE_ERROR:
                                        value[r][c] = "" + cell.getErrorCellValue();
                                        break;
                                    default:
                                }
                                System.out.print(value[r][c]);
                            } else {
                                System.out.print("[null]\t");
                            }
                        } // for(c)
                        System.out.print("\n");
                    }
                } // for(r)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}