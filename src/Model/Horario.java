package Model;

import java.time.LocalTime;
import java.util.Map;

public class Horario {
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

    public Horario(Aula aula, String dia, LocalTime horaInicio, LocalTime horaSalida) {
        this.aula = aula;
        this.dia = dia;
        this.horaInicio= horaInicio;
        this.horaSalida=horaSalida;
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
