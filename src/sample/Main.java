package sample;

import Controllers.CargadorArchivos;
import Controllers.MainWindow;
import Data.ReporteDAR;
import Model.*;
import Model.DataLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.pdfbox.exceptions.COSVisitorException;

import java.io.*;


public class Main extends Application {
    private static DataHolder dataHolder = DataHolder.getInstance();
    private static CargadorArchivos cargadorArchivos= new CargadorArchivos();
    Email email = Email.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainwindow.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Asistente Inclusiones ATI");
        primaryStage.setScene(new Scene(root, 1000, 800));

        primaryStage.show();

        MainWindow controlador = fxmlLoader.getController();
        controlador.mostrarErroresDeCarga();
    }

    public static void pruebasJose(){

    }

    public static void pruebasOscar() {
       // ReporteDAR reporte = new ReporteDAR( "we", "asd");
        //reporte.write();

    }

    public static void pruebasSergie() throws IOException {
    }

    public static void main(String[] args) throws Exception {
        DataLoader dataLoader = new DataLoader();
        try{
            dataLoader.addPlanes("plan.xlsx");
            dataLoader.addAulas("aulas.xlsx");
            dataLoader.addEstudiantesComplete("infoEstudiantes.xlsx");
            //dataHolder.saveGroups();
            dataLoader.addEstudiantes("infoEstudiantes.xlsx");
            dataLoader.addRN("rn.csv");
            System.out.println("Todos los documentos se han cargado");
        }
        catch (Exception e){
            System.out.println("Error al cargar los documentos");
            System.out.println(e.toString());
        }

        try{
            dataLoader.addInclusionesExistentes();
        }
        catch (Exception e){

        }

        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        launch(args);
        pruebasJose();
        pruebasSergie();
        pruebasOscar();
    }


}
