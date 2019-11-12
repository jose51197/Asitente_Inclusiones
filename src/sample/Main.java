package sample;

import Data.ResultPDF;
import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;



public class Main extends Application {
    Email email = Email.getInstance();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/mainwindow.fxml"));
        primaryStage.setTitle("Asistente Inclusiones ATI");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void pruebasJose(){

    }

    public static void pruebasOscar(){

    }

    public static void pruebasSergie() throws IOException {

    }

    public static void main(String[] args) throws IOException {

        CSVreader r= new CSVreader();
        Map<String, Curso> malla=r.getMalla_Curricular("plan.csv");
        Map<Integer, Estudiante> estudiantes=r.getEstudiantes("rn.csv",malla);
        Map<String, Aula> aulas = r.getAulas("aulas.csv");
        Map<String, Grupo> grupos= r.getGrupos("grupos.csv",malla,aulas);
        Estudiante sergie = new Estudiante(2016138296, "Sergie Salas Rojas", "Sergie98@gmail.com", 87764520) ;
        Estudiante jose = new Estudiante(2016157695, "Jose Gonzalez Alvarado", "jose51197@hotmail.com", 71085654) ;
        estudiantes.put(2016138296,sergie);
        estudiantes.put(2016157695,jose);
        Map <Integer,ArrayList<Inclusion>> inclusionesMap= new HashMap<Integer, ArrayList<Inclusion>>();
        ArrayList<Inclusion> inclusiones = r.getInclusiones("inclusiones.csv",grupos,estudiantes,inclusionesMap);

        pruebasJose();
        pruebasSergie();
        pruebasOscar();

        //ResultPDF resultPDF = new ResultPDF();
        //resultPDF.write();
        launch(args);
    }


}
