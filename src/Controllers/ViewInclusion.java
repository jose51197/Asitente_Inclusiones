package Controllers;

import Model.Curso;
import Model.EstadoInclusion;
import Model.Inclusion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import javax.swing.*;

public class ViewInclusion {
    @FXML TextArea lcomentario;
    @FXML Label lestado;
    @FXML Label lmateria;
    @FXML Label lrequisitos;
    @FXML Label lcorrequisitos;
    @FXML Label labelEstadisticas;
    private Inclusion inclusion;

    public void btn_rechazar(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.RECHAZADA);
        lestado.setText("Rechazada");
        lestado.setTextFill(Color.DARKRED);
    }

    public void btn_aceptar(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.ACEPTADA);
        lestado.setTextFill(Color.BLUE);
        lestado.setText("Aceptada");
    }

    public void btn_pendiente(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.EN_PROCESO);
        lestado.setTextFill(Color.BLUEVIOLET);
        lestado.setText("Pendiente");
    }

    public void btn_cancelar(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.CANCELADA);
        lestado.setTextFill(Color.BLACK);
        lestado.setText("Cancelada");
    }

    public void setInclusion(Inclusion inclusion) {
        this.inclusion = inclusion;
        lestado.setText(inclusion.getEstado().toString());
        lmateria.setText(inclusion.getGrupo().getCurso().getNombre()+ " GR "+ inclusion.getGrupo().getNumGrupo());
        String requisitos="";
        for (Curso curso: inclusion.getGrupo().getCurso().getRequisitos()) {
            requisitos+=curso.getNombre() + "\n";
        }
        lrequisitos.setText(requisitos);
        String corequisitos="";
        for (Curso curso: inclusion.getGrupo().getCurso().getCorequisitos()) {
            corequisitos+=curso.getNombre()+"\n";
        }
        //lcorrequisitos.setText(corequisitos);
        lcomentario.setText(inclusion.getDetalle());

    }
}
