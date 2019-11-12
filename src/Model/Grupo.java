package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import java.util.List;

public class Grupo implements Serializable {
    private int numGrupo;
    private String horarios;
    private String profesor;
    private Aula aula;
    private Curso curso;
    private List<Horario> horario;

    public int getNumGrupo() {
        return numGrupo;
    }

    public String getHorarios() {
        return horarios;
    }

    public String getProfesor() {
        return profesor;
    }

    public Aula getAula() {
        return aula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setNumGrupo(int numGrupo) {
        this.numGrupo = numGrupo;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void addHorario(Horario horario){
        this.horario.add(horario);
    }

    public Grupo(int numGrupo, String horarios, String profesor, Aula aula, Curso curso) {
        this.numGrupo = numGrupo;
        this.horarios = horarios;
        this.profesor = profesor;
        this.aula = aula;
        this.curso = curso;
        this.horario = new ArrayList<>();
    }

    public Grupo(int numGrupo, String profesor, Curso curso, List<Horario> horario) {
        this.numGrupo = numGrupo;

        this.profesor = profesor;
        this.curso = curso;
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "numGrupo=" + numGrupo +
                ", horarios='" + horarios + '\'' +
                ", profesor='" + profesor + '\'' +
                ", aula=" + aula +
                ", curso=" + curso +
                ", horario=" + horario +
                '}';
    }
}
