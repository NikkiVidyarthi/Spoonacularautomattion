package tests;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    // Method to read ingredients from the Excel sheet
    public List<String> readIngredientsFromExcel(String filePath) {
        List<String> ingredientsList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Assuming the ingredients are in the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate through the rows to read ingredients
            for (Row row : sheet) {
                Cell cell = row.getCell(0); // Assuming the ingredients are in the first column
                if (cell != null) {
                    ingredientsList.add(cell.getStringCellValue());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingredientsList;
    }
}


