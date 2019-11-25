package Controllers;

import Model.Aula;
import Model.DataHolder;
import Model.Grupo;
import Model.Horario;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.logging.Logger;

import java.time.LocalTime;
import java.util.*;

public class EditGroupController {
    @FXML
    GridPane grid_horarios;
    @FXML
    TextField label_profesor;
    @FXML
    VBox horariosGrupo;

    int filasGrid = 0;
    Map<Integer, ComboBox[]> datosHorario;
    List<String> horasInicio = new ArrayList<>();
    List<String> horasSalida = new ArrayList<>();
    String dias[];
    Set<String> aulas;

    Grupo miGrupo;

    public void initialize(){
        filasGrid = 1;
        datosHorario = new HashMap<>();
    }

    public void iniciar(Grupo grupo, Set<String> aulas, Map<Integer, String> lecciones, String _dias[]){
        this.aulas = aulas;
        dias = new String[]{"LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO"};
        label_profesor.setText(grupo.getProfesor().replace('\t', ' '));
        miGrupo = grupo;

        for (int i = 0; i < 15; i++){
            String horas[] = lecciones.get(i).split("-");
            horasInicio.add(horas[0]);
            horasSalida.add(horas[1]);
        }

        for (int revisados = 0; revisados < grupo.getHorario().size(); revisados++){
            Horario h = grupo.getHorario().get(revisados);
            HBox box = new HBox();
            box.setMinWidth(400);
            box.setMinHeight(35);
            box.setAlignment(Pos.CENTER);

            ComboBox comboBoxAulas = new ComboBox();
            comboBoxAulas.getItems().addAll(aulas);
            comboBoxAulas.setMinWidth(50);
            comboBoxAulas.getSelectionModel().select(h.getAula().getCodigo());


            ComboBox combDia = new ComboBox();
            combDia.getItems().addAll(dias);
            combDia.setMinWidth(50);
            combDia.setMaxWidth(100);
            combDia.getSelectionModel().select(h.getDia());


            ComboBox horaInicio = new ComboBox();
            horaInicio.getItems().addAll(horasInicio);
            horaInicio.setMinWidth(50);
            horaInicio.setMaxWidth(75);
            horaInicio.getSelectionModel().select(h.getHoraInicio());


            ComboBox horaFin = new ComboBox();
            horaFin.getItems().addAll(horasSalida);
            horaFin.setMinWidth(50);
            horaFin.setMaxWidth(75);
            horaFin.getSelectionModel().select(h.getHoraSalida());


            Button eliminar = new Button("Eliminar");


            final int parameter = revisados;
            eliminar.setOnMouseClicked( e -> eliminarFila(parameter) );

            ComboBox fila[] = {combDia, comboBoxAulas, horaInicio, horaFin};
            datosHorario.put(revisados, fila);

            box.getChildren().addAll(comboBoxAulas, combDia, horaInicio, horaFin, eliminar);
            grid_horarios.addRow(filasGrid++, comboBoxAulas, combDia, horaInicio, horaFin, eliminar);
            //grid_horarios

        }
    }

    public void agregarFila(){
        System.out.println("Total de filas " + filasGrid);
        HBox box = new HBox();
        box.setMinWidth(400);
        box.setMinHeight(35);
        box.setAlignment(Pos.CENTER);

        ComboBox comboBoxAulas = new ComboBox();
        comboBoxAulas.getItems().addAll(aulas);
        comboBoxAulas.setMinWidth(50);
        comboBoxAulas.getSelectionModel().selectFirst();

        ComboBox combDia = new ComboBox();
        combDia.getItems().addAll(dias);
        combDia.setMinWidth(50);
        combDia.setMaxWidth(100);
        combDia.getSelectionModel().selectFirst();

        ComboBox horaInicio = new ComboBox();
        horaInicio.getItems().addAll(horasInicio);
        horaInicio.setMinWidth(50);
        horaInicio.setMaxWidth(75);
        horaInicio.getSelectionModel().selectFirst();

        ComboBox horaFin = new ComboBox();
        horaFin.getItems().addAll(horasSalida);
        horaFin.setMinWidth(50);
        horaFin.setMaxWidth(75);
        horaFin.getSelectionModel().selectFirst();

        Button eliminar = new Button("Eliminar");
        final int parameter = filasGrid;
        eliminar.setOnMouseClicked( e -> eliminarFila(parameter) );

        ComboBox fila[] = {combDia, comboBoxAulas, horaInicio, horaFin};
        datosHorario.put(filasGrid, fila);

        box.getChildren().addAll(comboBoxAulas, combDia, horaInicio, horaFin, eliminar);
        //grid_horarios.addRow(filasGrid++, box);
        grid_horarios.addRow(filasGrid++, comboBoxAulas, combDia, horaInicio, horaFin, eliminar);
    }

    private void eliminarFila(int index){
        System.out.println("Eliminando: " + index);
        System.out.println(grid_horarios.getChildren() == null);
        List<Node> toRemove = new ArrayList<>();

        for (Node node : grid_horarios.getChildren()){
            System.out.println( GridPane.getRowIndex(node) );
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == index)
                toRemove.add(node);
        }

        if (toRemove.size() > 0){
            for (Node node : toRemove){
                grid_horarios.getChildren().remove(node);
            }
            //grid_horarios.getChildren().removeIf(node -> GridPane.getRowIndex(node) == index);
            //filasGrid--;
        }


    }

    public void guardarDatos(){
        Set<Integer> llaves = datosHorario.keySet();
        //Usar un mapa, int indice y valores de widgets
        List<Horario> horarios = new ArrayList<>();

        for (int llave : llaves){
            ComboBox fila[] = datosHorario.get(llave);
            String codAula = fila[0].getValue().toString();
            String dia =  fila[1].getValue().toString();

            String horaInicio = fila[2].getValue().toString();
            String horaFin = fila[3].getValue().toString();
            Aula aula= DataHolder.getInstance().getAulas().get(codAula);
            System.out.println(aula.toString());
            Horario horario = new Horario(aula, dia, LocalTime.parse(horaInicio), LocalTime.parse(horaFin));
            horarios.add(horario);
        }
        //Setear el grupo
        //
       // miGrupo.setHorario(horarios);
        closeWindow();
    }


    public void closeWindow(){
        Stage stage = (Stage) label_profesor.getScene().getWindow();
        stage.close();
    }
}
