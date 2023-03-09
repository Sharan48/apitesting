package utilits;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Password {
    
    
    public String getCartaddpassword() throws IOException, ParseException, org.json.simple.parser.ParseException{

        byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\cartpayload.json"));
        String body = new String(file);
        JSONParser parse = new JSONParser();
        Object parsebodyt = parse.parse(body);
        JSONObject jsonbody = (JSONObject)parsebodyt;
        String sellerid = String.valueOf(jsonbody.get("seller_id"));
        String qty=(String)jsonbody.get("qty");

        return sellerid+","+qty;

}

   public String getCartUpdatepassword() throws IOException, org.json.simple.parser.ParseException{

    byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\updatecart.json"));
        String body = new String(file);
        JSONParser parse = new JSONParser();
        Object parsebodyt = parse.parse(body);
        JSONObject jsonbody = (JSONObject)parsebodyt;
        String sellerid = String.valueOf(jsonbody.get("seller_id"));
        String qty=(String)jsonbody.get("qty");
        
        return sellerid+","+qty;

   }

   
   public String getorderpasswrod() throws IOException, org.json.simple.parser.ParseException {
    byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\orderplace.json"));
        String body = new String(file);
        JSONParser parse = new JSONParser();
        Object parsebodyt = parse.parse(body);
        JSONObject jsonbody = (JSONObject)parsebodyt;
       JSONArray array = (JSONArray)jsonbody.get("order_items");
        
       int sum=0;
       for(int i=0; i<array.size();i++){
         Object order = array.get(i);
         JSONObject orderitems = (JSONObject)order;
        int id=Integer.parseInt((String)orderitems.get("qty"));
        sum=sum+id;  
       }
       String sumofall=String.valueOf(sum);
    //    System.out.println(sumofall);

       Object order = array.get(0);
       JSONObject ordersellerid = (JSONObject)order;
       String sellerid=String.valueOf(ordersellerid.get("seller_id"));
    //   System.out.println(sellerid);

    return sellerid+","+sumofall;
   }

   public String getAddressPassword() throws IOException, org.json.simple.parser.ParseException{
      byte[] file = Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\address.json"));
      String body = new String(file);
      JSONParser parse = new JSONParser();
      Object parsebodyt = parse.parse(body);
      JSONObject jsonbody = (JSONObject)parsebodyt;
      String postcode = String.valueOf(jsonbody.get("postcode"));
      String mobilenumber=(String)jsonbody.get("mobile_number");

      return postcode+","+mobilenumber;

   }
}
