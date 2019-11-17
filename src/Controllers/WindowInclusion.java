package Controllers;

import Model.DataHolder;
import Model.Inclusion;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javax.swing.plaf.TabbedPaneUI;
import java.util.ArrayList;

public class WindowInclusion {
    public Label lrn;
    @FXML Tab tab1;
    @FXML Label lcantidad;
    @FXML Label lcarnet;
    @FXML Label lnombre;
    @FXML TabPane tabInclusiones;
    private int carnet;
    private int selected=0;
    private int total=1;

    public void iniciar(int carnet, Inclusion i) {
        this.carnet = carnet;
        ArrayList<Inclusion> inclusiones = DataHolder.getInstance().getInclusionesMap().get(carnet);
        try{
            Tab tab = new Tab();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/viewInclusion.fxml"));
            tab.setContent(loader.load());
            ((ViewInclusion)loader.getController()).setInclusion(i);
            tabInclusiones.getTabs().add(tab);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error anadiendo tab");
        }

        for(Inclusion inclusion: inclusiones){
            try{
                if (inclusion==i){//es la ya mostrada
                    continue;
                }
                this.total+=1;
                Tab tab = new Tab();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/viewInclusion.fxml"));
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
        if(selected<total){
            selected+=1;
            tabInclusiones.getSelectionModel().select(selected);
        }
        lcantidad.setText(String.valueOf(selected+1)+"/"+ String.valueOf(total));
    }
}
