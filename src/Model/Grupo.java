package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import java.util.List;

public class Grupo implements Serializable {
    private int numGrupo;
    private String profesor;
    private Curso curso;
    private List<Horario> horario;

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
        this.horario.add(horario);
    }

    public List<Horario> getHorario() {
        return horario;
    }

    public void setHorario(List<Horario> horario) {
        this.horario = horario;
    }

    public Grupo(int numGrupo, String horarios, String profesor, Aula aula, Curso curso) {
        this.numGrupo = numGrupo;
        this.profesor = profesor;
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
                ", profesor='" + profesor + '\'' +
                ", curso=" + curso +
                ", horario=" + horario +
                '}';
    }
}
