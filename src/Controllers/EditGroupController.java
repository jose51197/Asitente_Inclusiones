package Controllers;

import Model.Grupo;
import Model.Horario;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.LocalTime;
import java.util.*;

public class EditGroupController {
    @FXML
    GridPane grid_horarios;
    int filasGrid;
    Map<Integer, ComboBox[]> datosHorario;

    Grupo miGrupo;

    public void initialize(){
        filasGrid = 3;
        datosHorario = new HashMap<>();
    }

    public void iniciar(Grupo grupo){
        miGrupo = grupo;

        for (int revisados = 0; revisados < grupo.getHorario().size(); revisados++){
            HBox box = new HBox();

            ComboBox comboBoxAulas = new ComboBox();
            ComboBox combDia = new ComboBox();
            ComboBox horaInicio = new ComboBox();
            ComboBox horaFin = new ComboBox();
            Button eliminar = new Button("Eliminar");
            eliminar.setOnMouseClicked( e -> eliminarFila(filasGrid) );

            ComboBox fila[] = {comboBoxAulas, combDia, horaInicio, horaFin};
            datosHorario.put(revisados, fila);

            box.getChildren().addAll(comboBoxAulas, combDia, horaInicio, horaFin, eliminar);
            grid_horarios.addRow(filasGrid++, box);
        }
    }

    public void agregarFila(){
        HBox box = new HBox();
        ComboBox comboBoxAulas = new ComboBox();
        ComboBox horaInicio = new ComboBox();
        ComboBox horaFin = new ComboBox();
        Button eliminar = new Button("Eliminar");

        box.getChildren().addAll(comboBoxAulas, horaInicio, horaFin, eliminar);
        grid_horarios.addRow(filasGrid++, box);
    }

    private void eliminarFila(int index){
        grid_horarios.getRowConstraints().remove(index);
    }

    private void guardarDatos(){
        Set<Integer> llaves = datosHorario.keySet();
        //Usar un mapa, int indice y valores de widgets
        List<Horario> horarios = new ArrayList<>();

        for (int llave : llaves){
            ComboBox fila[] = datosHorario.get(llave);
            String codAula = fila[0].getValue().toString();
            String dia =  fila[1].getValue().toString();

            String horaInicio = fila[2].getValue().toString();
            String horaFin = fila[3].getValue().toString();

            Horario horario = new Horario(codAula, dia, LocalTime.parse(horaInicio), LocalTime.parse(horaFin));
            horarios.add(horario);
        }


        //Setear el grupo
        //miGrupo.setHorario(horarios);
    }
}
