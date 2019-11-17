package Model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataHolder {
    private Map<String,Map<String, Curso>> malla;
    private Map<Integer, Estudiante> estudiantes;
    private Map<String, Aula> aulas;
    private Map<String, Grupo> grupos;
    private Map <Integer, ArrayList<Inclusion>> inclusionesMapPorEstudiante;
    private Map <String, ArrayList<Inclusion>> inclusionesMapPorMateria;
    private ArrayList<Inclusion> inclusiones;
    private static DataHolder instance ;

    private DataHolder (){
        malla= new HashMap<>();
        estudiantes= new HashMap<>();
        aulas = new HashMap<>();
        grupos = new HashMap<>();
        inclusionesMapPorEstudiante= new HashMap<>();
        inclusionesMapPorMateria= new HashMap<>();
        inclusiones = new ArrayList<>();
    }

    public static DataHolder getInstance(){
        if (instance==null){
            instance= new DataHolder();
        }
        return instance;
    }

    public Map<String,Map<String, Curso>> getMalla() {
        return malla;
    }

    public void setMalla(Map<String,Map<String, Curso>> malla) {
        this.malla = malla;
    }

    public Map<Integer, Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Map<Integer, Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Map<String, Aula> getAulas() {
        return aulas;
    }

    public void setAulas(Map<String, Aula> aulas) {
        this.aulas = aulas;
    }

    public Map<String, Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(Map<String, Grupo> grupos) {
        this.grupos = grupos;
    }

    public Map<Integer, ArrayList<Inclusion>> getInclusionesMapPorEstudiante() {
        return inclusionesMapPorEstudiante;
    }

    public void setInclusionesMapPorEstudiante(Map<Integer, ArrayList<Inclusion>> inclusionesMap) {
        this.inclusionesMapPorEstudiante = inclusionesMap;
    }

    public ArrayList<Inclusion> getInclusiones() {
        return inclusiones;
    }

    public void setInclusiones(ArrayList<Inclusion> inclusiones) {
        this.inclusiones = inclusiones;
    }

    public Map<String, ArrayList<Inclusion>> getInclusionesMapPorMateria() {
        return inclusionesMapPorMateria;
    }

    public void setInclusionesMapPorMateria(Map<String, ArrayList<Inclusion>> inclusionesMapPorMateria) {
        this.inclusionesMapPorMateria = inclusionesMapPorMateria;
    }
}
