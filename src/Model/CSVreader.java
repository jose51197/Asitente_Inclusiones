package Model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class CSVreader {

    public ArrayList<ArrayList<String>>  readFiles(String filepath) throws IOException {
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

    public Map<Integer, Estudiante> getEstudiantes(String filepath, Map<String,Curso> malla) throws IOException {
        ArrayList<ArrayList<String>> data= readFiles(filepath);
        data.remove(0);
        int carnet=0;
        Estudiante estudiante = null;
        Map<Integer, Estudiante> result = new HashMap<Integer, Estudiante>();
        for(ArrayList<String> row :data){
            int currentCarnet= Integer.valueOf(row.get(0));
            if(currentCarnet!=carnet){
                estudiante= new Estudiante(currentCarnet);
                result.put(currentCarnet,estudiante);
                carnet=currentCarnet;
            }
            if(!row.get(2).equals("1")){
                estudiante.setRn(true);
            }
            String codigoCurso=row.get(1).split(" - ")[0];
            Curso curso=malla.get(codigoCurso);
            estudiante.addCursoActuale(curso);

        }
        return result;
    }

    public Map<String, Curso> getMalla_Curricular(String filepath) throws IOException {
        ArrayList<ArrayList<String>> data= readFiles(filepath);
        data.remove(0);
        Curso actual;
        Map<String, Curso> result = new HashMap<String, Curso>();
        for(ArrayList<String> row :data){
            String codigo= row.get(0);
            actual=new Curso(codigo,row.get(1),Integer.valueOf(row.get(2)));
            result.put(codigo,actual);
        }
        for(ArrayList<String> row :data){
            String codigo= row.get(0);
            String[] requisitos = row.get(3).split(",");
            String[] corequisitos = row.get(4).split(",");
            for(String requisito:requisitos){
                if(result.containsKey(requisito)) {
                    Curso requisitoActual = result.get(requisito);
                    actual = result.get(codigo);
                    actual.addRequisito(requisitoActual);
                }
            }
            for(String corequisito:corequisitos){
                if(result.containsKey(corequisito)) {
                    Curso corequisitoActual = result.get(corequisito);
                    actual = result.get(codigo);
                    actual.addCorequisito(corequisitoActual);
                }
            }
        }
        return result;
    }
}
