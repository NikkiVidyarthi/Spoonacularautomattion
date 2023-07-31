package tests;

import datamodel.MealPlan;
import datamodel.Mealitems;
import datamodel.Recipe;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import static io.restassured.RestAssured.given;



public class MealPostRequest extends TestBase {

 public static final int MAX_DAILY_CALORIES = 2500;
 public static final int MAX_WEEKLY_CALORIES = 15000;
 public static final double BUDGET = 100;
 //public static Set<String> availableIngredients = new HashSet<>();



 @Test
 public void CreateWeeklyMealPlan() throws IOException {
  String excelFilePath = "C:\\Users\\RAGHVANDRA KUMAR\\IdeaProjects\\SpoonacularApi\\src\\test\\resources\\ReadFromExcel.xlsx";
  String sheetName = "Sheet1";


  InputStream inputStream = new FileInputStream(excelFilePath);
  Workbook workbook = new XSSFWorkbook(inputStream);
  Sheet sheet = workbook.getSheet(sheetName);



  int rows = sheet.getLastRowNum();
  List<Mealitems> itemsList = new ArrayList<>();

  MealPlan mealPlan = new MealPlan();
  mealPlan.setName("My new meal plan template");
  mealPlan.setItems(itemsList);



  int weekNumber = 1;

  System.out.println("number of rows" +rows);
  for (int r = 1; r <= rows; r++) {
   Row row = sheet.getRow(r);


   int day = (int) getNumericCellValue(row.getCell(0));
   String title1 = getStringCellValue(row.getCell(1));
   String title2 = getStringCellValue(row.getCell(2));
   String title3 = getStringCellValue(row.getCell(3));
   long position = (long) getNumericCellValue(row.getCell(4));


   int id1 = 0;
   int id2 = 0;
   int id3 = 0;

   String id1Str = getStringCellValue(row.getCell(5));
   String id2Str = getStringCellValue(row.getCell(6));
   String id3Str = getStringCellValue(row.getCell(7));



   double servings = getNumericCellValue(row.getCell(8));
   String imageType = getStringCellValue(row.getCell(9));
   double calories = getNumericCellValue(row.getCell(10));


   int dayTotalCalories = 0;
   int weekTotalCalories = 0;

   double totalCalories = servings * calories;

   dayTotalCalories += totalCalories;
   weekTotalCalories += totalCalories;


   if (dayTotalCalories > MAX_DAILY_CALORIES) {
    System.out.println("Total Weekly Calories: " + weekTotalCalories);
   }

   if (weekTotalCalories > MAX_WEEKLY_CALORIES) {
    weekNumber++;
    System.out.println("Daily calorie limit exceeded!");
   }




   Recipe recipe1 = new Recipe();
   recipe1.setId((int) getNumericCellValue(row.getCell(5)));
   recipe1.setServings(String.valueOf(servings));
   recipe1.setTitle(getStringCellValue(row.getCell(1)));
   recipe1.setImageType(imageType);

   Recipe recipe2 = new Recipe();
   recipe2.setId((int) getNumericCellValue(row.getCell(6)));
   recipe2.setServings(String.valueOf(servings));
   recipe2.setTitle(getStringCellValue(row.getCell(2)));
   recipe2.setImageType(imageType);

   Recipe recipe3 = new Recipe();
   recipe3.setId((int) getNumericCellValue(row.getCell(7)));
   recipe3.setServings(String.valueOf(servings));
   recipe3.setTitle(getStringCellValue(row.getCell(3)));
   recipe3.setImageType(imageType);

   Mealitems mealItem1 = new Mealitems();
   mealItem1.setDay(day);
   mealItem1.setSlot(1);
   mealItem1.setPosition((int) position);
   mealItem1.setType("RECIPE");
   mealItem1.setValue(recipe1);
   itemsList.add(mealItem1);

   Mealitems mealItem2 = new Mealitems();
   mealItem2.setDay(day);
   mealItem2.setSlot(2);
   mealItem2.setPosition((int) position);
   mealItem2.setType("RECIPE");
   mealItem2.setValue(recipe2);
   itemsList.add(mealItem2);

   Mealitems mealItem3 = new Mealitems();
   mealItem3.setDay(day);
   mealItem3.setSlot(3);
   mealItem3.setPosition((int) position);
   mealItem3.setType("RECIPE");
   mealItem3.setValue(recipe3);
   itemsList.add(mealItem3);


  }



  String requestObject = GsontoJSON.convettoJSN(mealPlan);
  System.out.println("Request JSON: " + requestObject);

  Response response = postRequest("https://api.spoonacular.com/mealplanner/api-123-user16/templates?hash=32a133cc8c6f26f82acdb2eb4958a903a80f5a8b", requestObject);
  Assert.assertEquals(response.statusCode(), 200);
  System.out.println("Response Status Code: " + response.getStatusCode());
  System.out.println("Response Body: " + response.getBody().asString());



 }




 public String getStringCellValue(Cell cell) {
  if (cell == null) {
   return "";
  } else if (cell.getCellType() == CellType.STRING) {
   return cell.getStringCellValue();
  } else {
   return "";
  }
 }


 public double getNumericCellValue(Cell cell) {
  if (cell == null) {
   return 0;
  }
  else if (cell.getCellType() == CellType.NUMERIC) {
   return cell.getNumericCellValue();
  }
  else if (cell.getCellType() == CellType.STRING) {
   try {
    return Double.parseDouble(cell.getStringCellValue());
   }
   catch (NumberFormatException e) {
    return 0;
   }
  }
  else {
   return 0;
  }
 }
 public Response postRequest(String url, String requestBody) {
  Response response = given()
          .contentType(ContentType.JSON)
          .queryParam("apiKey", "3f6adc999d2e4c9cabb3b527db7acf0f")
          .and()
          .body(requestBody)
          .when()
          .post(url)
          .then()
          .extract().response();

  return response;
 }
}