package Controllers;

import Model.Grupo;
import Model.Horario;
import com.sun.javafx.text.TextLine;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class EditGroupController {
    @FXML
    GridPane grid_horarios;

    public void initialize(){

    }

    public void iniciar(Grupo grupo){

        for (int revisados = 0; revisados < grupo.getHorario().size(); revisados++){

            ComboBox comboBoxAulas;
            ComboBox horaInicio;
            ComboBox horaFin;

            grid_horarios.addRow(3 + revisados, new Label());

        }
    }
}
