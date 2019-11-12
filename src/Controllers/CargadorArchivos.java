package Controllers;

import Model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class CargadorArchivos {
    private CSVreader reader= new CSVreader();
    private DataHolder dataHolder = DataHolder.getInstance();
    private Serializator serializator= new Serializator();
    private int selected=0;


    public void cargarNuevosDatos(String pathMalla,String pathRN, String pathAulas, String pathGrupos) throws IOException {

        CSVreader reader= new CSVreader();
        dataHolder.setMalla(reader.getMalla_Curricular(pathMalla));
        dataHolder.setEstudiantes(reader.getEstudiantes(pathRN,dataHolder.getMalla()));
        dataHolder.setAulas(reader.getAulas(pathAulas));
        dataHolder.setGrupos(reader.getGrupos_Aulas(pathGrupos,dataHolder.getMalla(),dataHolder.getAulas()));
    }

    public void cargarInclusiones(String pathInclusiones) throws IOException {
        CSVreader reader= new CSVreader();
        dataHolder.setInclusiones(reader.getInclusiones(pathInclusiones,dataHolder.getGrupos(),dataHolder.getEstudiantes(),dataHolder.getInclusionesMap()));
    }

    public void cargarDatos(){
        try{
            dataHolder.setMalla((Map)serializator.deserializeObject("malla"));
            dataHolder.setEstudiantes((Map)serializator.deserializeObject("estudiantes"));
            dataHolder.setAulas((Map)serializator.deserializeObject("aulas"));
            dataHolder.setGrupos((Map)serializator.deserializeObject("grupos"));
        }
        catch (Exception e){
            System.out.printf("Datos Corruptos");
        }
        try{
            dataHolder.setInclusiones((ArrayList<Inclusion>) serializator.deserializeObject("inclusiones"));
            for(Inclusion inclusion: dataHolder.getInclusiones()){
                ArrayList<Inclusion> inclusionesEstudiante;
                Map <Integer,ArrayList<Inclusion>>inclusionesEstudianteMap = dataHolder.getInclusionesMap();
                if(inclusionesEstudianteMap.containsKey(inclusion.getEstudiante().getCarnet())){
                    inclusionesEstudiante=inclusionesEstudianteMap.get(inclusion.getEstudiante().getCarnet());
                    inclusionesEstudiante.add(inclusion);
                }
                else{
                    inclusionesEstudiante= new ArrayList<>();
                    inclusionesEstudiante.add(inclusion);
                    inclusionesEstudianteMap.put(inclusion.getEstudiante().getCarnet(),inclusionesEstudiante);
                }
            }
        }
        catch (Exception e){
            System.out.printf("Datos Corruptos");
        }
    }
    

}
