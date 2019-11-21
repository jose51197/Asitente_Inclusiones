package Model;

import java.io.Serializable;

public class Aula {
    private String codigo;
    private int capacidad;
    private String tipo;
    private String ubicacion;
    private String retiroDeLlaves;
    private String extension;
    private String disponeDeVideoBeam;

    public Aula(String codigo, String tipo, int capacidad, String ubicacion, String retiroDeLlaves, String extension, String disponeDeVideoBeam) {
        this.codigo = codigo;
        this.capacidad = capacidad;
        this.tipo=tipo;
        this.ubicacion=ubicacion;
        this.retiroDeLlaves=retiroDeLlaves;
        this.extension=extension;
        this.disponeDeVideoBeam= disponeDeVideoBeam;
    }



    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }


    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getRetiroDeLlaves() {
        return retiroDeLlaves;
    }

    public void setRetiroDeLlaves(String retiroDeLlaves) {
        this.retiroDeLlaves = retiroDeLlaves;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDisponeDeVideoBeam() {
        return disponeDeVideoBeam;
    }

    public void setDisponeDeVideoBeam(String disponeDeVideoBeam) {
        this.disponeDeVideoBeam = disponeDeVideoBeam;
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

    @Override
    public String toString(){
        if(capacidad==-1){
            return codigo;
        }else{
            return codigo + " Capacidad: "  +capacidad;
        }

    }

}
