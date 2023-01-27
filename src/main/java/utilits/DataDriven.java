package utilits;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDriven {
    
    @Test
    // @DataProvider(name="DataProvider")
    public static void main(String[] args) throws IOException{

        PropertiesDataOfAdminPanel readdata = new PropertiesDataOfAdminPanel();
        List<String> datafromfile = readdata.propertiesData();
        System.out.println(datafromfile.get(0));
    }
}
