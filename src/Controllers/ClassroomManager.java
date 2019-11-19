package Controllers;

import Model.DataHolder;
import Model.Grupo;
import Model.Horario;
import javafx.beans.value.ChangeListener;
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
import java.util.*;


public class ClassroomManager {

    @FXML
    private ComboBox combo_Aulas;
    @FXML
    private Label lCapacidad;
    @FXML
    private Canvas canvas_horarioAula;
    private GraphicsContext gc;

    private Set<String> aulas;
    private final double anchoCelda = 190;
    private List<double[]> posiciones = new ArrayList<>();
    private List<Grupo> gruposEnPantalla = new ArrayList<>();
    Map<String, Integer> diasMap;
    Map<Integer, String> lecciones;

    private String diasLectivos[] = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

    private void drawOnCanvas(String codigoAula){
        clearCanvas();

        for (String key : DataHolder.getInstance().getGrupos().keySet()){
            Grupo grupo = DataHolder.getInstance().getGrupos().get(key);

            for (Horario horario : grupo.getHorario()){
                if (horario.getAula().equals(codigoAula)){
                    agregarLecciones(grupo, horario);
                }
            }
        }
    }

    public void agregarLecciones(Grupo grupo, Horario horario){
        gc.setFont(new Font("Calibri", 15));

        String body = textoDeCelda(grupo, horario);

        double x = diasMap.get(horario.getDia());
        double y = (horario.getHoraInicio().toSecondOfDay()/60) - 410;
        double height= (horario.getHoraSalida().toSecondOfDay()/60) - 410 -  y;
        int span = ((int) height / 55) - 2;

        System.out.println(x + ", " + y + " - " + height + ", "+ span);

        gc.setFill(Color.GRAY);
        gc.fillRect( x, y , anchoCelda, height);

        gc.setFill(Color.WHITE);
        gc.fillText(body,x+7.5,y + 33*span,anchoCelda-10);

        posiciones.add(new double[]{x,y});
        gruposEnPantalla.add(grupo);
    }

    private String textoDeCelda(Grupo grupo, Horario horario){
        String nombreCurso = grupo.getCurso().getNombre() + " GR " + grupo.getNumGrupo();
        String nombreProfesor = "Profesor: " + grupo.getProfesor().replace('\t', ' ');

        nombreCurso = ajustarTextoLargo(nombreCurso);
        nombreProfesor = ajustarTextoLargo(nombreProfesor);

        String body = "\n"+ nombreCurso;
        body += "\n Hora: " + horario.getHoraInicio() + "-" + horario.getHoraSalida();
        body += "\n " +  nombreProfesor;

        return body;
    }

    private String ajustarTextoLargo(String texto){
        StringBuilder textoAjustado = new StringBuilder();

        String parte = "";
        for (String splitString :  texto.split(" |\t") ) {
            if (parte.length() + splitString.length() > 25){
                textoAjustado.append(" ").append(parte).append("\n");
                parte = splitString  + " ";
            } else
                parte += splitString + " ";
        }
        textoAjustado.append(" ").append(parte);

        return textoAjustado.toString();
    }

    private void clearCanvas(){
        gc.clearRect(0, 0, canvas_horarioAula.getWidth(), canvas_horarioAula.getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, canvas_horarioAula.getWidth(), canvas_horarioAula.getHeight());

        drawLines();
        gruposEnPantalla.clear();
        posiciones.clear();
        drawDays();
        drawHours();
    }

    private void drawLines(){
        int baseY = 40;
        double HEIGHT = 110;
        for (int row = 0; row < 15; row++){
            gc.setFill(row%2==0? Color.LIGHTGRAY : Color.WHITE);
            gc.fillRect(0,baseY, canvas_horarioAula.getWidth(),HEIGHT);
            baseY+=55;
        }
    }

    private void drawDays(){
        int baseX = 150;

        gc.setFont(new Font("Roboto", 16));
        gc.setFill(Color.BLACK);
        for (String currentDay : diasLectivos){
            gc.fillText(currentDay, baseX, 25 );
            baseX+=200;
        }

        canvas_horarioAula.setWidth(baseX);
    }

    private void drawHours(){
        int baseY = 70;

        gc.setFont(new Font("Roboto", 16));
        gc.setFill(Color.BLACK);
        for (int i = 0; i < 15; i++){
            String hora = lecciones.get(i);
            gc.fillText(hora, 15, baseY,100);
            baseY+=55;
        }

        canvas_horarioAula.setHeight(baseY-45);
    }

    public void initialize(){
        this.gc = canvas_horarioAula.getGraphicsContext2D();

        aulas = new HashSet<>();// = DataHolder.getInstance().getAulas().keySet();
        for (Grupo grupo : DataHolder.getInstance().getGrupos().values()){
            for (Horario h : grupo.getHorario()){
                aulas.add(h.getAula().getCodigo());
            }
        }

        combo_Aulas.getItems().addAll(aulas);
        combo_Aulas.getSelectionModel().selectFirst();
        combo_Aulas.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> drawOnCanvas(t1));

        //Para hacer las pruebas, antes de seguir esto lo pasare a un CSV o alguna otra alternativa, pero ocupo esto en especifico

        diasMap = new HashMap<>();
        diasMap.put("LUNES",115);
        diasMap.put("MARTES",315);
        diasMap.put("MIERCOLES",515);
        diasMap.put("JUEVES",715);
        diasMap.put("VIERNES",915);
        diasMap.put("SABADO",1215);

        lecciones = new HashMap<>();
        lecciones.put(0, "7:30-8:20");
        lecciones.put(1, "8:30-9:20");
        lecciones.put(2, "9:30-10:20");
        lecciones.put(3, "10:30-11:20");
        lecciones.put(4, "11:30-12:20");
        lecciones.put(5, "12:30-12:50");
        lecciones.put(6, "13:00-13:50");
        lecciones.put(7, "14:00-14:50");
        lecciones.put(8, "15:00-15:50");
        lecciones.put(9, "16:00-16:50");
        lecciones.put(10, "17:00-17:50");
        lecciones.put(11, "18:00-18:50");
        lecciones.put(12, "19:00-19:50");
        lecciones.put(13, "20:00-20:50");
        lecciones.put(14, "21:00-21:50");


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
                    abrirEdicionGrupo(gruposEnPantalla.get(i));
                }
            }
        }

        private boolean isInRange(double[] coordClick, double[] coordTest){
            return coordTest[0] <= coordClick[0] && coordClick[0] <= (coordTest[0]+anchoCelda) &&
                    coordTest[1] <= coordClick[1] && coordClick[1] <= (coordTest[1]+15);
        }
    };

    private void abrirEdicionGrupo(Grupo group){
        System.out.println(group.toString());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/editgroup.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNIFIED);

            stage.setTitle(group.getCurso().getNombre() + " GR" + group.getNumGrupo());
            stage.setScene(new Scene(parent));
            stage.setMaxWidth(500);
            stage.setMinWidth(500);
            stage.show();

            EditGroupController controlador = fxmlLoader.getController();
            controlador.iniciar(group, aulas, lecciones, diasLectivos);

        }  catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
