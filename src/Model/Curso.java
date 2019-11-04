package Model;

import java.util.ArrayList;

public class Curso {
    private String id;
    private String nombre;
    private ArrayList<Curso> requisitos;
    private ArrayList<Curso> corequisitos;

    public Curso(String id, String nombre, ArrayList<Curso> requisitos, ArrayList<Curso> corequisitos) {
        this.id = id;
        this.nombre = nombre;
        this.requisitos = requisitos;
        this.corequisitos = corequisitos;
    }

    public Curso(String id, String nombre, ArrayList<Curso> requisitos) {
        this.id = id;
        this.nombre = nombre;
        this.requisitos = requisitos;
        this.corequisitos = new ArrayList();
    }

    public Curso(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.requisitos = new ArrayList();
        this.corequisitos = new ArrayList();
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

    public ArrayList<Curso> getRequisito() {
        return requisitos;
    }

    public void setRequisito(ArrayList<Curso> requisito) {
        this.requisitos = requisito;
    }

    public ArrayList<Curso> getCorequisito() {
        return corequisitos;
    }

    public void setCorequisito(ArrayList<Curso> corequisito) {
        this.corequisitos = corequisito;
    }
}
