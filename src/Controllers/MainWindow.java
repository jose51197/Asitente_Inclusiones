package Controllers;

import Data.ReportePDF;
import Model.DataHolder;
import Model.Email;
import Model.EstadoInclusion;
import Model.Inclusion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.apache.pdfbox.exceptions.COSVisitorException;

import java.io.IOException;
import java.util.ArrayList;

public class MainWindow {
    @FXML TextField textSearch;
    @FXML TableView tablaInclusiones;
    @FXML TableColumn cNombre,cCarne,cMateria,cEstado,cPonderado;
    private ObservableList<Inclusion> inclusiones;

    public void abrirConfiguracion()  {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/configuration.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNIFIED);
            stage.setTitle("Configuración");
            stage.setScene(new Scene(root1));
            ((Configuration) fxmlLoader.getController()).setControllerMain(this);
            stage.show();
        }  catch (IOException e){
            System.out.println(e.toString());
        }
    }



    public void alertMe(String mensaje){
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.ERROR);
        a.setContentText(mensaje);
        // show the dialog
        a.show();
    }

    public void ayuda(){
        alertMe("Ayuda");
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
            stage.setMinHeight(800);
            stage.setMinWidth(1000);
            stage.show();
        }  catch (IOException e){
            System.out.println(e.toString());
        }

    }


    public void initialize(){
        cNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        cMateria.setCellValueFactory(new PropertyValueFactory<>("Materia"));
        cEstado.setCellValueFactory(new PropertyValueFactory<>("EstadoString"));
        cPonderado.setCellValueFactory(new PropertyValueFactory<>("Ponderado"));
        cCarne.setCellValueFactory(new PropertyValueFactory<>("Carne"));
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
        inclusiones = FXCollections.observableArrayList(DataHolder.getInstance().getInclusiones());
        System.out.println(inclusiones.size());
        tablaInclusiones.setItems(inclusiones);

    }


    public void imprimirAprobados(){
        ReportePDF pdf = new ReportePDF("../ReporteDAR.pdf");
        try {
            pdf.write();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
    }

    public void onSeleccionarInclusion(MouseEvent mouseEvent) {
        Inclusion seleccionada = (Inclusion)tablaInclusiones.getSelectionModel().getSelectedItem();
        if(seleccionada==null){
            return;
        }
        tablaInclusiones.getSelectionModel().clearSelection();
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
            controlador.iniciar(seleccionada);
        }  catch (IOException e){
            System.out.println(e.toString());
        }
        tablaInclusiones.refresh();
    }

    public void enviarCorreos(ActionEvent actionEvent) {
        ArrayList<Inclusion> inclusiones = DataHolder.getInstance().getInclusiones();
        Email email =Email.getInstance();
        Thread thread = new Thread(){
            public void run(){
                for(Inclusion inclusion:inclusiones){
                    if(inclusion.getEstado()!= EstadoInclusion.EN_PROCESO){
                        email.sendEmail(inclusion.getCorreo(),"Resultado de inclusion "+
                                        inclusion.getGrupo().getCurso().getNombre() + " GR "+ inclusion.getGrupo().getNumGrupo(),
                                "El resultado de la inclusion es: "+inclusion.getEstado().toString());
                    }
                }
            }
        };
        thread.start();
    }

    public void btn_enter(ActionEvent actionEvent) {
       search(actionEvent);
    }

    public void search(ActionEvent actionEvent) {
        String[] query = textSearch.getText().split("-");
        ArrayList<Inclusion> actuales;
        if(query.length==1 || query.length%2!=0){
            tablaInclusiones.setItems(FXCollections.observableArrayList(DataHolder.getInstance().getInclusiones()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El query no es valido");

            alert.showAndWait();
            return;
        }
        ArrayList<Inclusion> seleccionadas = DataHolder.getInstance().getInclusiones();
        for (int i =0;i<query.length;i+=2) {
            switch (query[i]){
                case "n"://por nombre
                    actuales=seleccionadas;
                    seleccionadas = new ArrayList<Inclusion>();
                    for(Inclusion inclusion: actuales){
                        if(inclusion.getEstudiante().getNombre().toLowerCase().contains(query[i+1].toLowerCase())){
                            seleccionadas.add(inclusion);
                        }
                    }
                    break;
                case "m"://por materia
                    actuales=seleccionadas;
                    seleccionadas = new ArrayList<Inclusion>();
                    for(Inclusion inclusion: actuales){
                        if(inclusion.getGrupo().getCurso().getNombre().toLowerCase().contains(query[i+1].toLowerCase())){
                            seleccionadas.add(inclusion);
                        }
                    }
                    break;
                case "p>":
                    actuales=seleccionadas;
                    seleccionadas = new ArrayList<Inclusion>();
                    for(Inclusion inclusion: actuales){
                        try{
                            if(inclusion.getEstudiante().getPonderado() > Double.valueOf(query[i+1])){
                                seleccionadas.add(inclusion);
                            }
                        }catch (Exception e){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Ingrese un numero valido en ponderado");

                            alert.showAndWait();
                        }

                    }
                    break;
                case "p<":
                    actuales=seleccionadas;
                    seleccionadas = new ArrayList<Inclusion>();
                    for(Inclusion inclusion: actuales){
                        try{
                            if(inclusion.getEstudiante().getPonderado() < Double.valueOf(query[i+1])){
                                seleccionadas.add(inclusion);
                            }
                        }catch (Exception e){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Ingrese un numero valido en ponderado");

                            alert.showAndWait();
                        }

                    }
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Query invalido");

                    alert.showAndWait();
                    return;

            }
        }
        tablaInclusiones.setItems(FXCollections.observableArrayList(seleccionadas));
        tablaInclusiones.refresh();

    }
}
