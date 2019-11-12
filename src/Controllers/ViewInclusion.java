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
    private Inclusion inclusion;

    public void btn_rechazar(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.RECHAZADA);
        lestado.setText("Rechazada");
        lestado.setTextFill(Color.DARKRED);
    }

    public void btn_aceptar(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.ACEPTADA);
        lestado.setTextFill(Color.BLUE);
    }

    public void btn_pendiente(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.EN_PROCESO);
        lestado.setTextFill(Color.BLUEVIOLET);
    }

    public void btn_cancelar(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.CANCELADA);
        lestado.setTextFill(Color.BLACK);
    }

    public void setInclusion(Inclusion inclusion) {
        this.inclusion = inclusion;
        lestado.setText(inclusion.getEstado().toString());
        lmateria.setText(inclusion.getNombre());
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
