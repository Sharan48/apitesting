package utilits;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesData {

    
    public List<String> propertiesDataofdaminpanel() throws IOException{

        FileInputStream filedata=new FileInputStream("src\\configuration\\configadminpanel.properties");
        java.util.Properties property = new java.util.Properties();
        property.load(filedata);
        String username = property.getProperty("username");
        String password = property.getProperty("password");
        String appname=property.getProperty("appname");
        return Arrays.asList(username,password,appname);
    }

    public List<String> propertiesofdataofstorefront() throws IOException{

        FileInputStream filedataofstore = new FileInputStream("src\\configuration\\configstorefront.properties");
        Properties property=new Properties();
        property.load(filedataofstore);
        String username = property.getProperty("username");
        String password = property.getProperty("password");
        String appname=property.getProperty("appname");
        return Arrays.asList(username,password,appname);

    }

    public String allpaths() throws IOException{
        FileInputStream fileofpaths = new FileInputStream("src\\configuration\\paths.properties");
        Properties property = new Properties();
        property.load(fileofpaths);
        String orderpath = property.getProperty("orderpath");
        return orderpath;
    }
    
}
