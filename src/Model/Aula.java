package Model;

public class Aula {
    private String Codigo;
    private int capacidad;

    public Aula(String codigo, int capacidad) {
        Codigo = codigo;
        this.capacidad = capacidad;
    }

    public String getCodigo() {
        return Codigo;
    }

    public int getCapacidad() {
        return capacidad;
    }


}
