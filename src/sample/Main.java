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
        ArrayList<String> errores = dataHolder.getErrores();
        for(String error: errores){
            System.out.println(error);
        }
        System.out.println(dataHolder.getAulas().get("F9-03").getCapacidad());
        System.out.printf(String.valueOf(dataHolder.getGrupos().get("GR1TI3600").getCantEstudiantes()));
    }

    public static void main(String[] args) throws Exception {
        DataLoader dataLoader = new DataLoader();
        dataLoader.getPlanes("plan.xlsx");
        dataLoader.addAulas("aulas.xlsx");
        dataLoader.getEstudiantes("..\\PROYINCLUSIONES\\ATI.xlsx","..\\PROYINCLUSIONES\\rn.csv");
        dataLoader.getInclusiones("inclusiones.csv");
        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        pruebasJose();
        pruebasSergie();
        pruebasOscar();

        launch(args);
    }


}
