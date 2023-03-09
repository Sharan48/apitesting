package cart;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.StoreFrontLoginUser;
import utilits.PropertiesData;
import static io.restassured.RestAssured.given;

public class StoreFrontLoginUserTest {

    @org.testng.annotations.Test(enabled=true)
    public void loginVerify() throws IOException {
       
        //Login credentials
        PropertiesData data = new PropertiesData();
        List<String> logincredential = data.propertiesofdataofstorefront();

        StoreFrontLoginUser login=new StoreFrontLoginUser();
        login.setUser_name(logincredential.get(0));
        login.setPassword(logincredential.get(1));
        login.setApp_name(logincredential.get(2));


        //Enter base uri
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("http://43.204.100.142:8011").
        setContentType(ContentType.JSON).build();
        ResponseSpecification rsp = new ResponseSpecBuilder().expectStatusCode(200).
       expectContentType(ContentType.JSON).build();

       given().
             spec(req).body(login).
       when().
             post("/users-services/v1/token/login").
      then().time(Matchers.lessThan(4000L), TimeUnit.MILLISECONDS).log().all();
             


    }


}
