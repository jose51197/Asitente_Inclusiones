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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class ViewInclusion {
    public Label lcorrequistos;
    public Label labelPlanB;
    public Label labelHorario;
    public TextFlow lcomentario;
    @FXML Label lestado;
    @FXML Label lmateria;
    @FXML Label lrequisitos;
    @FXML Label labelEstadisticas;
    private Inclusion inclusion;

    public void btn_rechazar(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.RECHAZADA);
        lestado.setText("Rechazada");
        lestado.setTextFill(Color.DARKRED);
        setInclusion(this.inclusion);//TODO llamar a un dialogo que ingrese la razon de cancelacion
        try {
            DataHolder.getInstance().saveStatus();
        } catch (IOException e) {
            e.printStackTrace();// TODO anadir error
        }
    }

    public void btn_aceptar(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.ACEPTADA);
        lestado.setTextFill(Color.BLUE);
        lestado.setText("Aceptada");
        setInclusion(this.inclusion);
        try {
            DataHolder.getInstance().saveStatus();
        } catch (IOException e) {
            e.printStackTrace();// TODO anadir error
        }
    }

    public void btn_pendiente(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.EN_PROCESO);
        lestado.setTextFill(Color.BLUEVIOLET);
        lestado.setText("Pendiente");
        setInclusion(this.inclusion);
        try {
            DataHolder.getInstance().saveStatus();
        } catch (IOException e) {
            e.printStackTrace();// TODO anadir error
        }
    }

    public void btn_cancelar(ActionEvent actionEvent) {
        inclusion.setEstado(EstadoInclusion.CANCELADA);
        lestado.setTextFill(Color.BLACK);
        lestado.setText("Cancelada");
        setInclusion(this.inclusion);
        try {
            DataHolder.getInstance().saveStatus();
        } catch (IOException e) {
            e.printStackTrace();// TODO anadir error
        }
    }

    public void setInclusion(Inclusion inclusion) {
        this.inclusion = inclusion;
        lestado.setText(inclusion.getEstado().toString());
        lmateria.setText(inclusion.getGrupo().getCurso().getNombre()+ " GR "+ inclusion.getGrupo().getNumGrupo() +"\n"+inclusion.getGrupo().getProfesor());
        if(inclusion.isPlanB()){
            labelPlanB.setTextFill(Color.RED);
            labelPlanB.setText("PIDE PLAN B");
        }else{
            labelPlanB.setText("Inclusion normal");
        }

        String requisitos="";
        for (Curso curso: inclusion.getGrupo().getCurso().getRequisitos()) {
            if(curso.getNombre().length()>20){
                String estado = inclusion.getEstudiante().getCursos().get((curso.getId()));
                if (estado==null){
                    estado="Sin info";
                }
                requisitos+=GenericFunctions.splitByNumber(curso.getNombre(),20)[0] + "-"+estado;
                continue;
            }
            requisitos+=curso.getNombre() + "\n";
        }
        lrequisitos.setText(requisitos);
        String corequisitos="";
        for (Curso curso: inclusion.getGrupo().getCurso().getCorequisitos()) {
            if(curso.getNombre().length()>20){
                String estado = inclusion.getEstudiante().getCursos().get((curso.getId()));
                if (estado==null){
                    estado="Sin info";
                }
                corequisitos+=GenericFunctions.splitByNumber(curso.getNombre(),20)[0] +"-" + estado;
                continue;
            }
            corequisitos+=curso.getNombre()+"\n";
        }
        lcorrequistos.setText(corequisitos);
        lcomentario.getChildren().add(new Text(inclusion.getDetalle()));
        ArrayList<Inclusion> inclusiones = DataHolder.getInstance().getInclusionesMapPorMateria().get(inclusion.getGrupo().getCurso().getId());
        String estadisticas="Total recibidas: "+ inclusiones.size()+" Matriculados: "+inclusion.getGrupo().getCantEstudiantes() +"\n";
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
        labelHorario.setText(inclusion.getGrupo().getHorariotext());
        labelEstadisticas.setText(estadisticas);
    }
}
