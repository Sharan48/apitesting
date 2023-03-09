package Product.token;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import paths.customers.BasePaths;
import paths.product.category.ProductBasepaths;

import static io.restassured.RestAssured.given;

public class AuthAndToken {

    public String xapptoken() throws IOException{
      //Get path for generate token
      BasePaths baseuri = new BasePaths();
      String tokenpath = baseuri.getBaseuritokn();
      String endpointpath = baseuri.getUserendpoint();
      

      RequestSpecification reqtoken = new RequestSpecBuilder().setBaseUri(tokenpath).build();
              
      byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\product\\category\\accepttoken.json"));
      String bodytoken = new String(file);
      
      String accepttoken = Base64.getEncoder().encodeToString(bodytoken.getBytes());
      System.out.println(accepttoken);

      Map<String, String> map = new HashMap<String, String>();
      map.put("data", accepttoken);

      String token = given().spec(reqtoken).headers(map).when().get(endpointpath).then().log()
              .all().extract().body().jsonPath().get("token");
      System.out.println(token);

      return token;
      
    
 
    }

    public String authtoken() throws IOException{

        //Get path for category product
     ProductBasepaths paths = new ProductBasepaths();
     String bearerbasepath = paths.getBearerbasepath();
     String baererendpath = paths.getBearerendpoint();

     byte[] file1 = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\product\\category\\bearertoken.json"));
     String bearertokenbody = new String(file1);

     RequestSpecification reqbearer = new RequestSpecBuilder().setBaseUri(bearerbasepath).setContentType(ContentType.JSON).build();

     String bearerresponse = given().spec(reqbearer).body(bearertokenbody).when().
     post(baererendpath).then().log().all().extract().response().jsonPath().get("last_access_token");

      System.out.println(bearerresponse);
      return bearerresponse;
    }
    
}
