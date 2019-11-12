package Model;

import java.io.Serializable;

public class Horario implements Serializable {
    private Aula aula;
    private String dia;
    private String horas;
    private int lecciones;
    private String horaInicio;
    private String horaFin;

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

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Horario(Aula aula, String dia, String horas) {
        this.aula = aula;
        this.dia = dia;
        this.horas = horas;
        System.out.println(horas);
        horaInicio = horas.split("-")[0];
        horaFin = horas.split("-")[1];
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
