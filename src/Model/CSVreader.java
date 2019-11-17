package Model;

import sun.awt.windows.WPrinterJob;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVreader {

    public ArrayList<ArrayList<String>>  readFiles(String filepath) throws IOException {
        System.out.println(filepath);
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

    public Map<String, Aula> getAulas(String filepath) throws IOException {
        ArrayList<ArrayList<String>> data= readFiles(filepath);
        data.remove(0);
        Map<String, Aula>  result = new HashMap<String, Aula> ();
        for(ArrayList<String> row :data){
            Aula aula= new Aula(row.get(0),Integer.valueOf(row.get(1)),Integer.valueOf(row.get(1))-Integer.valueOf(row.get(2)));
            result.put(row.get(0),aula);
        }
        return result;
    }

    public Map<String, Grupo> getGrupos(String filepath, Map<String,Curso> malla, Map<String,Aula> aulas ) throws IOException {
        ArrayList<ArrayList<String>> data= readFiles(filepath);
        data.remove(0);
        Map<String, Grupo>  result = new HashMap<String, Grupo> ();
        for(ArrayList<String> row :data){
            Aula aula=aulas.get(row.get(4));
            Curso curso = malla.get(row.get(0));
            Grupo grupo= new Grupo(Integer.valueOf(row.get(1)),row.get(3),row.get(2), aula,curso);
            result.put("GR"+row.get(1)+curso.getId(),grupo);
        }
        return result;
    }

    public Map<String, Grupo> getGrupos_Aulas(String filepath, Map<String,Curso> malla, Map<String,Aula> aulas ) throws IOException {
        ArrayList<ArrayList<String>> data = readFiles(filepath);
        data.remove(0);
        Map<String, Grupo>  result = new HashMap<String, Grupo> ();
        for(ArrayList<String> row :data){
            Curso curso = malla.get(row.get(0));
            Grupo grupo = result.get("GR"+row.get(1)+curso.getId());
            if (grupo == null) { //Add to the map
                grupo = new Grupo(Integer.valueOf(row.get(1)), row.get(2), curso, new ArrayList<>());
                result.put("GR"+row.get(1)+curso.getId(),grupo);
            }

            Aula aula=aulas.get(row.get(5));
            String dia = row.get(3);
            String horas = row.get(4);
            Horario h = new Horario(aula, dia, horas);
            grupo.addHorario(h);

        }

        return result;
    }

    public ArrayList<Inclusion> getInclusiones(String filepath, Map<String,Grupo> grupos,Map<Integer, Estudiante> estudiantes,Map<Integer, ArrayList<Inclusion>> inclusionesEstudianteMap ) throws IOException {
        ArrayList<ArrayList<String>> data= readFiles(filepath);
        data.remove(0);
        ArrayList<Inclusion>  result = new ArrayList<> ();
        for(ArrayList<String> row :data){
            try{
                Estudiante e= estudiantes.get(Integer.valueOf(row.get(2)));
                //validar pin WIP
                if(e== null){
                    System.out.println("estudiante no existe");
                }
                else{
                    String[] datosCurso=row.get(6).split(" - ");
                    Grupo g = grupos.get(datosCurso[1]+datosCurso[0]);
                    if(g== null){
                        System.out.println("grupo no existe");
                    }
                    else{
                        boolean planB = (row.get(7).equals("Si"));
                        e.setNombre(row.get(4));
                        e.setEmail(row.get(1));
                        e.setPhone(Integer.valueOf(row.get(5)));
                        Inclusion nuevaInclusion= new Inclusion(planB, g,e,row.get(8),row.get(1));
                        result.add(nuevaInclusion);
                        ArrayList<Inclusion> inclusionesEstudiante;
                        if(inclusionesEstudianteMap.containsKey(e.getCarnet())){
                            inclusionesEstudiante=inclusionesEstudianteMap.get(e.getCarnet());
                            inclusionesEstudiante.add(nuevaInclusion);
                        }
                        else{
                            inclusionesEstudiante= new ArrayList<>();
                            inclusionesEstudiante.add(nuevaInclusion);
                            inclusionesEstudianteMap.put(e.getCarnet(),inclusionesEstudiante);
                        }
                    }
                }
            }
            catch (Exception e){
                System.out.println("Datos Invalidos");
            }
        }
        return result;
    }
}
