package Controllers;

import Model.Curso;
import Model.DataHolder;
import Model.EstadoInclusion;
import Model.Inclusion;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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
        String comentarioAdmin=inclusion.getComentarioAdmin().replace("linebreaku","\n");//Querido Javier, si usted ve esto, es culpa de Sergie Atte Jose
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Rechazo");
        dialog.setHeaderText("Ingrese la razón del rechazo");
        ButtonType loginButtonType = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextArea razon = new TextArea();
        razon.setText(comentarioAdmin);
        razon.setPromptText("Razón");
        grid.add(new Label("Razón:"), 0, 0);
        grid.add(razon, 1, 0);
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);
        //deshabilito si no hay texto
        razon.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> razon.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return razon.getText();
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            inclusion.setEstado(EstadoInclusion.RECHAZADA);
            inclusion.setComentarioAdmin(result.get().replace("\n","linebreaku"));
            lestado.setText("Rechazada");
            lestado.setTextFill(Color.DARKRED);
            setInclusion(this.inclusion);
            try {
                DataHolder.getInstance().saveStatus();
            } catch (IOException e) {
                e.printStackTrace();// TODO anadir error
            }
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
        lcomentario.getChildren().clear();
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
