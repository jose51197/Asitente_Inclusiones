package Controllers;

import Model.Aula;
import Model.DataHolder;
import Model.Grupo;
import Model.Horario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class ClassroomManager {

    @FXML
    private ComboBox combo_Aulas;
    @FXML
    private Label lCapacidad;
    @FXML
    private Canvas canvas_horarioAula;
    private GraphicsContext gc;

    private final double anchoCelda = 160;
    private List<double[]> posiciones = new ArrayList<>();
    private List<Grupo> gruposEnPantalla = new ArrayList<>();
    Map<String, Integer> diasMap;

    private String diasLectivos[] = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

    private void drawOnCanvas(String codigoAula){
        clearCanvas();

        Aula aula = DataHolder.getInstance().getAulas().get( codigoAula );
        if (aula == null) return;

        for (String key : DataHolder.getInstance().getGrupos().keySet()){
            Grupo grupo = DataHolder.getInstance().getGrupos().get(key);

            for (Horario horario : grupo.getHorario()){
                System.out.println(horario.getHoraInicio().toString());
                if (horario.getAula().equals(aula.getCodigo())){
                    agregarLecciones(grupo, horario);
                }
            }
        }
    }

    public void agregarLecciones(Grupo grupo, Horario horario){
        String body = "\n  Horario: " + horario.getHoraInicio();
        body += "\n  Profesor: " + grupo.getProfesor();
        body += "\n  Aula: ";

        String title = grupo.getCurso().getId()+ " Grupo: " + grupo.getNumGrupo();
        gc.setFont(new Font("Calibri", 14));



        double x = diasMap.get(horario.getDia());
        double y = (horario.getHoraInicio().toSecondOfDay()/60) - 420;
        double height=(horario.getHoraSalida().toSecondOfDay()/60) - 420 - y;

        gc.setFill(Color.WHITE);
        gc.fillRoundRect(x,y , anchoCelda, height,15,15);
        gc.setFill(Color.GRAY);
        gc.fillRoundRect(x,y ,anchoCelda,15,15,15);
        gc.fillRect(x,y+2, anchoCelda,20);
        gc.setFill(Color.WHITE);
        gc.fillText(title,x+10,y+15, anchoCelda);
        gc.setFill(Color.BLACK);
        gc.fillText(body,x+5,y+25,anchoCelda-10);

        posiciones.add(new double[]{x,y});
        gruposEnPantalla.add(grupo);
    }

    private void clearCanvas(){
        gc.clearRect(0, 0, canvas_horarioAula.getWidth(), canvas_horarioAula.getHeight());
        gruposEnPantalla.clear();
        posiciones.clear();
        drawDays();
        drawHours();
    }

    private void drawLines(){
        int baseY = 50;
        double HEIGHT = 200;
        for (int row = 0; row < 15; row++){
            gc.setFill(row%2==0? Color.GRAY : Color.WHITE);
            gc.fillRect(0,baseY, anchoCelda,HEIGHT);
            baseY+=200;
        }
    }

    private void drawDays(){
        int baseX = 160;

        for (String currentDay : diasLectivos){
            gc.setFill(Color.BLACK);
            gc.fillText(currentDay, baseX, 20 ,60);
            baseX+=200;
        }

        canvas_horarioAula.setWidth(baseX);
    }

    private void drawHours(){
        int baseY = 120;

        for (int i = 0; i < 15; i++){
            //String hora = bHoursMap.get(i) + " - " + fHoursMap.get(i);
            gc.setFill(Color.BLACK);
            //gc.fillText(hora, 20, baseY,100);
            baseY+=100;
        }
    }

    public void initialize(){
        this.gc = canvas_horarioAula.getGraphicsContext2D();

        Set<String> aulas = new HashSet<>();// = DataHolder.getInstance().getAulas().keySet();
        for (Grupo grupo : DataHolder.getInstance().getGrupos().values()){
            for (Horario h : grupo.getHorario()){
                aulas.add(h.getAula());
            }
        }
        combo_Aulas.getItems().addAll(aulas);
        combo_Aulas.getSelectionModel().selectFirst();
        combo_Aulas.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> drawOnCanvas(t1));

        //Para hacer las pruebas, antes de seguir esto lo pasare a un CSV o alguna otra alternativa, pero ocupo esto en especifico

        diasMap = new HashMap<>();
        diasMap.put("LUNES",50);
        diasMap.put("MARTES",250);
        diasMap.put("MIERCOLES",450);
        diasMap.put("JUEVES",650);
        diasMap.put("VIERNES",850);
        diasMap.put("SABADO",1150);

        drawOnCanvas("B3-8");
        canvas_horarioAula.setOnMouseClicked(seleccionGrupo);
    }

    private EventHandler<MouseEvent> seleccionGrupo = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (!(event.getEventType() == MouseEvent.MOUSE_CLICKED)) return;
            double[] coordClick = {event.getX(), event.getY()};

            for (int i = 0; i < posiciones.size(); i++){
                double[] coord = posiciones.get(i);
                if (isInRange(coordClick, coord)){
                    popUp(gruposEnPantalla.get(i));
                }
            }
        }

        private boolean isInRange(double[] coordClick, double[] coordTest){
            return coordTest[0] <= coordClick[0] && coordClick[0] <= (coordTest[0]+anchoCelda) &&
                    coordTest[1] <= coordClick[1] && coordClick[1] <= (coordTest[1]+15);
        }
    };

    private void popUp(Grupo group){
        System.out.println(group.toString());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/editgroup.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNIFIED);

            stage.setTitle("Editar grupo");
            stage.setScene(new Scene(parent));
            stage.show();
            EditGroupController controlador = fxmlLoader.getController();
            controlador.iniciar(group);

        }  catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
