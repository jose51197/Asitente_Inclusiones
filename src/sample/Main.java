package sample;

import Controllers.CargadorArchivos;
import Model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        CSVreader r= new CSVreader();
        dataHolder.setMalla(r.getMalla_Curricular("plan.csv"));
        dataHolder.setEstudiantes(r.getEstudiantes("rn.csv",dataHolder.getMalla()));
        dataHolder.setAulas(r.getAulas("aulas.csv"));
        //dataHolder.setGrupos(r.getGrupos("grupos.csv",dataHolder.getMalla(),dataHolder.getAulas()));
        dataHolder.setGrupos(r.getGrupos_Aulas("grupos-horario-fixed.csv",dataHolder.getMalla(),dataHolder.getAulas()));

        for (Grupo grupo : dataHolder.getGrupos().values()){
            System.out.println(grupo.toString());
        }

        Estudiante sergie = new Estudiante(2016138296, "Sergie Salas Rojas", "Sergie98@gmail.com", 87764520) ;
        Estudiante jose = new Estudiante(2016157695, "Jose Gonzalez Alvarado", "jose51197@hotmail.com", 71085654) ;
        dataHolder.getEstudiantes().put(2016138296,sergie);
        dataHolder.getEstudiantes().put(2016157695,jose);
        dataHolder.setInclusiones(r.getInclusiones("inclusiones.csv",dataHolder.getGrupos(),dataHolder.getEstudiantes(),dataHolder.getInclusionesMap()));
    }

    public static void pruebasSergie() throws IOException {

    }

    public static void main(String[] args) throws Exception {

        //descomentar lo de abajo si no hay .ser
        cargadorArchivos.cargarNuevosDatos("plan.csv","rn.csv","aulas.csv","grupos-horario-fixed.csv");
        cargadorArchivos.cargarInclusiones("inclusiones.csv");
        //cargadorArchivos.cargarDatos();
        pruebasJose();
        pruebasSergie();
        //pruebasOscar();

        //ResultPDF resultPDF = new ResultPDF();
        //resultPDF.write();
        launch(args);
    }


}
