package Controllers;

import Model.Aula;
import Model.DataHolder;
import Model.Grupo;
import Model.Horario;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassroomManager {

    @FXML
    private GridPane gridHorario;

    private void fillGrid(){
        Aula aula = DataHolder.getInstance().getAulas().get("B3-7");

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

    }
}
