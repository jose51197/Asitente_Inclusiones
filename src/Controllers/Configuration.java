package Controllers;

import Model.DataHolder;
import Model.DataLoader;
import Model.Inclusion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    private DataHolder dataHolder = DataHolder.getInstance();
    public MainWindow controllerMain;

    public void setControllerMain(MainWindow main){
        controllerMain = main;
    }

    public void cargarRN(){
        try{
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            rn = fileChooser.showOpenDialog(stage);
            nameRN.setText("    Archivo cargado: "+rn.getName());
        }
        catch (Exception e){
            rn = null;
        }

    }

    public void cargarPlan(){
        try{
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().add(extFilter);
            plan = fileChooser.showOpenDialog(stage);
            namePlan.setText("    Archivo cargado: "+plan.getName());
        }
        catch (Exception e){
            plan = null;
        }
    }

    public void cargarInclusiones(){
        try{
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            inclusiones = fileChooser.showOpenDialog(stage);
            nameInclusiones.setText("    Archivo cargado: "+inclusiones.getName());
        }
        catch (Exception e){
            inclusiones = null;
        }
    }

    public void mostrarErroresDeCarga(){
        ArrayList<String> errores = DataHolder.getInstance().getErrores();
        String listadoErrores = "";
        for(String error: errores){
            listadoErrores += error + '\n';
            System.out.println(error);
        }

        if (listadoErrores.equals("")) return;

        TextArea area = new TextArea(listadoErrores);
        area.setWrapText(true);
        area.setEditable(false);

        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.ERROR);
        a.getDialogPane().setContent(area);
        a.setTitle("Errores de carga de datos");
        //a.setContentText("Se presentaron los siguientes errores al cargar los datos.");
        // show the dialog
        a.show();
        DataHolder.getInstance().clearErrores();
    }

    public void cargarAulas(ActionEvent actionEvent) {
        try{
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().add(extFilter);
            aulas = fileChooser.showOpenDialog(stage);
            nameAulas.setText("    Archivo cargado: "+aulas.getName());
        }
        catch (Exception e){
            aulas = null;
        }
    }

    public void cargarInfoEstudiantes(ActionEvent actionEvent) {Stage stage = new Stage();
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().add(extFilter);
            infoEstudiantes = fileChooser.showOpenDialog(stage);
            nameInfoEstudiantes.setText("    Archivo cargado: "+infoEstudiantes.getName());
        }
        catch (Exception e){

        }
    }

    public void cargarDatosBase(ActionEvent actionEvent) {

        Alert notification = new Alert(Alert.AlertType.CONFIRMATION);

        mostrarErroresDeCarga();

        if (rn==null || plan==null || infoEstudiantes==null || aulas==null){
            notification.setAlertType(Alert.AlertType.ERROR);
            notification.setHeaderText(null);
            notification.setContentText("Ingrese todos los documentos antes de solicitar la carga.");
            return;
        }


        File rnDestino= new File("rn.csv");
        File planDestino= new File("plan.xlsx");
        File infoEstudiantesDestino= new File("infoEstudiantes.xlsx");
        File aulasDestino = new File("aulas.xlsx");
        File gruposDestino = new File("grupos.csv");

        try {
            FileUtils.copyFile(rn, rnDestino);
            FileUtils.copyFile(plan, planDestino);
            FileUtils.copyFile(infoEstudiantes, infoEstudiantesDestino);
            FileUtils.copyFile(aulas, aulasDestino);
            DataHolder.getInstance().resetDataHolder();

            try{
                dataLoader.addPlanes("plan.xlsx");
            }
            catch(Exception e){
                controllerMain.alertMe("Error en archivo "+plan.getName());
                return;
            }

            try{
                dataLoader.addAulas("aulas.xlsx");
            }
            catch(Exception e){
                controllerMain.alertMe("Error en archivo "+aulas.getName());
                return;
            }
            try{
                dataLoader.addEstudiantesComplete("infoEstudiantes.xlsx");
                dataHolder.saveGroups(); //Creates the file that stores the groups

            }
            catch(Exception e){
                controllerMain.alertMe("Error en archivo "+infoEstudiantes.getName());
                return;
            }
            try{
                dataLoader.addRN("rn.csv");
            }
            catch(Exception e){
                controllerMain.alertMe("Error en archivo "+rn.getName());
                e.printStackTrace();
                return;
            }

            ObservableList<Inclusion> inclusionesView = FXCollections.observableArrayList(DataHolder.getInstance().getInclusiones());
            controllerMain.tablaInclusiones.setItems(inclusionesView);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Se han cargado los datos seleccionados.");
            alert.showAndWait();
            mostrarErroresDeCarga();
        } catch (IOException e) {
            controllerMain.alertMe("Error alguno de los archivos seleccionados, esta siendo usado por otra aplicación");
        }
        catch (Exception e){
            controllerMain.alertMe("Error en los archivos");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }



    }

    public void cargarDatosInclusiones(ActionEvent actionEvent) {
        if(inclusiones!=null){
            File inclusionesDestino= new File("inclusiones.csv");
            try {
                FileUtils.copyFile(inclusiones, inclusionesDestino);
                DataHolder.getInstance().resetInclusiones();
                dataLoader.addInclusionesNuevas("inclusiones.csv");
                System.out.printf(String.valueOf(DataHolder.getInstance().getInclusiones().size()));
                ObservableList<Inclusion> inclusionesView = FXCollections.observableArrayList(DataHolder.getInstance().getInclusiones());
                controllerMain.tablaInclusiones.setItems(inclusionesView);
                DataHolder.getInstance().saveStatus();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Se han cargado las inclusiones.");
                alert.showAndWait();
                mostrarErroresDeCarga();
            } catch (IOException e) {
                controllerMain.alertMe("Error alguno de los archivos seleccionados, esta siendo usado por otra aplicación");
            }
            catch (Exception e){
                controllerMain.alertMe("Error en los archivos");
            }

        }
        else{
            controllerMain.alertMe("Debe cargar archivo antes de continuar");
        }
    }
}
