package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import java.util.List;

public class Grupo {
    private int numGrupo;
    private String profesor;
    private Curso curso;
    private List<Horario> horarios;

    public boolean notContainsHorario(String dia){
        for (Horario horario: horarios){
            if(horario.getDia().equals(dia)){
                return false;
            }
        }
        return true;
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
}
