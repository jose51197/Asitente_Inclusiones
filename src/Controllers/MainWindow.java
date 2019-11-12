package Controllers;

import Model.DataHolder;
import Model.EstadoInclusion;
import Model.Inclusion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

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

    public void cargarInclusiones(){
        cNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        cMateria.setCellValueFactory(new PropertyValueFactory<>("Materia"));
        cEstado.setCellValueFactory(new PropertyValueFactory<>("EstadoString"));

        ObservableList<Inclusion> inclusiones = FXCollections.observableArrayList(DataHolder.getInstance().getInclusiones());
        System.out.println(inclusiones.size());


        cEstado.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<Inclusion, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            if(item.contains("PROCESO"))
                                this.setTextFill(Color.BLUEVIOLET);
                            if(item.contains("CANCELADA"))
                                this.setTextFill(Color.BLACK);
                            if(item.contains("ACEPTADA"))
                                this.setTextFill(Color.BLUE);
                            if(item.contains("RECHAZADA"))
                                this.setTextFill(Color.DARKRED);
                            setText(item);
                        }
                    }
                };
            }
        });

        tablaInclusiones.setItems(inclusiones);
    }
    public void initialize(){
        cargarInclusiones();
    }

    public void onSeleccionarInclusion(MouseEvent mouseEvent) {
        Inclusion seleccionada = (Inclusion)tablaInclusiones.getSelectionModel().getSelectedItem();
        //seleccionada.setEstado(EstadoInclusion.RECHAZADA);
        System.out.println(((Inclusion)tablaInclusiones.getSelectionModel().getSelectedItem()).getNombre());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/WindowInclusion.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNIFIED);
            stage.setTitle("Inclusion");
            stage.setScene(new Scene(root1));
            stage.show();
            WindowInclusion controlador = fxmlLoader.getController();
            controlador.setCarnet(seleccionada.getEstudiante().getCarnet());
        }  catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
