package address;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.specification.RequestSpecification;
import utilits.Base64Encoding;
import utilits.Password;
import static io.restassured.RestAssured.*;

public class Address {
    RequestSpecification req;
    HashMap<String, String> map;
    Object id;
    String responseid;

    @Test(priority = 0, enabled = true)
    public void addAddress() throws IOException, ParseException {

        req = new RequestSpecBuilder().setBaseUri("https://storeapi.faarms.com/customers")
                .setContentType(ContentType.JSON).build();

        byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\address.json"));
        String body = new String(file);
        JSONParser parse = new JSONParser();
        Object parsebodyt = parse.parse(body);
        JSONObject jsonbody = (JSONObject) parsebodyt;
        id = jsonbody.get("customer_id");
        System.out.println(id);

        Password pswrd = new Password();
        String password = pswrd.getAddressPassword();
        System.out.println(password);

        String origin = "https://faarms.in";
        String secretkey = Base64Encoding.getBase64Encoding(origin, password);
        map = new HashMap<String, String>();
        map.put("secret-key", secretkey);
        map.put("origin", origin);

        // Response response = given().spec(req).headers(map).body(body).pathParam("id",
        // id).when()
        // .post("/v1/customer/{id}/address").then().assertThat().statusCode(201).log().all().extract().response();

        // responseid = response.body().jsonPath().getString("id");
        // String responsecutomerid =
        // response.body().jsonPath().getString("customer_id");
        // System.out.println(responseid);

        // Assert.assertEquals(id, responsecutomerid);
    }

    @Test(priority = 1, enabled = false)
    public void editAddress() throws IOException {

        byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\updateaddress.json"));
        String body = new String(file);

        ResponseBodyExtractionOptions responsebody = given().spec(req).headers(map).body(body).pathParam("id", id)
                .pathParam("addressid", responseid).when().put("/v1/customer/{id}/address/{addressid}").then().log()
                .all().and().extract().body();

        String updateid = responsebody.jsonPath().getString("id");
        Assert.assertEquals(responseid, updateid);

    }

    @Test(priority = 2, enabled = true)
    public void removeAddress() throws IOException, ParseException {

        byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\responebody.json"));
        String body = new String(file);
        JSONParser parse = new JSONParser();
        Object parsebody = parse.parse(body);
        JSONObject id1 = (JSONObject) parsebody;
        Long idstring = (Long) id1.get("id");
        System.out.println(idstring);

        // given().spec(req).headers(map).body(body).
        // pathParam("id", id).pathParam("idstring", idstring).
        // when().put("/v1/customer/{id}/address/{idstring}")
        // .then().log().all();

        String response = given().spec(req).headers(map).pathParam("id", id).when().get("/v1/customer/{id}/address")
                .then().log().all().extract().response().body().asPrettyString();

        FileWriter adres = new FileWriter("src\\main\\java\\Resource\\responseadrs.json");

        ObjectMapper mapper = new ObjectMapper();
        String tree = mapper.readTree(response).toPrettyString();
        adres.write(tree);
        adres.close();

    }

}
