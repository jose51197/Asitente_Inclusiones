package Controllers;

import Data.ReporteDAR;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.pdfbox.exceptions.COSVisitorException;


import java.io.IOException;

public class DialogDatosReporte {
    @FXML
    private TextField field_anio;
    @FXML
    private TextField textBox_periodo;

    public void guardarResultados(){
        String anio = field_anio.getText();
        String periodo = textBox_periodo.getText();
        ReporteDAR pdf = new ReporteDAR("../resultadoDAR.docx", periodo, anio);
        try {
            pdf.write();
            showSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            showError();
        }

    }


    private void showSuccess(){
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("El documento se ha escrito con exito.");
        // show the dialog
        a.show();
    }

    private void showError(){
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.ERROR);
        a.setContentText("No se escribio el documento.");
        // show the dialog
        a.show();
    }
}
