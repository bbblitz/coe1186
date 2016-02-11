package trackmodelprototype;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class trackmodelmain {
	
	private static final String FILE_PATH = "\Users\pvn03\workspace\trackmodelprototype\src\trackmodelprototype/TrackInfo.xlsx";

	public static void main(String[] args) {
		
			List TrainData = ExcelTrainInfo();

	        System.out.println(TrainInfo);
	    }
	
	private static List ExcelTrainInfo() {
        
		List TrackData = new ArrayList();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(FILE_PATH);

            Workbook workbook = new XSSFWorkbook(fis);
            
            int numberOfSheets = workbook.getNumberOfSheets();

            //looping over each workbook sheet
            for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            Iterator rowIterator = sheet.iterator();
                

          //iterating over each row
            while (rowIterator.hasNext()) {

            trackmodelp trackmodelp = new trackmodelp();
            Row row = rowIterator.next();
            Iterator cellIterator = row.cellIterator();
            
          //Iterating over each cell (column wise)  in a particular row.
            while (cellIterator.hasNext()) {

            	Cell cell = cellIterator.next();
                //The Cell Containing String 1 is Line.
            	if (cell.getColumnIndex() == 1) {
                    trackmodelp.setLine(cell.getStringCellValue());
                }
            	//The Cell Containing String 2 is Section.
            	if (cell.getColumnIndex() == 2) {
                    trackmodelp.setSection(cell.getStringCellValue());
                }
            	//The Cell Containing Number 1 is BlockNo.
            	if (cell.getColumnIndex() == 3) {
                    trackmodelp.setBlockNo(cell.getNumericCellValue());
                }
            	//The Cell Containing Number 2 is BlockLength.
            	if (cell.getColumnIndex() == 4) {
                    trackmodelp.setBlockLength(cell.getNumericCellValue());
                }
            	//The Cell Containing Number 3 is BlockGrade.
            	if (cell.getColumnIndex() == 5) {
                    trackmodelp.setBlockGrade(cell.getNumericCellValue());
                }
            	//The Cell Containing Number 4 is SpeedLimit.
            	if (cell.getColumnIndex() == 3) {
                    trackmodelp.setSpeedLimit(cell.getNumericCellValue());
                }
            }
            }
        }
        //end iterating a row, add all the elements of a row in list
        TrackData.add(trackmodelp);

        
fis.close();

        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
return TrainData;
	}
}