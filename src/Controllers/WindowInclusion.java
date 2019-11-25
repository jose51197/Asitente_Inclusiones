package Controllers;

import Model.DataHolder;
import Model.Inclusion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class WindowInclusion {
    public Label lrn;
    public ScrollPane scroll;
    public AnchorPane ap;
    @FXML Label labelPonderado;
    @FXML Tab tab1;
    @FXML Label lcantidad;
    @FXML Label lcarnet;
    @FXML Label lnombre;
    @FXML TabPane tabInclusiones;
    @FXML TabPane tabPlanHorario;
    @FXML Pane plan;

    private int carnet;
    private int selected=0;
    private int total=1;

    public void iniciar(Inclusion i) {

        this.carnet = i.getEstudiante().getCarnet();
        labelPonderado.setStyle("-fx-font-weight: bold;");
        labelPonderado.setText("Ponderado: " + String.valueOf(i.getEstudiante().getPonderado()));

        ArrayList<Inclusion> inclusiones = DataHolder.getInstance().getInclusionesMapPorEstudiante().get(carnet);

        //PLAN SELECCIONADO
        try{
            Tab tab = new Tab();
            tab.setText(i.getEstudiante().getPlan());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewPlan.fxml"));
            tab.setContent(loader.load());
            ((viewPlanController)loader.getController()).setPlan(i.getEstudiante());
            tabPlanHorario.getTabs().add(tab);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error anadiendo horario/plan");
        }
        //HORARIO SELECCIONADO
        try{
            Tab tab = new Tab();
            tab.setText("Horario");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewHorario.fxml"));
            tab.setContent(loader.load());
            ((viewHorarioController)loader.getController()).setDias(i);
            tabPlanHorario.getTabs().add(tab);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error anadiendo horario/plan");
        }
        //INCLUSION SELECCIONADA
        try{
            Tab tab = new Tab();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewInclusion.fxml"));
            tab.setContent(loader.load());
            ((ViewInclusion)loader.getController()).setInclusion(i);
            tabInclusiones.getTabs().add(tab);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error anadiendo inclusion a tab");
        }

        for(Inclusion inclusion: inclusiones){
            try{
                if (inclusion==i){//es la ya mostrada
                    continue;
                }
                this.total+=1;
                Tab tab = new Tab();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("viewInclusion.fxml"));
                tab.setContent(loader.load());
                ((ViewInclusion)loader.getController()).setInclusion(inclusion);
                tabInclusiones.getTabs().add(tab);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("error anadiendo tab");
            }
        }

        lnombre.setText(inclusiones.get(0).getNombre());
        lcarnet.setText(String.valueOf(carnet));
        if(DataHolder.getInstance().getEstudiantes().get(carnet).isRn()){
            lrn.setText("RN: SI");
            lrn.setTextFill(Color.RED);
        }else{
            lrn.setText("RN: NO");
            lrn.setTextFill(Color.BLUE);
        }
        lcantidad.setText(String.valueOf(selected+1)+"/"+ String.valueOf(total));

        Stage stage = (Stage) ap.getScene().getWindow();
        scroll.setPrefHeight(stage.getHeight()-276);
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            scroll.setPrefHeight(newVal.intValue()-276);
        });
    }

    public void initialize(){

    }

    public void izquierda(ActionEvent actionEvent) {
        if(selected>0){
            selected-=1;
            tabInclusiones.getSelectionModel().select(selected);
        }
        lcantidad.setText(String.valueOf(selected+1)+"/"+ String.valueOf(total));
    }

    public void derecha(ActionEvent actionEvent) {
        if(selected<total-1){
            selected+=1;
            tabInclusiones.getSelectionModel().select(selected);
        }
        lcantidad.setText(String.valueOf(selected+1)+"/"+ String.valueOf(total));
    }
}
