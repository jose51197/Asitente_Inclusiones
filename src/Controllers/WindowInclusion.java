package Controllers;

import Model.DataHolder;
import Model.Inclusion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import javax.swing.plaf.TabbedPaneUI;
import java.util.ArrayList;

public class WindowInclusion {
    public Label lcantidad;
    public Label lcarnet;
    public Label lnombre;
    @FXML TabPane tabInclusiones;
    private int carnet;

    public void setCarnet(int carnet) {
        this.carnet = carnet;
        ArrayList<Inclusion> inclusiones = DataHolder.getInstance().getInclusionesMap().get(carnet);
        for(Inclusion inclusion: inclusiones){
            try{
                Tab tab = new Tab();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/viewInclusion.fxml"));
                tab.setContent(loader.load());
                ((ViewInclusion)loader.getController()).setInclusion(inclusion);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("error anadiendo tab");
            }
        }
        lnombre.setText(inclusiones.get(0).getNombre());
        lcarnet.setText(String.valueOf(carnet));

    }

    public void initialize(){

    }

    public void izquierda(ActionEvent actionEvent) {

    }

    public void derecha(ActionEvent actionEvent) {
    }
}
