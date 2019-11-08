package sample;

import Model.CSVreader;
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
        //Map<Integer, Estudiante> algo=r.getEstudiantes("C:\\Users\\Sergie\\Downloads\\resul.csv");
        launch(args);
    }
}
