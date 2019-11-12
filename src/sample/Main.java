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
        Map<String, Grupo> grupos= r.getGrupos("C:\\Users\\sergi\\Desktop\\grupos.csv",malla,aulas);
        Estudiante sergie = new Estudiante(2016138296, "Sergie Salas Rojas", "Sergie98@gmail.com", 87764520) ;
        Estudiante jose = new Estudiante(2016157695, "Jose Gonzalez Alvarado", "jose5119798@hotmail.com", 71085654) ;
        estudiantes.put(2016138296,sergie);
        estudiantes.put(2016157695,jose);
        Map <Integer,ArrayList<Inclusion>> inclusionesMap= new HashMap<Integer, ArrayList<Inclusion>>();
        ArrayList<Inclusion> inclusiones = r.getInclusiones("C:\\Users\\sergi\\Desktop\\inclusiones.csv",grupos,estudiantes,inclusionesMap);
        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\sergi\\Desktop\\plan.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(malla);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("C:\\Users\\sergi\\Desktop\\estudiantes.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(estudiantes);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("C:\\Users\\sergi\\Desktop\\aulas.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(aulas);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("C:\\Users\\sergi\\Desktop\\grupos.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(grupos);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("C:\\Users\\sergi\\Desktop\\inclusionesMap.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(inclusionesMap);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("C:\\Users\\sergi\\Desktop\\inclusiones.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(inclusiones);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }*/

        //ResultPDF resultPDF = new ResultPDF();
        //resultPDF.write();
        launch(args);
    }
}
