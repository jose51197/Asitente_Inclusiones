package sample;

import Controllers.CargadorArchivos;
import Controllers.EditGroupController;
import Controllers.MainWindow;
import Data.ReportePDF;
import Model.*;
import Model.DataLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.pdfbox.exceptions.COSVisitorException;

import java.io.*;
import java.util.ArrayList;


public class Main extends Application {
    private static DataHolder dataHolder = DataHolder.getInstance();
    private static CargadorArchivos cargadorArchivos= new CargadorArchivos();
    Email email = Email.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/mainwindow.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Asistente Inclusiones ATI");
        primaryStage.setScene(new Scene(root, 1000, 800));

        primaryStage.show();

        MainWindow controlador = fxmlLoader.getController();
        controlador.mostrarErroresDeCarga();
    }

    public static void pruebasJose(){

    }

    public static void pruebasOscar() throws IOException {
        ReportePDF pdf = new ReportePDF("../BlankPage.pdf");
        try {
            pdf.write();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        }
    }

    public static void pruebasSergie() throws IOException {
    }

    public static void main(String[] args) throws Exception {
        DataLoader dataLoader = new DataLoader();
        try{
            dataLoader.getPlanes("plan.xlsx");
            dataLoader.addAulas("aulas.xlsx");
            dataLoader.getEstudiantes("infoEstudiantes.xlsx","rn.csv");
        }
        catch (Exception e){

        }
        try{
            dataLoader.getInclusionesExistentes();
        }
        catch (Exception e){

        }
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        pruebasJose();
        pruebasSergie();
        pruebasOscar();

        launch(args);
    }


}
