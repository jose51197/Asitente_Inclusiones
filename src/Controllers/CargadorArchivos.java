package Controllers;

import Model.CSVreader;
import Model.DataHolder;
import Model.Estudiante;
import Model.Inclusion;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class CargadorArchivos {
    private CSVreader reader= new CSVreader();
    private DataHolder dataHolder = DataHolder.getInstance();

    public void serializeObject( Object object, String name){
        try {
            FileOutputStream fileOut = new FileOutputStream(name+".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


    public Object deserializeObject( String name){
        Object object = null;
        try {
            FileInputStream fileIn = new FileInputStream(name+".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            object = (Object) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
        }
        return object;
    }

    public void cargarNuevosDatos(String pathMalla,String pathRN, String pathAulas, String pathGrupos) throws IOException {

        CSVreader reader= new CSVreader();
        dataHolder.setMalla(reader.getMalla_Curricular(pathMalla));
        serializeObject(dataHolder.getMalla(),"malla");
        dataHolder.setEstudiantes(reader.getEstudiantes(pathRN,dataHolder.getMalla()));
        serializeObject(dataHolder.getEstudiantes(),"estudiantes");
        dataHolder.setAulas(reader.getAulas(pathAulas));
        serializeObject(dataHolder.getAulas(),"aulas");
        dataHolder.setGrupos(reader.getGrupos(pathGrupos,dataHolder.getMalla(),dataHolder.getAulas()));
        serializeObject(dataHolder.getGrupos(),"grupos");
    }

    public void cargarInclusiones(String pathInclusiones) throws IOException {
        CSVreader reader= new CSVreader();
        dataHolder.setInclusiones(reader.getInclusiones(pathInclusiones,dataHolder.getGrupos(),dataHolder.getEstudiantes(),dataHolder.getInclusionesMap()));
    }

    public void cargarDatos(){
        try{
            dataHolder.setMalla((Map)deserializeObject("malla"));
            dataHolder.setEstudiantes((Map)deserializeObject("estudiantes"));
            dataHolder.setAulas((Map)deserializeObject("aulas"));
            dataHolder.setGrupos((Map)deserializeObject("grupos"));
        }
        catch (Exception e){
            System.out.printf("Datos Corruptos");
        }
        try{
            dataHolder.setInclusiones((ArrayList<Inclusion>) deserializeObject("inclusiones"));
            dataHolder.setInclusionesMap((Map)deserializeObject("inclusionesMap"));
        }
        catch (Exception e){
            System.out.printf("Datos Corruptos");
        }
    }
    

}
