package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Configuration {
    @FXML
    Button btn_cargarEstudiantes;


    public void cargarEstudiantes(){

    }

    public void cargarOfertaSemestre(){

    }

    public void cargarPlan(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);


    }

    public void cargarInclusiones(){

    }
}
