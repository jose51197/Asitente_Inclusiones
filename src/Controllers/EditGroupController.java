package Controllers;

import Model.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

            ComboBox fila[] = {comboBoxAulas, combDia, horaInicio, horaFin};
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

        ComboBox fila[] = {comboBoxAulas, combDia, horaInicio, horaFin};
        datosHorario.put(filasGrid, fila);

        box.getChildren().addAll(comboBoxAulas, combDia, horaInicio, horaFin, eliminar);
        //grid_horarios.addRow(filasGrid++, box);
        grid_horarios.addRow(filasGrid++, comboBoxAulas, combDia, horaInicio, horaFin, eliminar);
    }

    private void eliminarFila(int index){
        System.out.println("Eliminando: " + index);
        System.out.println(grid_horarios.getChildren() == null);
        List<Node> toRemove = new ArrayList<>();

        //grid_horarios.getChildren().removeIf(n -> GridPane.getRowIndex(n) == index);

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
            ComboBox fila[] = datosHorario.get(llave);
            String codAula = fila[0].getValue().toString();
            String dia =  fila[1].getValue().toString();

            String horaInicio = fila[2].getValue().toString();
            String horaFin = fila[3].getValue().toString();

            Aula aula = DataHolder.getInstance().getAulas().get(codAula);
            System.out.println(horaInicio + " - " + horaFin);
            System.out.println(LocalTime.parse("09:30"));
            System.out.println(horaInicio + " - " + horaFin);
            Horario horario = new Horario(aula, dia, LocalTime.parse(horaInicio), LocalTime.parse(horaFin));
            horarios.add(horario);
        }

        if (horariosSonValidos(horarios)){
            System.out.println("Todo bien con la modificacion");
            miGrupo.setHorario(horarios);
            String profesor = tbox_profesor.getText();
            miGrupo.setProfesor(profesor);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Choques de horario");
            a.setContentText("Se guardaron los cambios");
            a.show();
            try {
                DataHolder.getInstance().saveStatus();
            } catch (IOException e) {
                e.printStackTrace();
            }

            closeWindow();
        }




        //Setear el grupo
        //
       // miGrupo.setHorario(horarios);


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

        return !hayColisionHorarios(horariosCambio);
    }

    private boolean hayColisionHorarios(List<Horario> horariosCambio){
        String listadoErrores = "";
        for (Horario horarioC : horariosCambio){
            Aula aula = horarioC.getAula();
            int hnInicio = horarioC.getHoraInicio().toSecondOfDay();
            int hnSalida = horarioC.getHoraSalida().toSecondOfDay();

            for (String key : DataHolder.getInstance().getGrupos().keySet()){
                Grupo grupo = DataHolder.getInstance().getGrupos().get(key);

                if (grupo.equals(miGrupo)) continue; //No va a tener conflicto con si mismo

                for (Horario horario : grupo.getHorario()){

                    if (horario.getAula().getCodigo().equals(aula.getCodigo())){
                        int hcInicio = horario.getHoraInicio().toSecondOfDay();
                        int hcSalida = horario.getHoraSalida().toSecondOfDay();

                        if (colisionHoras( hnInicio, hnSalida, hcInicio, hcSalida )){
                            Curso curso = grupo.getCurso();
                            String error = "Choque de horario con " + curso.getNombre();
                            error += " GR" + grupo.getNumGrupo();
                            error += " (Aula " + aula.getCodigo();
                            error += ", " + horario.getHoraInicio().toString() + "-" + horario.getHoraSalida() + ")";
                            listadoErrores+= error + "\n";
                        }
                    }
                }
            }

        }

        if (listadoErrores != ""){
            TextArea area = new TextArea(listadoErrores);
            area.setWrapText(true);
            area.setEditable(false);

            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.getDialogPane().setContent(area);
            a.setTitle("Choques de horario");
            //a.setContentText("Se presentaron los siguientes errores al cargar los datos.");
            // show the dialog
            a.show();
            return true;
        }

        return false;
    }

    private boolean colisionHoras(int haInicio, int haSalida, int hbInicio, int hbSalida){
        if ( haInicio <= hbInicio && hbSalida <= haSalida )
            return true;
        else return hbInicio <= haInicio && haSalida <= hbSalida;
    }




    public void closeWindow(){
        Stage stage = (Stage) tbox_profesor.getScene().getWindow();
        stage.close();
    }
}
