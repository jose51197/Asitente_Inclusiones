package sample;

import Data.ResultPDF;
import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/mainwindow.fxml"));
        primaryStage.setTitle("Asistente Inclusiones ATI");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        CSVreader r= new CSVreader();

        //pruebas de funcionamiento de los diccionarios;
        Map<String, Curso> malla=r.getMalla_Curricular("C:\\Users\\sergi\\Desktop\\plan.csv");
        Map<Integer, Estudiante> estudiantes=r.getEstudiantes("C:\\Users\\sergi\\Desktop\\rn.csv",malla);
        Map<String, Aula> aulas = r.getAulas("C:\\Users\\sergi\\Desktop\\aulas.csv");
        Map<String, Grupo> grupos= r.getGrupos("C:\\Users\\sergi\\Desktop\\grupos.csv",malla,aulas);
        Estudiante sergie = new Estudiante(2016138296, "Sergie Salas Rojas", "Sergie98@gmail.com", 87764520) ;
        Estudiante jose = new Estudiante(2016157695, "Jose Gonzalez Alvarado", "jose5119798@hotmail.com", 71085654) ;
        estudiantes.put(2016138296,sergie);
        estudiantes.put(2016157695,jose);
        Map <Integer,ArrayList<Inclusion>> inclusionesMap= new HashMap<Integer, ArrayList<Inclusion>>();
        ArrayList<Inclusion> inclusions = r.getInclusiones("C:\\Users\\sergi\\Desktop\\inclusiones.csv",grupos,estudiantes,inclusionesMap);
        System.out.println(inclusions.get(1).getEstudiante().getEmail());
        //ResultPDF resultPDF = new ResultPDF();
        //resultPDF.write();
        launch(args);
    }
}
