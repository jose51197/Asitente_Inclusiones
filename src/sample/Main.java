package sample;

import Controllers.CargadorArchivos;
import Model.*;
import Model.DataLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {
    private static DataHolder dataHolder= DataHolder.getInstance();
    private static CargadorArchivos cargadorArchivos= new CargadorArchivos();
    Email email = Email.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/mainwindow.fxml"));
        primaryStage.setTitle("Asistente Inclusiones ATI");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

    public static void pruebasJose(){


    }

    public static void pruebasOscar() throws IOException {
       }

    public static void pruebasSergie() throws IOException {

    }

    public static void main(String[] args) throws Exception {

        //descomentar lo de abajo si no hay .ser
        //cargadorArchivos.cargarNuevosDatos("plan.csv","rn.csv","aulas.csv","grupos.csv");
        //cargadorArchivos.cargarInclusiones("inclusiones.csv");
        DataLoader dataLoader = new DataLoader();
        dataLoader.getPlanes("plan.xlsx");
        //cambiar a las direcciones de cada uno
        dataLoader.getEstudiantes("..\\PROYINCLUSIONES\\ATI_Oscar.xlsx","..\\PROYINCLUSIONES\\rn.csv");
        dataLoader.getInclusiones("inclusiones.csv");
        //cargadorArchivos.cargarDatos();
        //dataHolder.guardar();
        pruebasJose();
        pruebasSergie();
        //pruebasOscar();

        //ResultPDF resultPDF = new ResultPDF();
        //resultPDF.write();
        launch(args);
    }


}
