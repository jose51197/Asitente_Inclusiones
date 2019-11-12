package Controllers;

import Model.Aula;
import Model.DataHolder;
import Model.Grupo;
import Model.Horario;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassroomManager {

    @FXML
    private GridPane gridHorario;
    @FXML
    private ComboBox combo_Aulas;

    private void fillGrid(){
        Aula aula = DataHolder.getInstance().getAulas().get( (String) combo_Aulas.getValue());

        for (String key : DataHolder.getInstance().getGrupos().keySet()){
            Grupo grupo = DataHolder.getInstance().getGrupos().get(key);

            for (Horario horario : grupo.getHorario()){
                if (horario.getAula().getCodigo().equals(aula.getCodigo())){

                }
            }


        }
    }

    public void draw(){

    }

    public void initialize(){
        Set<String> aulas = DataHolder.getInstance().getAulas().keySet();
        combo_Aulas.getItems().addAll(aulas);
        combo_Aulas.getSelectionModel().selectFirst();

        fillGrid();
    }
}
