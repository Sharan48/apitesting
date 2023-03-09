package paths.customers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BasePaths {

    private String baseuritokn="http://43.204.100.142:8011/users-services";
    private String userendpoint="/v1/app-token/fetch-app-token";
    private String customerendpoint="/v1/customers";
    private String verifytoeknendpoint="/v1/customers/verify-otp";
    private long timelessthan=1000;
    private String loginpath="/v1/customers/login";
    private String otppath="/v1/customers/resend-otp";
    private String putpath="/v1/customers/";


    public String getbaseuri() throws IOException {

        FileInputStream file = new FileInputStream("src\\configuration\\customer\\customersbaseuri.properties");
        Properties load = new Properties();
        load.load(file);
        String uri = load.getProperty("baseuri");
        return uri;

    }

    public String getBaseuritokn() {
        return baseuritokn;
    }

    public String getUserendpoint() {
        return userendpoint;
    }

    public String getCustomerendpoint() {
        return customerendpoint;
    }

    public String getVerifytoeknendpoint() {
        return verifytoeknendpoint;
    }

    public long getTimelessthan() {
        return timelessthan;
    }

    public String getLoginpath() {
        return loginpath;
    }

    public String getOtppath() {
        return otppath;
    }

    public String getPutpath() {
        return putpath;
    }

    
    

}
