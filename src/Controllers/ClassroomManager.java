package Controllers;

import Model.Aula;
import Model.DataHolder;
import Model.Grupo;
import Model.Horario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class ClassroomManager {

    @FXML
    private ComboBox combo_Aulas;
    @FXML
    private ScrollPane scroll_horario;
    @FXML
    private Label lCapacidad;
    @FXML
    private Canvas canvas_horarioAula;
    private GraphicsContext gc;

    int debugX = 0;
    int debugY = 0;

    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    Map<String, Integer> bHoursMap;
    Map<String, Integer> fHoursMap;
    Map<String, Integer> diasMap;

    private void drawOnCanvas(String codigoAula){
        clearCanvas();
        debugX = 0;
        debugY = 0;

        Aula aula = DataHolder.getInstance().getAulas().get( codigoAula );

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
        System.out.println(horario.toString());
        //int span = fHoursMap.get(horario.getHoraSalida()) - bHoursMap.get(horario.getHoraInicio()) + 1;
        String body = "\n  Horario: " + horario.getHoraInicio();
        body += "\n  Profesor: " + grupo.getProfesor();
        body += "\n  Aula: ";

        String title = grupo.getCurso().getId()+ " Grupo: " + grupo.getNumGrupo();

        gc.setFont(new Font("Calibri", 14));


        int x = 200 * debugX++ + 100;
        int y = 100 * debugY++ + 50;

        int width = 160;

        gc.setFill(Color.WHITE);
        gc.fillRoundRect(x,y ,width,100,15,15);
        gc.setFill(Color.GRAY);
        gc.fillRoundRect(x,y ,width,15,15,15);
        gc.fillRect(x,y+2,width,20);
        gc.setFill(Color.WHITE);
        gc.fillText(title,x+10,y+15,width);
        gc.setFill(Color.BLACK);
        gc.fillText(body,x+5,y+25,width-10);

    }

    private void pruebas(){
        gc.strokeRoundRect(10, 10, 100, 100, 10, 10);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 300, 50);
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 50, 300, 50);
    }

    private void clearCanvas(){
        gc.clearRect(0, 0, 1000, 1000);
        drawDays();
        drawHours();
    }

    private void drawDays(){
        int baseX = 120;
        int baseY = 20;

        String days[] = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        for (String currentDay : days){
            gc.setFill(Color.BLACK);
            gc.fillText(currentDay,baseX,baseY,100);
            baseX+=200;
        }

        canvas_horarioAula.setWidth(baseX);

    }

    private void drawHours(){
        int baseX = 20;
        int baseY = 120;

        for (int i = 0; i < 15; i++){
            String hora = bHoursMap.get(i) + " - " + fHoursMap.get(i);
            gc.setFill(Color.BLACK);
            gc.fillText(hora,baseX,baseY,100);
            baseY+=100;
        }

        canvas_horarioAula.setHeight(baseY);


    }



    public void initialize(){
        this.gc = canvas_horarioAula.getGraphicsContext2D();

        pruebas();

        Set<String> aulas = DataHolder.getInstance().getAulas().keySet();
        combo_Aulas.getItems().addAll(aulas);

        combo_Aulas.getSelectionModel().selectFirst();
        combo_Aulas.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                drawOnCanvas(t1);
            }
        });

        //Para hacer las pruebas, antes de seguir esto lo pasare a un CSV o alguna otra alternativa, pero ocupo esto en especifico
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
        bHoursMap.put("21:00", 14);

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
        diasMap.put("LUNES", 1);
        diasMap.put("MARTES", 2);
        diasMap.put("MIERCOLES", 3);
        diasMap.put("JUEVES", 4);
        diasMap.put("VIERNES", 5);
        diasMap.put("SABADO", 6);
    }

}
