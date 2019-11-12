package Model;

import java.io.Serializable;

public class Horario implements Serializable {
    private Aula aula;
    private String dia;
    private String horas;

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public Horario(Aula aula, String dia, String horas) {
        this.aula = aula;
        this.dia = dia;
        this.horas = horas;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "aula=" + aula +
                ", dia='" + dia + '\'' +
                ", horas='" + horas + '\'' +
                '}';
    }
}
