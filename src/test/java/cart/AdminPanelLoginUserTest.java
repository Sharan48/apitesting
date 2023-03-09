package cart;

import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AdminPanelLoginUser;
import pojo.AdminPanelLoginUserResponse;
import utilits.PropertiesData;



import static io.restassured.RestAssured.given;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPanelLoginUserTest {
  AdminPanelLoginUser user;
  RequestSpecification req;
  ResponseSpecification rsp;

    @Test(priority = 0,enabled = false)
    public void loginTestVerify() throws IOException {

     //Enter userlogin credentails
       user=new AdminPanelLoginUser();
       PropertiesData readdata = new PropertiesData();
        List<String> datafromfile = readdata.propertiesDataofdaminpanel();
       user.setUser_name(datafromfile.get(0));
       user.setPassword(datafromfile.get(1));
       user.setApp_name(datafromfile.get(2)); 

       //Enter base url
       req = new RequestSpecBuilder().setBaseUri("http://43.204.100.142:8011").
       setContentType(ContentType.JSON).build();

       rsp = new ResponseSpecBuilder().expectStatusCode(200).
       expectContentType(ContentType.JSON).build();

      Response responsebody = given().spec(req).body(user).
       when().
             post("/users-services/v1/token/login").
      then().
             extract().response();
       

      //Extra the response body
       String responseextractbody = responsebody.getBody().asString();

      //Extract the response head
      String responseofheader = responsebody.getHeaders().toString();

      //Create a json object to store body and header
      Map<String, Object> map=new HashMap<String, Object>();
      map.put("body",responseextractbody);
      map.put("header",responseofheader);
      
      JSONObject responsedata=new JSONObject(map);
      
      //Write json object to file
      FileWriter responsefile=new FileWriter("src\\main\\java\\Resource\\responebody.json");
    
      ObjectMapper mapper=new ObjectMapper();
      String valuedat = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responsedata);
      responsefile.write(valuedat);
      responsefile.close();
      

    }

    @Test(priority = 1, enabled = false)
    public void loginTest() throws IOException {

       AdminPanelLoginUserResponse response = given().spec(req).body(user).
       when().
             post("/users-services/v1/token/login").
       then().
             log().all().extract().response().as(AdminPanelLoginUserResponse.class);

       //AccessToken
       String username = response.getUser_name();
       System.out.println(username);
       Assert.assertEquals("amit_singh",username);

    }

    /**
     * @throws IOException
     */
    @Test(priority = 2, enabled = false)
    public void loginTestVerifyHead() throws IOException {

       given().
             spec(req).body(user).
       when().
             post("/users-services/v1/token/login").
       then().
             log().all().assertThat().
              header("server","uvicorn").
              header("content-encoding", "gzip").and().
              body("id",is(17));

    }

    
}
