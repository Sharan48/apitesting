package utilits;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PropertiesDataOfAdminPanel {

    public List<String> propertiesData() throws IOException{

        FileInputStream filedata=new FileInputStream("src\\configuration\\config.properties");
        java.util.Properties property = new java.util.Properties();
        property.load(filedata);
        String username = property.getProperty("username");
        String password = property.getProperty("password");
        String appname=property.getProperty("appname");
        return Arrays.asList(username,password,appname);
    }
    
}
