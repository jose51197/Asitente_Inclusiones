package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Horario implements Serializable {
    private Aula aula;
    private String dia;
    private LocalTime horaInicio;
    private  LocalTime horaSalida;

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

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Horario(Aula aula, String dia, String horas) {
        this.aula = aula;
        this.dia = dia;
        String [] hora = horas.split("-");
        horaInicio= LocalTime.parse(hora[0]);
        horaSalida= LocalTime.parse(hora[1]);
    }

    @Override
    public String toString() {
        return "Horario{" +
                "aula=" + aula +
                ", dia='" + dia + '\'' +
                ", horas='" + horaInicio.toString()+" - "+ horaSalida.toString() + '\'' +
                '}';
    }
}
