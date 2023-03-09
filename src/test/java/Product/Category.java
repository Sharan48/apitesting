package Product;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import org.testng.annotations.Test;

import Product.token.AuthAndToken;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import paths.product.category.ProductBasepaths;

public class Category {


    @Test
    public void category() throws IOException{

        //Get path for generate token
       AuthAndToken producttoken = new AuthAndToken();
       String bearerresponse = producttoken.authtoken();
       String token = producttoken.xapptoken();

       //Get path for category product
       ProductBasepaths paths = new ProductBasepaths();
       String baseur = paths.getBaseuripath();
       String endpoint = paths.getEndpointpath();
      

         RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseur)
        .build();

        byte[] file2 = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\product\\category\\data.json"));
        String databody = new String(file2);
    //    System.out.println(databody); 
       

       File imagefile = new File("src\\main\\java\\Resource\\product\\category\\Raagi.jpg");
      
       HashMap<String,String> headers = new HashMap<String,String>();
       headers.put("x-app-token", token);
       headers.put("Authorization", "Bearer "+bearerresponse);
        
       Response response = given().spec(req).
        headers(headers). 
        accept(ContentType.ANY).
        contentType(ContentType.MULTIPART).
        multiPart("image",imagefile,"image/jpg").
        formParam("slug", "grocery3").
        formParam("data", "[{\"name\": \"grocery3\", \"language_id\": 1}]").
        post(endpoint).then().log().all().assertThat().extract().response();

       Object id = response.jsonPath().get("id");
       String slug = response.jsonPath().get("slug");
       String details= response.jsonPath().get("details");
       String statuscode = String.valueOf(response.statusCode());
       System.out.println(statuscode); 
      
        //   Response response1 = given().spec(req).
        // headers(headers). 
        // accept(ContentType.ANY).
        // contentType(ContentType.MULTIPART).
        // multiPart("image",imagefile,"image/jpg").
        // formParam("slug", "grocery4").
        // formParam("data", "[{\"name\": \"grocery3\", \"language_id\": 1}]").
        // post(endpoint).then().log().all().assertThat().extract().response();
        // String stastscode=String.valueOf(response1.statusCode());
       

       
        

    }
    
}
