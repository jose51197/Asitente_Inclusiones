package Controllers;

import Model.DataHolder;
import Model.Inclusion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainWindow {
    @FXML Button btn_abrirConfig;
    @FXML TableView tablaInclusiones;
    @FXML TableColumn cNombre;
    @FXML TableColumn cMateria;
    @FXML TableColumn cEstado;

    public void abrirConfiguracion()  {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/configuration.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNIFIED);
            stage.setTitle("Configuración");
            stage.setScene(new Scene(root1));
            stage.show();
        }  catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public void abrirSolicitud()  {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/inclusion.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNIFIED);
            System.out.println("Here");
            stage.setTitle("Solicitud de inclusión");
            stage.setScene(new Scene(root1));
            stage.show();
        }  catch (IOException e){
            System.out.println(e.toString());
        }

    }

    public void abrirAdminAulas()  {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/classroommanager.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNIFIED);
            stage.setTitle("Administrador  de aula");
            stage.setScene(new Scene(root1));
            stage.setMaxHeight(600);
            stage.setMaxWidth(800);
            stage.show();
        }  catch (IOException e){
            System.out.println(e.toString());
        }

    }

    public void cargarInclusiones(){
        cNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        cMateria.setCellValueFactory(new PropertyValueFactory<>("Materia"));
        cEstado.setCellValueFactory(new PropertyValueFactory<>("EstadoString"));

        ObservableList<Inclusion> inclusiones = FXCollections.observableArrayList(DataHolder.getInstance().getInclusiones());
        System.out.println(inclusiones.size());
        tablaInclusiones.setItems(inclusiones);
    }

    public void initialize(){
        cargarInclusiones();
    }
}
