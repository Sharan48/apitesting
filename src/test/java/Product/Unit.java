package Product;

import static io.restassured.RestAssured.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import paths.product.unit.Unitpaths;

public class Unit {

    public void unittest(){

        Unitpaths paths = new Unitpaths();
        String basepath=paths.getBasepath();
        String endpath=paths.getEndpoint();

        byte[] file2 = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\product\\unit\\unitbody.json"));
        String databody = new String(file2);

        RequestSpecification request = new RequestSpecBuilder().setBaseUri(basepath).setContentType(ContentType.JSON).build();

        given().spec(request).
    }
    
}
