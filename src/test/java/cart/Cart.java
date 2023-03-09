package cart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.cart.AddToCartResponse;
import utilits.Base64Encoding;
import utilits.Password;
import utilits.PropertiesData;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Cart {

        RequestSpecification req;
        AddToCartResponse response;
        JSONParser parse;
        String path;
        @Test(priority = 1, enabled = true)
        public void addtoCart() throws IOException, ParseException, java.text.ParseException {
                req = new RequestSpecBuilder().setBaseUri(path)
                                .setContentType(ContentType.JSON).build();

                byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\cartpayload.json"));
                String body = new String(file);
                parse= new JSONParser();
                Object parsebodyt = parse.parse(body);
                JSONObject jsonbody = (JSONObject)parsebodyt;
                String itemnm = (String)jsonbody.get("item_name");
                int qty1=Integer.valueOf((String)jsonbody.get("qty"));
                System.out.println(itemnm);
                System.out.println(qty1);


                Password paswrd = new Password();
                String pasword=paswrd.getCartaddpassword();
                System.out.println(pasword);
                
                String salt="https://faarms.in";
                String secretkey = Base64Encoding.getBase64Encoding(salt, pasword);
                System.out.println(secretkey);
                Log logger = LogFactory.getLog(getClass());
 
                String origin = "";
                Map<String, String> map = new HashMap<String, String>();
                map.put("secret-key",secretkey);             
                map.put("origin", origin);

                logger.info("we are logging in using the secret key");

                response = given().spec(req).headers(map).body(body).when().post("/v1/carts").then().assertThat()
                                .body("item_name", equalTo(itemnm),
                                                "qty", equalTo(qty1))
                                .log().all().extract().body().as(AddToCartResponse.class);

                String itemname = response.getItem_name();
                int qty = response.getQty();

                Assert.assertEquals(itemnm, itemname);
                Assert.assertEquals(qty1, qty);

                // Add item to cart
                // Json file to add cart
                byte[] file1 = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\cartpayload1.json"));
                String body1 = new String(file1);
                parse= new JSONParser();
                Object parsebody = parse.parse(body1);
                JSONObject data = (JSONObject)parsebody;
                String  nameofitem = (String)data.get("item_name");
                String mobilenumber = (String)data.get("customer_mobile");
                String currencycode = (String)data.get("currency_code");
                


                given().spec(req).headers(map).body(body1).when().put("/v1/carts").then().log().all();

               

                // Get all cart iteams
                Response responeitems = given().spec(req).headers(map).queryParam("customer_mobile", mobilenumber)
                                .queryParam("currency_code",currencycode ).when().get("/v1/carts");
                                responeitems.then().log().all();

                JsonPath path = new JsonPath(responeitems.getBody().asString());
                String iteamname = path.getString("item_name");
                String[] arry = iteamname.split(",");
                for (int i = 0; i < arry.length; i++) {
                        String name = path.getString("[" + i + "].item_name");
                        if (name.equals(nameofitem)) {
                                Assert.assertEquals(name, nameofitem);
                                System.out.println(name);
                        }
                }

                 // Remove item from cart

                 // Json file to remove from cart
                byte[] file2 = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\removeitemfromcart.json"));
                String body2 = new String(file2);
                parse=new JSONParser();
                Object removebody = parse.parse(body2);
                JSONObject jsonrmbody = (JSONObject)removebody;
                boolean remove = (boolean)jsonrmbody.get("is_remove");
                boolean bln=false;

                if(remove==bln){
                given().spec(req).headers(map).body(body2).when().put("/v1/carts").
                then().log().all();
                }else{
                        System.out.println("Item is not removed");
                }

        }

        @Test(priority = 2, enabled = true)
        public void updateCart() throws IOException, ParseException {
                req = new RequestSpecBuilder().setBaseUri(path)
                                .setContentType(ContentType.JSON).build();

                Password pswrd = new Password();
                String password=pswrd.getCartUpdatepassword();

                String salt="https://faarms.in";
                String secretkey=Base64Encoding.getBase64Encoding(salt, password);

                String origin = "com.faarms";
                Map<String, String> map = new HashMap<String, String>();
                map.put("secret-key",secretkey);         
                map.put("origin", origin);

                byte[] file1 = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\updatecart.json"));
                String updatebody = new String(file1);

                given().spec(req).headers(map).body(updatebody).when().put("/v2/carts").then().log().all();

        }

        @Test(enabled = true, priority = 0)
        public void deleteIteamInCart() throws IOException, ParseException, java.text.ParseException {

                PropertiesData data = new PropertiesData();
                path = data.allpaths();
                byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\cartpayload.json"));
                String body = new String(file);
                parse= new JSONParser();
                Object parsebodyt = parse.parse(body);
                JSONObject data1 = (JSONObject)parsebodyt;
                String mobilenumber = (String)data1.get("customer_mobile");
                String currencycode = (String)data1.get("currency_code");
                System.out.println(mobilenumber);
                System.out.println(currencycode);

                req = new RequestSpecBuilder().setBaseUri(path)
                                .setContentType(ContentType.JSON)
                                .build();

                                Password paswrd = new Password();
                                String pasword=paswrd.getCartaddpassword();
                                String salt="https://faarms.in";
                                String secretkey = Base64Encoding.getBase64Encoding(salt, pasword);
                
                                String origin = "com.faarms";
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("secret-key",secretkey);             
                                map.put("origin", origin);
                              


                given().spec(req).headers(map).
                queryParams("customer_mobile", mobilenumber).queryParam("currency_code", currencycode).
                when().delete("/v1/carts") .then().log().all();
                               

        }


}
