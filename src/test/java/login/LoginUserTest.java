package login;

import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.LoginUser;
import pojo.LoginUserResponse;
import utilits.PropertiesDataOfAdminPanel;

import static io.restassured.RestAssured.given;

import java.io.IOException; 
import java.util.List;

public class LoginUserTest {
    @Test
    public void loginTest() throws IOException {

     //Enter userlogin credentails
       LoginUser user=new LoginUser();
       PropertiesDataOfAdminPanel readdata = new PropertiesDataOfAdminPanel();
        List<String> datafromfile = readdata.propertiesData();
       user.setUser_name(datafromfile.get(0));
       user.setPassword(datafromfile.get(1));
       user.setApp_name(datafromfile.get(2)); 

       //Enter base url
       RequestSpecification rsp = new RequestSpecBuilder().setBaseUri("http://43.204.100.142:8011").
       setContentType(ContentType.JSON).build();

       LoginUserResponse response = given().spec(rsp).body(user).
       when().
       post("/users-services/v1/token/login").
       then().
       log().all().extract().response().as(LoginUserResponse.class);

       //AccessToken
       String accesstoken = response.getLast_access_token();

    }
}
