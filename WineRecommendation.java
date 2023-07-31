package tests;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import datamodel.Wine;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class WineRecommendation extends TestBase {
    @Test
    public void WineUsed() throws IOException{
        String jsonData = "{\"pairings\":[\"stew\",\"steak\",\"chili\",\"burger\"],\"text\":\"" +
                "Malbec is a dry red wine which is bold and full bodied. It goes especially well with round steak, " +
                "tri tip steak, steak, boneless pork chops, and pizza burger.\"}";

        String API_KEY = "3f6adc999d2e4c9cabb3b527db7acf0f";

        Response response = GetRequest(API_KEY, jsonData);

        assertEquals(response.statusCode(), 200);

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());


        if (response.getBody().asString().contains("pairings")) {
            System.out.println("You can use wine with the meal being prepared for the party during the weekend!");
        } else {
            System.out.println("Wine pairing is not available for the meal being prepared for the party.");
        }
    }

    public Response GetRequest(String apiKey, String requestBody) {
        String url = "https://api.spoonacular.com/food/wine/recommendation?wine=merlot&number=2";


        Response response = given()
                .queryParam("apiKey", "3f6adc999d2e4c9cabb3b527db7acf0f")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .get("https://api.spoonacular.com/food/wine/recommendation?wine=merlot&number=2")
                .then()
                .extract().response();

        return response;
    }
}

