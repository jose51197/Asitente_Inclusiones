package Model;

import javafx.scene.layout.GridPane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import java.util.List;

public class Grupo {
    private int numGrupo;
    private String profesor;
    private Curso curso;
    private List<Horario> horarios;
    private int cantEstudiantes;

    public boolean notContainsHorario(String dia){
        for (Horario horario: horarios){
            if(horario.getDia().equals(dia)){
                return false;
            }
        }
        return true;
    }

    public void aumentarEstudiantes(){
        this.cantEstudiantes++;
    }

    public void disminuirEstudiantes(){
        this.cantEstudiantes--;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public int getCantEstudiantes() {
        return cantEstudiantes;
    }

    public void setCantEstudiantes(int cantEstudiantes) {
        this.cantEstudiantes = cantEstudiantes;
    }

    public int getNumGrupo() {
        return numGrupo;
    }


    public String getProfesor() {
        return profesor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setNumGrupo(int numGrupo) {
        this.numGrupo = numGrupo;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void addHorario(Horario horario){
        this.horarios.add(horario);
    }

    public List<Horario> getHorario() {
        return horarios;
    }

    public String getHorariotext(){
        String r ="\n";
        for(Horario h:horarios){
            r+=h.getDia() + " " +h.getAula() + " "+h.getHoraInicio() + " " +h.getHoraSalida()+"\n";
        }
        return r;
    }

    public void setHorario(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public Grupo(int numGrupo, String profesor, Curso curso) {
        this.numGrupo = numGrupo;
        this.profesor = profesor;
        this.curso = curso;
        this.horarios = new ArrayList<>();
    }

    public Grupo(int numGrupo, String profesor, Curso curso, List<Horario> horarios) {
        this.numGrupo = numGrupo;
        this.profesor = profesor;
        this.curso = curso;
        this.horarios = horarios;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "numGrupo=" + numGrupo +
                ", profesor='" + profesor + '\'' +
                ", curso=" + curso +
                ", horario=" + horarios.toString() +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Grupo)) return false;

        Grupo other = (Grupo) obj;
        String thisCode = this.getCurso().getId() + this.getNumGrupo();
        String otherCode = other.getCurso().getId() + other.getNumGrupo();

        return thisCode.equals(otherCode);
    }
}
