package Model;

public class Inclusion {

    private boolean planB;
    private Grupo grupo;
    private EstadoInclusion estado;
    private Estudiante estudiante;
    private String detalle;
    private String comentarioAdmin="";


    public Inclusion(boolean planB, Grupo grupo, EstadoInclusion estado, Estudiante estudiante, String detalle) {
        this.planB = planB;
        this.grupo = grupo;
        this.estado = estado;
        this.estudiante = estudiante;
        this.detalle=detalle;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getComentarioAdmin() {
        return comentarioAdmin;
    }

    public void setComentarioAdmin(String comentarioAdmin) {
        this.comentarioAdmin = comentarioAdmin;
    }
}
