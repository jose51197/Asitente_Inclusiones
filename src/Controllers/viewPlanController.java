package Controllers;

import Model.Curso;
import Model.DataHolder;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


import java.util.HashMap;
import java.util.Map;

public class viewPlanController {
    @FXML
    Canvas canvas;
    public void initialize(){
        setPlan(null);
    }
    public void setPlan(Map<String, Curso> plan){
        plan = DataHolder.getInstance().getMalla();//temporal
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);


        int y;
        final int h=80;
        int semestre;
        gc.setStroke(Color.WHITE);
        gc.setFont(new Font("Calibri",12));
        Map<Integer,Integer> mapSemestre = new HashMap<>();
        for (Map.Entry<String, Curso> curso : plan.entrySet()) {
            System.out.println(curso.getValue().getNombre());
            if(mapSemestre.containsKey(curso.getValue().getSemestre())){
                mapSemestre.put(curso.getValue().getSemestre(),mapSemestre.get(curso.getValue().getSemestre())+1);
                semestre=curso.getValue().getSemestre();
                y=mapSemestre.get(curso.getValue().getSemestre());
            }else{
                semestre=curso.getValue().getSemestre();
                mapSemestre.put(semestre,0);
                y=0;
            }

            int x = 100*semestre+ 20;
            //TODO tomar en cuenta si ya lo paso, por ahora azul por default
            gc.setFill(Color.BLUE);
            gc.fillRoundRect(x,y*h ,80,60,15,15);
            gc.setFill(Color.GRAY);
            gc.fillRoundRect(x,y*h ,80,5,15,15);
            gc.fillRect(x,y*h +2,80,8);
            gc.setFill(Color.BLACK);
            gc.fillText(curso.getKey(),x+25,y*h +10,80);
            gc.setFill(Color.WHITE);
            if(curso.getValue().getNombre().length()>12){
                String[] nombreCurso=splitByNumber(curso.getValue().getNombre(),12);
                int i=0;
                for(String pedazoNombreCurso : nombreCurso){
                    if(i>=3){break;}
                    gc.fillText(pedazoNombreCurso,x,y*h+20 + 10*i,80);
                    i+=1;
                }
            }else{
                gc.fillText(curso.getValue().getNombre(),x,y*h + 30,80);
            }
            gc.fillText("Creditos: "+curso.getValue().getCreditos(),x,y*h +50,80);
        }
        int mayor=0;
        for (Map.Entry<Integer,Integer> numSemestre : mapSemestre.entrySet()) {
            if(numSemestre.getValue()>mayor){
                mayor=numSemestre.getValue();
            }
        }
        gc.setFill(Color.BLACK);
        for (Map.Entry<Integer,Integer> numSemestre : mapSemestre.entrySet()) {
            gc.fillText("Semestre "+ numSemestre.getKey(),numSemestre.getKey()*100 + 32,(mayor+1)*h,80);
        }




    }
    public static String[] splitByNumber(String str, int size) {
        return (size<1 || str==null) ? null : str.split("(?<=\\G.{"+size+"})");
    }
}
