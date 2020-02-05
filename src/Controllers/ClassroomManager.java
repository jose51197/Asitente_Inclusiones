package Controllers;

import Model.Aula;
import Model.DataHolder;
import Model.Grupo;
import Model.Horario;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;


public class ClassroomManager {

    @FXML
    private ComboBox combo_Aulas;
    @FXML
    private Label lCapacidad;
    @FXML
    private Canvas canvas_horarioAula;
    @FXML
    private Pane basePane;
    private GraphicsContext gc;

    //Resources
    private Set<String> aulas;
    private final double anchoCelda = 190;
    Map<String, Integer> diasMap;
    Map<Integer, String> lecciones;

    //I could have a map with the start and end of each one
    //I wont recalculate every time
    private List<Integer> gridLines = new ArrayList<>();
    private List<ScrollPane> panesOnScreen = new ArrayList<>();


    private String diasLectivos[] = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

    private void drawOnCanvas(String codigoAula){
        Aula aula = DataHolder.getInstance().getAulas().get(codigoAula);

        basePane.getChildren().clear();

        lCapacidad.setText( "Capacidad:" + Integer.toString(aula.getCapacidad()) );
        for (String key : DataHolder.getInstance().getGrupos().keySet()){
            Grupo grupo = DataHolder.getInstance().getGrupos().get(key);

            for (Horario horario : grupo.getHorario()){

                if (horario.getAula().getCodigo().equals(codigoAula)){
                    agregarLecciones(grupo, horario);
                }
            }
        }
    }

    public void agregarLecciones(Grupo grupo, Horario horario){
        System.out.println("Agregando ... ");
        double x = diasMap.get(horario.getDia());
        double y = (horario.getHoraInicio().toSecondOfDay()/60) - 410; //
        double height= (horario.getHoraSalida().toSecondOfDay()/60) - 410 -  y;

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(anchoCelda+20,height);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(4.5);

        grid.setPadding(new Insets(2.5, 10, 2.5, 10));

        Label header = new Label(grupo.getCurso().getNombre() + " GR " + grupo.getNumGrupo());
        header.setWrapText(true);
        header.setFont(new Font("Roboto", 14));
        header.setMaxWidth(anchoCelda);

        Label classTime = new Label("Hora: " + horario.getHoraInicio() + "-" + horario.getHoraSalida());
        classTime.setWrapText(true);
        classTime.setFont(new Font("Roboto", 14));
        classTime.setMaxWidth(anchoCelda);
        classTime.setAlignment(Pos.CENTER);

        Label teacher =  new Label( "Profesor: " + grupo.getProfesor().replace('\t', ' '));
        teacher.setWrapText(true);
        teacher.setMaxWidth(anchoCelda);

        Button editBtn = new Button("Editar");
        editBtn.setAlignment(Pos.CENTER);
        editBtn.setStyle("-fx-base: blue;");
        editBtn.setOnMouseClicked(event -> abrirEdicionGrupo(grupo));

        grid.add( header, 0, 0);
        grid.add( classTime, 0, 1, 2, 1);
        grid.add( editBtn, 0, 2, 2,1);
        grid.add( teacher, 0, 3);

        scrollPane.setContent(grid);
        scrollPane.setLayoutX(x);
        scrollPane.setLayoutY(y);

        GridPane.setHalignment(editBtn, HPos.CENTER); // To align horizontally in the cell

        basePane.getChildren().add(scrollPane);
        panesOnScreen.add(scrollPane);
    }

    private void clearCanvas(){
        gc.clearRect(0, 0, canvas_horarioAula.getWidth(), canvas_horarioAula.getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, canvas_horarioAula.getWidth(), canvas_horarioAula.getHeight());

        drawLines();
        drawDays();
    }

    private void drawLines(){
        int startY = gridLines.get(0)/60 - 410; //40 at first
        int endY = 0;

        gc.setFont(new Font("Roboto", 16));

        for (int row = 1; row < 16; row++){
            String hora = lecciones.get(row-1);
            endY = gridLines.get(row)/60 - 410;
            int height = endY - startY;
            int center = startY + height / 2;

            //System.out.println("Row " + row + " base: " + startY + " end: " + endY);
            gc.setFill(row%2==0? Color.WHITE : Color.web("#F2F2F2") );
            gc.fillRect(0, startY, canvas_horarioAula.getWidth(), height);

            gc.setFill(Color.BLACK);
            gc.fillText(hora, 15, center + 8,100); //Pos del texto

            gc.setFill( Color.web("#BBBBBB"));
            gc.strokeLine(0, startY , canvas_horarioAula.getWidth(), startY );

            startY = endY;
        }

        canvas_horarioAula.setHeight(endY);
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


    public void initialize(){
        this.gc = canvas_horarioAula.getGraphicsContext2D();

        aulas = new HashSet<>();// = DataHolder.getInstance().getAulas().keySet();
        aulas = DataHolder.getInstance().getAulas().keySet();


        combo_Aulas.getItems().addAll(aulas);
        combo_Aulas.getSelectionModel().selectFirst();
        combo_Aulas.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> drawOnCanvas(t1));

        //As JSON would be better
        diasMap = new HashMap<>();
        diasMap.put("LUNES",115);
        diasMap.put("MARTES",315);
        diasMap.put("MIERCOLES",515);
        diasMap.put("JUEVES",715);
        diasMap.put("VIERNES",915);
        diasMap.put("SABADO",1215);

        lecciones = new HashMap<>();
        lecciones.put(0, "07:30-08:20");
        lecciones.put(1, "08:30-09:20");
        lecciones.put(2, "09:30-10:20");
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
        lecciones.put(15, "22:00-22:50");

        for (int i = 0; i < 16; i++){
            gridLines.add( LocalTime.parse(lecciones.get(i).split("-")[0]).toSecondOfDay() );
        }

        clearCanvas();
        drawOnCanvas(combo_Aulas.getSelectionModel().getSelectedItem().toString());
    }


    private void abrirEdicionGrupo(Grupo group){
        System.out.println(group.toString());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editgroup.fxml"));
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
