package Model;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

public class Inclusion {
    private boolean planB;
    private Grupo grupo;
    private EstadoInclusion estado;
    private Estudiante estudiante;
    private String detalle;
    private String comentarioAdmin="";
    private String correo="";


    public Inclusion(boolean planB, Grupo grupo,  Estudiante estudiante, String detalle, String correo) {
        this.planB = planB;
        this.grupo = grupo;
        this.estado = EstadoInclusion.EN_PROCESO;
        this.estudiante = estudiante;
        this.detalle=detalle;
        this.correo=correo;
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

    public String getCorreo() {
        return correo;
    }

    //FUNCIONES para mostrar NO TOCAR
    public String getNombre(){
        return estudiante.getNombre();
    }
    public String getMateria(){
        return grupo.getCurso().getNombre() + " - GR "+grupo.getNumGrupo();
    }
    public String getCodGrupo(){
        return grupo.getCurso().getId() + " - GR"+grupo.getNumGrupo()+" - "+grupo.getCurso().getNombre();
    }
    public String getEstadoString(){
        return estado.toString();
    }
    public String getPonderado(){return String.valueOf(estudiante.getPonderado());};
    public String getCarne(){return String.valueOf(estudiante.getCarnet());}

}
