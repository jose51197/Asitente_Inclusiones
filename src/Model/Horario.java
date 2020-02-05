package Model;

import java.time.LocalTime;

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


    public boolean choqueDeHorario(Horario horario){
        if (!aula.equals(horario.getAula())) return false;
        if (!dia.equals(horario.getDia())) return false;

        int miInicio = this.horaInicio.toSecondOfDay(), miSalida = this.horaSalida.toSecondOfDay();
        int pInicio = horario.horaInicio.toSecondOfDay(), pSalida = horario.horaSalida.toSecondOfDay();

        if ( miInicio <= pInicio && pSalida <= miSalida ) return true; //Si yo inicio antes y termino despues
        else return pInicio <= miInicio && miSalida <= pSalida; //Si el h de compraracion inicia antes y termina despues
    }

    @Override
    public String toString() {
        return "Horario{" +
                "aula=" + aula +
                ", dia='" + dia + '\'' +
                ", horas='" + horaInicio.toString()+" - "+ horaSalida.toString() + '\'' +
                '}';
    }

    public String toCsv(){
        return  dia + ',' + aula.getCodigo() + "," + horaInicio.toString() +"," + horaSalida.toString();
    }
}
