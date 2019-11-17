package Controllers;

import Model.Grupo;
import Model.Horario;
import Model.Inclusion;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class viewHorarioController {
    @FXML Canvas canvas;

    public void setDias(Inclusion i){
        Map<String, Grupo> materias = i.getEstudiante().getGrupos();
        GraphicsContext gr = canvas.getGraphicsContext2D();
        //Empezar 7:30am
        //Terminar 9:50pm 14:20 = 860 minutos = 0.7 ratio
        //600 pixeles
        Map<String,Integer> mapDias = new HashMap<>();
        mapDias.put("LUNES",50);
        mapDias.put("MARTES",250);
        mapDias.put("MIERCOLES",450);
        mapDias.put("JUEVES",650);
        mapDias.put("VIERNES",850);
        int y,x,height;

        for (Map.Entry<String,Grupo> materia : materias.entrySet()) {
            //TODO terminar de implementar horario, ya que hay un null
            System.out.println(i.getEstudiante().getCarnet());
            System.out.println(materia.getValue());
            for(Horario h:materia.getValue().getHorario()){
                y=(h.getHoraInicio().toSecondOfDay()/60) -420;
                height=(h.getHoraSalida().toSecondOfDay()/60)-420 -y;
                x=mapDias.get(h.getDia());

                gr.setFill(Color.STEELBLUE);
                gr.fillRect(x,y,100,height);
                gr.setFill(Color.WHITE);
                gr.fillText(materia.getValue().getProfesor().replace("\t"," "),x,y,100);
            }
        }
    }
}
