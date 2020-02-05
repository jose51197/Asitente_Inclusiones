package Model;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelReader {

    public Map<String, ArrayList<ArrayList<String>>> readXlsxFile(String path) throws IOException {
        File excelFile = new File(path);
        FileInputStream fin = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fin);
        Map<String, ArrayList<ArrayList<String>>> result = new HashMap<>();
        for(int i=0; i< workbook.getNumberOfSheets();i++){
            ArrayList<ArrayList<String>> sheetArray= new ArrayList<>();
            XSSFSheet sheet = workbook.getSheetAt(i);
            Iterator<Row> rowIt = sheet.iterator();
            while(rowIt.hasNext()) {
                ArrayList<String> rowArray= new ArrayList<>();
                Row row = rowIt.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                for(int j =0; j<row.getLastCellNum();j++){
                    Cell cell = row.getCell(j);
                    if(cell==null){
                        rowArray.add("");
                    }
                    else{
                        if(cell.getCellType().toString().equals("NUMERIC")){
                            rowArray.add(String.valueOf(cell.getNumericCellValue()));
                        }
                        else{
                            rowArray.add(cell.toString().replace("\t"," "));
                        }
                    }

                }
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    rowArray.add(cell.toString());
                }
                sheetArray.add(rowArray);
            }
            result.put(sheet.getSheetName(),sheetArray);
            workbook.close();
            fin.close();

        }
        return result;
    }

    public ArrayList<ArrayList<String>> readCsvFiles(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String row;
        String data="";
        ArrayList<String> cell= new ArrayList<>();
        ArrayList<ArrayList<String>> result= new ArrayList<>();
        boolean cut=true;
        while ((row = reader.readLine()) != null) {
            cut=true;
            for(int i=0; i<row.length();i++){
                char currentChar=row.charAt(i);
                if(currentChar=='"'){
                    cut= !cut;
                    continue;
                }
                if(cut && currentChar==','){
                    cell.add(data);
                    data="";
                    continue;
                }
                data+=currentChar;
            }
            cell.add(data);
            data="";
            result.add(cell);
            cell= new ArrayList();
        }
        return result;
    }
}
