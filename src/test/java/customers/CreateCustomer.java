package customers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import paths.customers.BasePaths;
import utilits.ExtentManager;
import utilits.Listner;
import utilits.RetryListner;

import static io.restassured.RestAssured.*;

/* 
 * Customer account create by using type, first name, last name, mobile number, age, gender
 * Login by using mobile number
 * Sending otp to registred mobile number
 * Updating body by using id
 * Fetching customer details by using id
 * 
 */

@Listeners(Listner.class)
public class CreateCustomer {
        RequestSpecification req;
        String token;
        String customerendpoint;
    @Test(retryAnalyzer = RetryListner.class)
    public void customerlogin() throws IOException, ParseException {

       
        BasePaths baseuri = new BasePaths();
        String tokenpath = baseuri.getBaseuritokn();
        String customeruri = baseuri.getbaseuri();
        String endpoint=baseuri.getUserendpoint();
        customerendpoint=baseuri.getCustomerendpoint();
        long time = baseuri.getTimelessthan();
        String loginendpoint=baseuri.getLoginpath();
        String otpendpoint=baseuri.getOtppath();
        String putendpoint=baseuri.getPutpath();


        System.out.println(customeruri);


        RequestSpecification reqtoken = new RequestSpecBuilder().setBaseUri(tokenpath).setContentType(ContentType.JSON)
                .build();

        byte[] file1 = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\customers\\accepttoken.json"));
        String bodytoken = new String(file1);
        
        String accepttoken = Base64.getEncoder().encodeToString(bodytoken.getBytes());
        System.out.println(accepttoken);

        Map<String, String> map = new HashMap<String, String>();
        map.put("data", accepttoken);

        token = given().spec(reqtoken).headers(map).when().get(endpoint).then().log()
                .all().extract().body().jsonPath().get("token");
        System.out.println(token);

        req = new RequestSpecBuilder().setBaseUri(customeruri).setContentType(ContentType.JSON)
                .build();
        ResponseSpecification rsp = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON)
                .build();

        byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\customers\\customerlogin.json"));
        String body = new String(file);
        JSONParser parse = new JSONParser(); 
        Object parsebody = parse.parse(body);
        JSONObject jsonbody = (JSONObject)parsebody;
        String firstname=(String)jsonbody.get("first_name");
        String mobilenumber=(String)jsonbody.get("mobile_number");


         Response response = given().spec(req).header("x-app-token", token).body(body).
         when().post(customerendpoint).
        then().spec(rsp).
        assertThat().body("first_name", equalTo(firstname),
         "mobile_number",equalTo(mobilenumber)).time(lessThanOrEqualTo(time)).
        log().all().extract().response();

        String responsestring = response.asPrettyString();
        ObjectMapper mapper = new ObjectMapper();
        String jsontreebody = mapper.readTree(responsestring).toString();
         
        //Generate report
        ExtentTest loggger = ExtentManager.extent.createTest("APITest", "Create Report");
        loggger.info(MarkupHelper.createCodeBlock(jsontreebody, CodeLanguage.JSON));

        String name =(String) response.jsonPath().get("first_name");
        String number = (String)response.jsonPath().get("mobile_number");
        Object id = response.jsonPath().get("id");
        long responsetime = response.time();
        System.out.println(time);

        Assert.assertEquals(firstname, name);
        Assert.assertEquals(mobilenumber, number);
        


        //Login with mobile only number

        System.out.println(mobilenumber);
        JSONObject bodynumber = new JSONObject();
        bodynumber.put("mobile_number", mobilenumber);

        Response responsebody=given().spec(req).header("x-app-token", token).body(bodynumber.toJSONString()).
        when().post(loginendpoint).
        then().log().all().spec(rsp).assertThat().body("mobile_number", equalTo(mobilenumber)).time(lessThanOrEqualTo(time)).
        extract().response();


        int loginid = responsebody.jsonPath().get("id");
        String loginnumber=(String)responsebody.jsonPath().get("mobile_number");
        String loginame = (String)responsebody.jsonPath().get("first_name");
        long logintime = responsebody.time();
        System.out.println(logintime);

        Assert.assertEquals(id, loginid);
        Assert.assertEquals(name, loginame);
        Assert.assertEquals(number, loginnumber);


        //Send otp to registerd number


        Response otpresponse = given().spec(req).header("x-app-token", token).body(bodynumber.toJSONString()).
        when().post(otpendpoint).
        then().
        assertThat().
        time(lessThanOrEqualTo(time)).and().extract().response();

        String message = otpresponse.body().jsonPath().get("message");
        String details = otpresponse.body().jsonPath().get("detail");
        int statuscode = otpresponse.statusCode();

        if(statuscode==201){
          System.out.println(message);
        }else {
                System.out.println(details);
        }
        
        
        
        //Update body
        String lgnid = Integer.toString(loginid);
        String customerid = Base64.getEncoder().encodeToString(lgnid.getBytes());
        String encoderidendpoint = putendpoint+"d-v-"+customerid;

        byte[] upbody = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\customers\\updatebody.json"));
        String updatebody = new String(upbody);
        JSONParser parse1 = new JSONParser(); 
        Object parsebody1 = parse1.parse(updatebody);
        JSONObject jsonbody1 = (JSONObject)parsebody1;
        String firstname1=(String)jsonbody1.get("first_name");
        String mobilenumber1=(String)jsonbody1.get("last_name");


        Response updateresponse = given().spec(req).header("x-app-token", token).
        body(updatebody).
        when().put(encoderidendpoint).
        then().log().all().assertThat().statusCode(200).
        time(lessThanOrEqualTo(time)).extract().response();


        Object updatebodyid = updateresponse.body().jsonPath().get("id");
        String updatebodyfirstname = updateresponse.body().jsonPath().get("first_name");
        String updatebodylastname = updateresponse.body().jsonPath().get("last_name");

        
        Assert.assertEquals(firstname1, updatebodyfirstname);
        Assert.assertEquals(mobilenumber1, updatebodylastname);
        Assert.assertEquals(loginid, updatebodyid);

        //Fetch customer details

        Response fetchresponse = given().spec(req).header("x-app-token", token).
        body(updatebody).
        when().get(encoderidendpoint).
        then().log().all().assertThat().statusCode(200).
        time(lessThanOrEqualTo(time)).extract().response();

        Object fetchbodyid = fetchresponse.body().jsonPath().get("id");
        String fetchbodyfirstname = fetchresponse.body().jsonPath().get("first_name");
        String fetchlastname = fetchresponse.body().jsonPath().get("last_name");
        
        Assert.assertEquals(updatebodyfirstname, fetchbodyfirstname);
        Assert.assertEquals(updatebodylastname, fetchlastname);
        Assert.assertEquals(updatebodyid, fetchbodyid);

       

    }
}
