package Model;

public class Estudiante{

    private int carnet;
    private String nombre;
    private int ponderado;
    private String email;
    private int phone;
    private boolean rn;

    public Estudiante(int carnet, String nombre, String email, int phone,int ponderado) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.email = email;
        this.phone = phone;
        this.rn=false;
        this.ponderado=ponderado;
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

    public int getPonderado() {
        return ponderado;
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

    public void setPonderado(int ponderado) {
        this.ponderado = ponderado;
    }
}

