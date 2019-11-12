package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Estudiante implements Serializable {

    private int carnet;
    private String nombre="";
    private String email="";
    private int phone=0;
    private boolean rn;
    private int  pin;
    private ArrayList<Curso> cursosActuales= new ArrayList<>();

    public Estudiante(int carnet, String nombre, String email, int phone) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.email = email;
        this.phone = phone;
        this.rn=false;
        this.pin=0;
    }

    public Estudiante(int carnet) {
        this.carnet = carnet;
        this.rn=false;
    }


    public ArrayList<Curso> getCursosActuales() {
        return cursosActuales;
    }

    public void setCursosActuales(ArrayList<Curso> cursosActuales) {
        this.cursosActuales = cursosActuales;
    }

    public void addCursoActuale(Curso cursoActual) {
        this.cursosActuales.add(cursoActual);
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

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}

