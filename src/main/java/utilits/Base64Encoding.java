package utilits;
import java.util.Base64;

public class Base64Encoding {

    
  public static String base64Encoding(String input) {
    return Base64.getEncoder().encodeToString(input.getBytes());
  
  }

  public static String getBase64Encoding(String saltname, String passwrd) {
    String salt = saltname;
    String password = passwrd;
    Argon2Encoder encoder = new Argon2Encoder(salt,16,1,16,2);
    String hash = encoder.encode(password);
    // System.out.println(hash);
    String base64EncodedHash = base64Encoding(hash);
    // System.out.println(base64EncodedHash);

   return base64EncodedHash;
  }
}
    

