package login;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.LoginUser;
import utilits.PropertiesDataOfAdminPanel;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LoginUserTest {
    // @Test
    public static void main(String[] args) throws IOException {

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

       given().spec(rsp).body(user).
       when().
       post("/users-services/v1/token/login").
       then().
       log().all();
       System.out.println("Done");



    // RequestSpecification req1 = new RequestSpecBuilder().setBaseUri("https://reqres.in/").setContentType(ContentType.JSON).build();
    
    // HashMap<String,String> map=new HashMap<String,String>();
    // map.put("name","simani");
    // map.put("job","jojo123");
 
    // JSONObject obj=new JSONObject(map);

    // given().
    // spec(req1).body(obj.toJSONString()).
    // when().
    // put("/api/users/2").
    // then().
    // log().all().assertThat().statusCode(200).contentType(ContentType.JSON);



    }
}
