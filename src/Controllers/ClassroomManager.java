package Controllers;

import Model.Aula;
import Model.DataHolder;
import Model.Grupo;
import Model.Horario;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ClassroomManager {

    @FXML
    private GridPane gridHorario;
    @FXML
    private ComboBox combo_Aulas;
    @FXML
    private Label lAula;
    @FXML
    private Label lCapacidad;

    Map<String, Integer> bHoursMap;
    Map<String, Integer> fHoursMap;
    Map<String, Integer> diasMap;

    private void fillGrid(){
        //Aula aula = DataHolder.getInstance().getAulas().get( (String) combo_Aulas.getValue());
        Aula aula = DataHolder.getInstance().getAulas().get( "B3-1");
        lAula.setText( "Aula: " + aula.getCodigo() );
        lCapacidad.setText( "Capacidad: " + aula.getCapacidad() );

        for (String key : DataHolder.getInstance().getGrupos().keySet()){
            Grupo grupo = DataHolder.getInstance().getGrupos().get(key);

            for (Horario horario : grupo.getHorario()){
                if (horario.getAula().getCodigo().equals(aula.getCodigo())){
                    System.out.println("Agregando al mapa");
                    addPane(grupo, horario);

                } else {
                    System.out.println(horario.getAula().getCodigo() + "   " +aula.getCodigo() );
                }
            }
        }

    }

    public void addPane(Grupo grupo, Horario horario){
        int span = fHoursMap.get(horario.getHoraFin()) - bHoursMap.get(horario.getHoraInicio()) + 1;
        Accordion accordion = new Accordion();
        TitledPane pane1 = new TitledPane("Boats" , new Label("Show all boats available"));

        pane1.setText(grupo.getCurso().getNombre());
        VBox box = new VBox();

        box.getChildren().add(new Label("Profe: " + grupo.getProfesor()));
        box.getChildren().add(new Label("Horario: " + horario.getHoras()));

        pane1.setContent(box);
        pane1.getWidth();
        accordion.getPanes().add(pane1);

        accordion.setExpandedPane(pane1);
        gridHorario.add(accordion, diasMap.get(horario.getDia()), bHoursMap.get(horario.getHoraInicio()), 1, span);
    }


    public void initialize(){
        Set<String> aulas = DataHolder.getInstance().getAulas().keySet();
        combo_Aulas.getItems().addAll(aulas);

        combo_Aulas.getSelectionModel().selectFirst();

        bHoursMap = new HashMap<>();
        bHoursMap.put("7:30", 1);
        bHoursMap.put("8:30", 2);
        bHoursMap.put("9:30", 3);
        bHoursMap.put("10:30", 4);
        bHoursMap.put("11:30", 5);
        bHoursMap.put("13:00", 6);
        bHoursMap.put("14:00", 7);
        bHoursMap.put("15:00", 8);
        bHoursMap.put("16:00", 9);
        bHoursMap.put("17:00", 10);
        bHoursMap.put("18:00", 11);
        bHoursMap.put("19:00", 12);
        bHoursMap.put("20:00", 13);
        bHoursMap.put("11:00", 14);

        fHoursMap = new HashMap<>();
        fHoursMap.put("8:20", 1);
        fHoursMap.put("9:20", 2);
        fHoursMap.put("10:20", 3);
        fHoursMap.put("11:20", 4);
        fHoursMap.put("12:20", 5);
        fHoursMap.put("13:50", 6);
        fHoursMap.put("14:50", 7);
        fHoursMap.put("15:50", 8);
        fHoursMap.put("16:50", 9);
        fHoursMap.put("17:50", 10);
        fHoursMap.put("18:50", 11);
        fHoursMap.put("19:50", 12);
        fHoursMap.put("20:50", 13);
        fHoursMap.put("21:50", 14);

        diasMap = new HashMap<>();
        diasMap.put("L", 1);
        diasMap.put("K", 2);
        diasMap.put("M", 3);
        diasMap.put("J", 4);
        diasMap.put("V", 5);
        diasMap.put("S", 6);

        ObservableList<RowConstraints> rows = gridHorario.getRowConstraints();
        for (RowConstraints row : rows){
            //row.setPrefHeight(200);
            row.setMinHeight(40);
        }

        for (ColumnConstraints column : gridHorario.getColumnConstraints()){
            column.setPrefWidth(200);
            column.setMinWidth(60);
        }

        fillGrid();
    }
}
