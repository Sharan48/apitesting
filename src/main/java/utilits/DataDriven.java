package utilits;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class DataDriven {
    
    
    @DataProvider(name="DataProvider")
    public Object[][] dataFetch() throws EncryptedDocumentException, IOException{

        FileInputStream datafile=new FileInputStream("src\\main\\java\\Resource\\Data.xlsx"); 
        Workbook workbook = WorkbookFactory.create(datafile);
        Sheet sheet = workbook.getSheet("Orders");
        Object[][] data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
        for(int i=0; i<sheet.getLastRowNum(); i++ ){
            for(int j=0; j<sheet.getRow(0).getLastCellNum();j++){
                data[i][j]=sheet.getRow(1+i).getCell(j);
            }   
        }
        return data;
    }
}
