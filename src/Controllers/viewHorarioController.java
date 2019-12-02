package Controllers;

import Model.DataHolder;
import Model.Grupo;
import Model.Horario;
import Model.Inclusion;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.xml.crypto.Data;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewHorarioController {
    @FXML Canvas canvas;

    public void setDias(Inclusion i){
        Map<String, Grupo> materias = i.getEstudiante().getGrupos();
        //ArrayList<Inclusion> otrasInclusiones = DataHolder.getInstance().getInclusionesMapPorEstudiante().get(i.getEstudiante().getCarnet()); poner inclusion encima de horario? pero ocupo choque de horario

        GraphicsContext gr = canvas.getGraphicsContext2D();
        //Empezar 7:30am
        //Terminar 9:50pm 14:20 = 860 minutos =~ 0.7 ratio
        //600 pixeles
        Map<String,Integer> mapDias = new HashMap<>();
        mapDias.put("LUNES",50);
        mapDias.put("MARTES",250);
        mapDias.put("MIERCOLES",450);
        mapDias.put("JUEVES",650);
        mapDias.put("VIERNES",850);
        mapDias.put("SABADO",1050);
        int y,x,height;
        final int maxWidth = 150;
        final double ratio = 0.6;
        final int offset =420;
        LocalTime hora = LocalTime.of(7,30);
        //Lineas de horas, etc
        for(int j=0;j<30;j++){
            gr.fillText(hora.toString(),5,ratio*(hora.toSecondOfDay()/60 -offset));
            gr.fillRect(0,ratio*(hora.toSecondOfDay()/60 -offset),1500,1);
            hora = hora.plusMinutes(30);
        }
        //dias
        gr.setFill(Color.BLACK);
        gr.fillText("LUNES",100,10);
        gr.fillText("MARTES",300,10);
        gr.fillText("MIERCOLES",500,10);
        gr.fillText("JUEVES",700,10);
        gr.fillText("VIERNES",900,10);
        gr.fillText("SABADO",1100,10);

        for (Map.Entry<String,Grupo> materia : materias.entrySet()) {
            System.out.println(i.getEstudiante().getCarnet());
            System.out.println(materia.getValue());
            for(Horario h:materia.getValue().getHorario()){
                y=(int)(ratio*(h.getHoraInicio().toSecondOfDay()/60 -offset));
                System.out.println(y);
                height=(int)(ratio*(h.getHoraSalida().toSecondOfDay()/60 -offset) -y);
                System.out.println(h.getDia());
                x=mapDias.get(h.getDia());

                gr.setFill(Color.STEELBLUE);
                gr.fillRect(x,y,maxWidth,height);
                gr.setFill(Color.WHITE);

                String[] strings = GenericFunctions.splitByNumber(materia.getValue().getCurso().getNombre(), 20);
                gr.fillText(strings[0],x,y+10,maxWidth);
                gr.fillText(materia.getValue().getProfesor().replace("\t"," " ),x,y+0.4*height,maxWidth);
                gr.fillText(h.getAula().getCodigo(),x,y+0.6*height,maxWidth);
                gr.fillText(h.getHoraInicio().toString() + " " + h.getHoraSalida(),x,y+0.8*height,maxWidth);
            }
        }
    }
}
