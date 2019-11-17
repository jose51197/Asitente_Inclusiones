package Controllers;

import Model.Curso;
import Model.DataHolder;
import Model.EstadoInclusion;
import Model.Inclusion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.util.ArrayList;

public class ViewInclusion {
    public Label lcorrequistos;
    @FXML TextArea lcomentario;
    @FXML Label lestado;
    @FXML Label lmateria;
    @FXML Label lrequisitos;
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
            if(curso.getNombre().length()>20){
                requisitos+=GenericFunctions.splitByNumber(curso.getNombre(),30)[0];
                continue;
            }
            requisitos+=curso.getNombre() + "\n";
        }
        lrequisitos.setText(requisitos);
        String corequisitos="";
        for (Curso curso: inclusion.getGrupo().getCurso().getCorequisitos()) {
            if(curso.getNombre().length()>20){
                corequisitos+=GenericFunctions.splitByNumber(curso.getNombre(),30)[0];
                continue;
            }
            corequisitos+=curso.getNombre()+"\n";
        }
        lcorrequistos.setText(corequisitos);
        lcomentario.setText(inclusion.getDetalle());
        labelEstadisticas.setText("Holi");
        ArrayList<Inclusion> inclusiones = DataHolder.getInstance().getInclusionesMapPorMateria().get(inclusion.getGrupo().getCurso().getId());
        String estadisticas="Total recibidas: "+ inclusiones.size()+"\n";
        int pendientes=0;
        int aceptadas=0;
        int rechazadas=0;
        for(Inclusion i : inclusiones){
            switch (i.getEstado()){
                case EN_PROCESO:
                    pendientes+=1;break;
                case ACEPTADA:
                    aceptadas+=1;break;
                case RECHAZADA:
                    rechazadas+=1;break;
                case CANCELADA:
                    break;
            }
        }
        estadisticas+="Pendientes: "+ pendientes+'\t';
        estadisticas+="Aceptadas: "+ aceptadas+'\t';
        estadisticas+="Rechazadas: "+ rechazadas;
        labelEstadisticas.setText(estadisticas);

    }
}
