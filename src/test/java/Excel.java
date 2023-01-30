import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

public class Excel {
    /**
     * @param args
     * @return 
     * @throws EncryptedDocumentException
     * @throws IOException
     */
    public static void main(String[] args) throws EncryptedDocumentException, IOException {
        
        FileInputStream file=new FileInputStream("src\\main\\java\\Resource\\Data.xlsx");
         Workbook book = WorkbookFactory.create(file);
         Sheet sheet = book.getSheet("Orders");
        //  Row row = sheet.getRow(0);
        // Cell cell = row.getCell(2);

        DataFormatter format=new DataFormatter();
        
        //data fetching based on data type

        for(Row row:sheet){
            for(Cell cell:row){
                //Using dataformatter
            //     String value = format.formatCellValue(cell);
            //  System.out.println(value);


            switch (cell.getCellType()) {
                case STRING:
                String string = cell.getRichStringCellValue().getString(); 
                    break;
                    case NUMERIC:
                    if(DateUtil.isCellDateFormatted(cell)){
                        java.util.Date data = cell.getDateCellValue();
                    } else{  
                    double number = cell.getNumericCellValue();
                    }
                    break;
                    case BOOLEAN:
                    boolean bolean = cell.getBooleanCellValue(); 
                        break;
                        case BLANK:
                        System.out.println("BLANK");
                            break;
                default:
                System.out.println("h");
                    break;
                    
            }
            

            }
        }


        // CreationHelper creation = book.getCreationHelper();

        // //cell style with format
        // CellStyle style = book.createCellStyle();
        // style.setDataFormat(creation.createDataFormat().getFormat("d/m/yy h:mm"));

        // //Drawing Border
        // PropertyTemplate template=new PropertyTemplate();
        // template.drawBorders(new CellRangeAddress(0, 2, 0, 2),
        // BorderStyle.THICK,IndexedColors.RED.getIndex(),BorderExtent.OUTSIDE);
        // template.applyBorders(sheet);

        // //If cell is empty
        // if(cell==null)
        // cell=row.createCell(1);
        // cell.setCellValue(Calendar.getInstance());
        // cell.setCellStyle(style);

        //write data
       FileOutputStream datawrite=new FileOutputStream("src\\main\\java\\Resource\\Data.xlsx");
       book.write(datawrite);
         book.close();
         
        System.out.println("cell created");


    }
}
