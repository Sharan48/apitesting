package paths.customers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CustomerPassword {


    public String getpassword() throws IOException, ParseException{
      

      byte[]  file=Files.readAllBytes(Paths.get("src\\main\\java\\Resource\\customers\\customerlogin.json"));
      String stringbody=new String(file);
      JSONParser parse = new JSONParser();
      Object parsebody = parse.parse(stringbody);
      JSONObject body = (JSONObject)parsebody;
      String mobilenumber = (String)body.get("mobile_number");
      return mobilenumber;

    }
    
}
