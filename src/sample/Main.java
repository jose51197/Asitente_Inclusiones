package sample;

import Model.*;
import Data.ResultPDF;
import Model.Aula;
import Model.Grupo;
import Model.CSVreader;
import Model.Curso;
import Model.Estudiante;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

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
        /*Map<String, Curso> malla=r.getMalla_Curricular("C:\\Users\\sergi\\Desktop\\plan.csv");
        Map<Integer, Estudiante> estudiantes=r.getEstudiantes("C:\\Users\\sergi\\Desktop\\rn.csv",malla);
        Map<String, Aula> aulas = r.getAulas("C:\\Users\\sergi\\Desktop\\aulas.csv");
        Map<String, Grupo> grupos= r.getGrupos("C:\\Users\\sergi\\Desktop\\grupos.csv",malla,aulas);*/
        ResultPDF resultPDF = new ResultPDF();
        resultPDF.write();
        launch(args);
    }
}
