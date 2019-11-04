package Model;

public class Inclusion {

    private boolean planB;
    private Grupo grupo;
    private EstadoInclusion estado;
    private Estudiante estudiante;


    public Inclusion(boolean planB, Grupo grupo, EstadoInclusion estado, Estudiante estudiante) {
        this.planB = planB;
        this.grupo = grupo;
        this.estado = estado;
        this.estudiante = estudiante;
    }

    public boolean isPlanB() {
        return planB;
    }

    public void setPlanB(boolean planB) {
        this.planB = planB;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public EstadoInclusion getEstado() {
        return estado;
    }

    public void setEstado(EstadoInclusion estado) {
        this.estado = estado;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
