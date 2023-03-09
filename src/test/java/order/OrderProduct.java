package order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utilits.Base64Encoding;
import utilits.Password;
import utilits.PropertiesData;

import static io.restassured.RestAssured.*;

public class OrderProduct {

    RequestSpecification req ;
    pojo.order.OrderPlaceResponse[] response;
    String path;
    Map<String,String> map;
   


    @Test(priority = 0, enabled = true)
    public void placeOrder() throws IOException, ParseException{

        PropertiesData data = new PropertiesData();
        path = data.allpaths();
        req = new RequestSpecBuilder().setBaseUri(path).
        setContentType(ContentType.JSON).build();

        byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\orderplace.json"));
        String body = new String(file);

        Password pswrd = new Password();
        String password = pswrd.getorderpasswrod();
        System.out.println(password);


        String origin="https://faarms.in";
        String secretkey=Base64Encoding.getBase64Encoding(origin, password);
        map=new HashMap<String,String>();
        map.put("secret-key",secretkey);
        map.put("origin",origin);

        response = given().spec(req).headers(map).body(body).
        when().
        post("/v4/orders").
        then().extract().response().body().as(pojo.order.OrderPlaceResponse[].class);

        System.out.println(response[0].getId());
        System.out.println(response[0].getOrder_number());
       
        byte[] file1 = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\orderplace.json"));
        String body1 = new String(file1);
        JSONParser parse = new JSONParser();
        Object parsebodyt = parse.parse(body1);
        JSONObject jsonbody = (JSONObject)parsebodyt;
       JSONArray array = (JSONArray)jsonbody.get("order_items");
        
       Double sum=0.0;
       for(int i=0; i<array.size();i++){
         Object order = array.get(i);
         JSONObject orderitems = (JSONObject)order;
       Double price = Double.valueOf((String)orderitems.get("price"));
       int priceqty=Integer.parseInt((String)orderitems.get("qty"));
        sum=sum+(price*priceqty);  
       }
       System.out.println(sum);

       double totalamount=response[0].getTotal_amount();
       System.out.println(totalamount);

       Assert.assertEquals(sum, totalamount);

          

    }

@Test(priority = 1, enabled = true)
    public void cancelOrder(){

        req = new RequestSpecBuilder().setBaseUri(path).
        setContentType(ContentType.JSON).build();

        int orderid=response[0].getId();

        given().spec(req).headers(map).pathParam("orderid", orderid).
        queryParam("status", "cancelled").
        patch("/v4/orders/{orderid}").
        then().assertThat().
        body("message", equalTo("Order status updated")).log().body();
    }

    
}
