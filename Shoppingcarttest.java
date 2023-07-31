package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class Shoppingcarttest extends TestBase {
    @Test
    public void newShopping() throws Exception {
        String excelFilePath = "C:\\Users\\RAGHVANDRA KUMAR\\IdeaProjects\\SpoonacularApi\\src\\test\\resources\\ReadFromExcel.xlsx";
        String sheetName = "Sheet2";


        Object[][] tabularData = {

                {632660, "Apricot Glazed Apple Tart", "https://spoonacular.com/recipeImages/632660-312x231.jpg", 3, 2, 200},
                {73420, "Apple Or Peach Strudel", "https://spoonacular.com/recipeImages/73420-312x231.jpg", 0, 1.5, 150},
                {8120,"Cereal","https://spoonacular.com/cdn/ingredients_100x100/rolled-oats.jpg",0, 3, 300},
                {1089003, "grannysmithapples","https://spoonacular.com/cdn/ingredients_100x100/grannysmith-apple.png", 11, 5, 500},
        };

        JSONArray jsonArray = new JSONArray();
        for (Object[] row : tabularData) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", row[0]);
            jsonObject.put("Title", row[1]);
            jsonObject.put("Image", row[2]);
            jsonObject.put("Likes", row[3]);
            jsonObject.put("cups",row[4]);
            jsonObject.put("gramsPerCup",row[5]);
            jsonArray.put(jsonObject);
        }


        System.out.println(jsonArray.toString());


        String apiEndpoint = "https://api.spoonacular.com/recipes/findByIngredients"; // Replace with the actual API endpoint URL
        makePostRequest(apiEndpoint, jsonArray.toString());


    }

    static Response makePostRequest(String apiEndpoint, String jsonData) {


        Response response = RestAssured.given()
                .queryParam("x-apikey","3f6adc999d2e4c9cabb3b527db7acf0f")
                .contentType(ContentType.JSON)
                .and()
                .when()
                .get( "https://api.spoonacular.com/recipes/findByIngredients")
                .then()
                .extract().response();
        return response;

    }
}