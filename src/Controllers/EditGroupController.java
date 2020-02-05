package Controllers;

import Model.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//import jdk.nashorn.internal.runtime.logging.Logger;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class EditGroupController {
    @FXML
    GridPane grid_horarios;
    @FXML
    TextField tbox_profesor;

    private int filasGrid = 0;
    private Map<Integer, ComboBox[]> datosHorario;
    private List<String> horasInicio = new ArrayList<>();
    private List<String> horasSalida = new ArrayList<>();
    private String[] dias;
    private Set<String> aulas;
    private Grupo miGrupo;


    public void initialize(){
        filasGrid = 1;
        datosHorario = new HashMap<>();
    }

    public void iniciar(Grupo grupo, Set<String> aulas, Map<Integer, String> lecciones, String[] _dias){
        this.aulas = aulas;
        dias = new String[]{"LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO"};
        tbox_profesor.setText(grupo.getProfesor().replace('\t', ' '));
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

            ComboBox[] fila = {comboBoxAulas, combDia, horaInicio, horaFin};
            datosHorario.put(revisados, fila);

            box.getChildren().addAll(comboBoxAulas, combDia, horaInicio, horaFin, eliminar);
            grid_horarios.addRow(filasGrid++, comboBoxAulas, combDia, horaInicio, horaFin, eliminar);
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

        ComboBox[] fila = {comboBoxAulas, combDia, horaInicio, horaFin};
        datosHorario.put(filasGrid, fila);

        box.getChildren().addAll(comboBoxAulas, combDia, horaInicio, horaFin, eliminar);
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
            datosHorario.remove(index);
        }

    }

    public void guardarDatos(){
        Set<Integer> llaves = datosHorario.keySet();
        List<Horario> horarios = new ArrayList<>(); //Usar un mapa, int indice y valores de widgets

        for (int llave : llaves){
            ComboBox[] fila = datosHorario.get(llave);
            String codAula = fila[0].getValue().toString();
            String dia =  fila[1].getValue().toString();

            String horaInicio = fila[2].getValue().toString();
            String horaFin = fila[3].getValue().toString();

            Aula aula = DataHolder.getInstance().getAulas().get(codAula);
            Horario horario = new Horario(aula, dia, LocalTime.parse(horaInicio), LocalTime.parse(horaFin));
            horarios.add(horario);
        }

        if (horariosSonValidos(horarios)){
            miGrupo.setHorario(horarios);
            String profesor = tbox_profesor.getText();
            miGrupo.setProfesor(profesor);


            try {
                DataHolder.getInstance().saveGroups();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Modificación del grupo");
                a.setContentText("Se guardaron los cambios");
                a.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Modificación del grupo");
                a.setContentText("No se lograron guardar los cambios");
                a.show();
            }

        }


    }

    private boolean horariosSonValidos(List<Horario> horariosCambio){

        for (Horario horario : horariosCambio){
            int hInicio = horario.getHoraInicio().toSecondOfDay();
            int hSalida = horario.getHoraSalida().toSecondOfDay();

            if (hSalida < hInicio){
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("La hora de inicio debe de ser antes de la hora de salida.");
                a.setTitle("Horas de la clase");
                a.show();
                return false;
            }
        }

        return !hayChoqueDeHorario(horariosCambio);
    }

    private boolean hayChoqueDeHorario(List<Horario> horariosCambio){
        String listadoErrores = "";
        for (Horario hoCambio : horariosCambio){ //Por cada fila de clases
            Aula aula = hoCambio.getAula();

            for (String key : DataHolder.getInstance().getGrupos().keySet()){
                Grupo grupo = DataHolder.getInstance().getGrupos().get(key);

                if (grupo.equals(miGrupo)) continue; //No va a tener conflicto con si mismo

                for (Horario hoActual : grupo.getHorario()){
                    //Si no hay choque
                    if (!hoCambio.choqueDeHorario(hoActual)) continue;

                    //Aqui si hay choque, entonces
                    Curso curso = grupo.getCurso();
                    String error = "Choque de horario con " + curso.getNombre();
                    error += " GR" + grupo.getNumGrupo();
                    error += " (Aula " + aula.getCodigo();
                    error += ", " + hoActual.getHoraInicio().toString() + "-" + hoActual.getHoraSalida() + ")";
                    listadoErrores+= error + "\n";
                }
            }

        }

        if (!listadoErrores.equals("")){
            TextArea area = new TextArea(listadoErrores);
            area.setWrapText(true);
            area.setEditable(false);

            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.getDialogPane().setContent(area);
            a.setTitle("Choques de horario");
            a.show();
            return true;
        }

        return false;
    }

    public void closeWindow(){
        Stage stage = (Stage) tbox_profesor.getScene().getWindow();
        stage.close();
    }
}
