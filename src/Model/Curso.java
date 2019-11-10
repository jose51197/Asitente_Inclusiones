package Model;

import java.util.ArrayList;

public class Curso {
    private String id;
    private String nombre;
    private ArrayList<Curso> requisitos = new ArrayList<>();
    private ArrayList<Curso> corequisitos = new ArrayList<>();
    private int creditos;

    public Curso(String id, String nombre, int creditos) {
        this.id = id;
        this.nombre = nombre;
        this.creditos=creditos;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Curso> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(ArrayList<Curso> requisito) {
        this.requisitos = requisito;
    }

    public ArrayList<Curso> getCorequisitos() {
        return corequisitos;
    }

    public void setCorequisitos(ArrayList<Curso> corequisito) {
        this.corequisitos = corequisito;
    }

    public void addRequisito(Curso requisito){
        this.requisitos.add(requisito);
    }

    public void addCorequisito(Curso corequisito){
        this.corequisitos.add(corequisito);
    }
}
