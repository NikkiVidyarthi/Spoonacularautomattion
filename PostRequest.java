package tests;


import datamodel.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;


public class PostRequest  extends TestBase {
     public Response response;

    @Test
    public void newLogin() {
        User user1 = new User();
        user1.setUsername("api_123_user");
        user1.setFirstName("yash");
        user1.setLastName("patel");
        user1.setEmail("yash@gmail.com");


        String requestObject = GsontoJSON.convettoJSN(user1);

        Response  response = postRequest("https://api.spoonacular.com/users/connect", requestObject);
       System.out.println("jsonbody post request is " + requestObject);
        Assert.assertEquals(response.statusCode(), 200);
        response.then().log().body();

    }
    public Response postRequest(String url, String requestBody) {
        Response response = given()
                .queryParam("apiKey", "3f6adc999d2e4c9cabb3b527db7acf0f")
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .extract().response();


        return response;
    }
}

