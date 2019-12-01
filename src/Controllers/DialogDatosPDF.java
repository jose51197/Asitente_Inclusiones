package Controllers;

import Data.ReporteDAR;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.pdfbox.exceptions.COSVisitorException;


import java.io.IOException;

public class DialogDatosPDF {
    @FXML
    private TextField field_anio;
    @FXML
    private TextField textBox_periodo;

    public void guardarResultados(){
        String anio = field_anio.getText();
        String periodo = textBox_periodo.getText();
        ReporteDAR pdf = new ReporteDAR("../ReporteDAR.pdf", periodo, anio);
        try {
            pdf.write();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
