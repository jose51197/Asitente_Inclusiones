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
        try {
            FileOutputStream fileOut = new FileOutputStream("plan.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(malla);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("estudiantes.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(estudiantes);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("aulas.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(aulas);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("grupos.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(grupos);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("inclusionesMap.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(inclusionesMap);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("inclusiones.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(inclusiones);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        /*Map<String, Curso> malla;
        Map<Integer, Estudiante> estudiantes;
        Map<String, Aula> aulas;
        Map<String, Grupo> grupos;
        Map <Integer,ArrayList<Inclusion>> inclusionesMap;
        ArrayList<Inclusion> inclusiones;
        try {
            FileInputStream fileIn = new FileInputStream("plan.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            malla = (Map<String, Curso>) in.readObject();
            in.close();
            fileIn.close();
            fileIn = new FileInputStream("aulas.ser");
            in = new ObjectInputStream(fileIn);
            aulas = (Map<String, Aula>) in.readObject();
            in.close();
            fileIn.close();
            fileIn = new FileInputStream("estudiantes.ser");
            in = new ObjectInputStream(fileIn);
            estudiantes = (Map<Integer, Estudiante> ) in.readObject();
            in.close();
            fileIn.close();
            fileIn = new FileInputStream("grupos.ser");
            in = new ObjectInputStream(fileIn);
            grupos = (Map<String, Grupo>) in.readObject();
            in.close();
            fileIn.close();
            fileIn = new FileInputStream("inclusionesMap.ser");
            in = new ObjectInputStream(fileIn);
            inclusionesMap = (Map <Integer,ArrayList<Inclusion>> ) in.readObject();
            in.close();
            fileIn.close();
            fileIn = new FileInputStream("inclusiones.ser");
            in = new ObjectInputStream(fileIn);
            inclusiones = (ArrayList<Inclusion> ) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }*/
    }

    public static void main(String[] args) throws IOException {
        pruebasJose();
        pruebasSergie();
        pruebasOscar();
        //pruebas de funcionamiento de los diccionarios;

        //ResultPDF resultPDF = new ResultPDF();
        //resultPDF.write();
        launch(args);
    }


}
