package contest.performance.analyzer;

import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.File;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

/**
 * This Class read an Excel File,
 * Then make a Contest Object
 * 
 * @author Shahriar
 * @version 1.0
 */
class ReadExcelFile{
        
    private File fl;
    private Contest contest;
    private String contestName;
    
    /**
     * receive the file which will be read
     * 
     * @param file .xlsx file which will be read
     */
    ReadExcelFile( File file ){
       fl = file;
       contestName = removeExtension( file.getName() );
//       read();
    }
    
    /**
     * removes the extension from file name 
     * 
     * @param fileName a String contains the file name
     * @return a String the file name without Extension
     */
    private String removeExtension(String fileName){
        String ret = new String();
        for( int i = 0; i<fileName.length(); i++ ){
            if( fileName.charAt(i) == '.' ) break;
            ret += fileName.charAt(i);
        }
        return ret;
    }
    
    /**
     * Read the Excel file,
     * And throw an Exception if an Error occured
     * 
     * @throws Exception if there is something wrong in the Excel file
     */
    public void read() throws Exception{
        
            FileInputStream file = new FileInputStream(fl);
            
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            int rowsCount = sheet.getLastRowNum();
            
            if( rowsCount<=2 )
                throw new Exception();
            
            contest = new Contest( contestName, rowsCount-1 );
            
            for (int i = 1; i <= rowsCount; i++) {
                Row row = sheet.getRow(i);
                
                if( i == rowsCount ){
//                    System.out.println(row.getCell(0).getNumericCellValue());
                    contest.addDate( row.getCell(0).getStringCellValue() );
                    break;
                }
                
                String name = row.getCell(0).getStringCellValue();
                long tmp = (long)row.getCell(1).getNumericCellValue();
                String regNo  = Long.toString(tmp);
                int solved = (int)row.getCell(2).getNumericCellValue();
                int tried = (int)row.getCell(3).getNumericCellValue();
                int sbm = (int)row.getCell(4).getNumericCellValue();
                int penalty = (int)row.getCell(5).getNumericCellValue();
                contest.addData(name, regNo, solved, tried, sbm, penalty);
            }
    }
    
    /**
     * After reading the Excel File, It make a Contest object
     * 
     * @return a Contest object
     * @throws Exception an exception if an error occured
     * @see Contest
     */
    Contest getContest() throws Exception{
        read();
        return contest;
    }
    
    /**
     * it will be used for debugging.
     */
    void print(){
        int i;
        System.out.println( contest.totalAc + " " + contest.totalSubmission );
        for( i = 0; i<contest.totalContestant; i++ ){
            System.out.println( contest.participant[i].name + " " + contest.participant[i].regNo );
            
        }
    }
    
}