package Model;

import java.io.Serializable;

public class Aula implements Serializable {
    private String codigo;
    private int capacidad;
    private int camposDisponibles;

    public Aula(String codigo, int capacidad, int camposDisponibles) {
        this.codigo = codigo;
        this.capacidad = capacidad;
        this.camposDisponibles=camposDisponibles;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getCamposDisponibles() {
        return camposDisponibles;
    }

    public void setCamposDisponibles(int camposDisponibles) {
        this.camposDisponibles = camposDisponibles;
    }


}
