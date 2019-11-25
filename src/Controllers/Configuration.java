package Controllers;

import Model.DataHolder;
import Model.DataLoader;
import Model.Inclusion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Configuration {
    public Label nameRN;
    public Label namePlan;
    public Label nameAulas;
    public Label nameInfoEstudiantes;
    public Label nameInclusiones;
    @FXML
    Button btn_cargarEstudiantes;


    private File rn=null;
    private File plan=null;
    private File inclusiones=null;
    private File aulas=null;
    private File infoEstudiantes=null;
    private DataLoader dataLoader = new DataLoader();
    public MainWindow controllerMain;

    public void setControllerMain(MainWindow main){
        controllerMain = main;
    }

    public void cargarRN(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        rn = fileChooser.showOpenDialog(stage);
        nameRN.setText("    Archivo cargado: "+rn.getName());
    }

    public void cargarPlan(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        plan = fileChooser.showOpenDialog(stage);
        namePlan.setText("    Archivo cargado: "+plan.getName());
    }

    public void cargarInclusiones(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        inclusiones = fileChooser.showOpenDialog(stage);
        nameInclusiones.setText("    Archivo cargado: "+inclusiones.getName());
    }

    public void cargarAulas(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        aulas = fileChooser.showOpenDialog(stage);
        nameAulas.setText("    Archivo cargado: "+aulas.getName());
    }

    public void cargarInfoEstudiantes(ActionEvent actionEvent) {Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        infoEstudiantes = fileChooser.showOpenDialog(stage);
        nameInfoEstudiantes.setText("    Archivo cargado: "+infoEstudiantes.getName());
    }

    public void cargarDatosBase(ActionEvent actionEvent) {
        if(rn!=null && plan!=null&&infoEstudiantes!=null&&aulas!=null){
            File rnDestino= new File("rn.csv");
            File planDestino= new File("plan.xlsx");
            File infoEstudiantesDestino= new File("infoEstudiantes.xlsx");
            File aulasDestino = new File("aulas.xlsx");
            try {
                FileUtils.copyFile(rn, rnDestino);
                FileUtils.copyFile(plan, planDestino);
                FileUtils.copyFile(infoEstudiantes, infoEstudiantesDestino);
                FileUtils.copyFile(aulas, aulasDestino);
                dataLoader.getPlanes("plan.xlsx");
                dataLoader.addAulas("aulas.xlsx");
                dataLoader.getEstudiantes("infoEstudiantes.xlsx","rn.csv");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            controllerMain.alertMe("Debe cargar todos los archivos antes de continuar");
        }
    }

    public void cargarDatosInclusiones(ActionEvent actionEvent) {
        if(inclusiones!=null){
            File inclusionesDestino= new File("inclusiones.csv");
            try {
                FileUtils.copyFile(inclusiones, inclusionesDestino);
                dataLoader.getInclusionesNuevas("inclusiones.csv");
                System.out.printf(String.valueOf(DataHolder.getInstance().getInclusiones().size()));
                ObservableList<Inclusion> inclusionesView = FXCollections.observableArrayList(DataHolder.getInstance().getInclusiones());
                controllerMain.tablaInclusiones.setItems(inclusionesView);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            controllerMain.alertMe("Debe cargar archivo antes de continuar");
        }
    }
}
