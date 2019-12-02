package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Estudiante {

    private int carnet;
    private String nombre="";
    private String email="";
    private int phone=0;
    private boolean rn=false;
    private double ponderado=0;
    private String plan="N/A";
    private Map<String, String> cursos=  new HashMap<>();
    private Map<String, Grupo> grupos=  new HashMap<>();

    public Estudiante(int carnet, String nombre, String email, int phone,String plan) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.email = email;
        this.phone = phone;
        this.plan=plan;
        this.rn=false;
    }

    public Estudiante(int carnet, String nombre,String plan) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.rn=false;
        this.plan=plan;
    }

    public Estudiante(int carnet,String plan) {
        this.carnet = carnet;
        this.rn=false;
        this.plan=plan;
    }




    public int getCarnet() {
        return carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    public boolean isRn() {
        return rn;
    }

    public void setCarnet(int carnet) {
        this.carnet = carnet;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setRn(boolean rn) {
        this.rn = rn;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Map<String, String> getCursos() {
        return cursos;
    }

    public void setCursos(Map<String, String> cursos) {
        this.cursos = cursos;
    }

    public Map<String, Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(Map<String, Grupo> grupos) {
        this.grupos = grupos;
    }

    public double getPonderado() {
        return ponderado;
    }

    public void setPonderado(double ponderado) {
        this.ponderado = ponderado;
    }
}

