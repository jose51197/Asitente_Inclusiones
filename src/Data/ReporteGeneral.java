package Data;

import Controllers.GenericFunctions;
import Model.*;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteGeneral extends Reporte {
    private XWPFDocument document = new XWPFDocument();
    private XWPFTable table;
    private String path;
    private List<List<Inclusion>> resultados;

    private static final String[] columnsSizes = {"15%", "40%", "7%", "7%", "7%", "7%", "7%", "10%"};

    public ReporteGeneral(String path){
        super();
        this.path = path;
    }

    public void write() {
        document = new XWPFDocument();
        table = document.createTable(1, 8); //Base for the upper part
        table.setWidthType(TableWidthType.PCT);
        table.setWidth("100%");
        escribirTitulos();

        //for all the document
        for (String codCurso : DataHolder.getInstance().getInclusionesMapPorMateria().keySet()){
            System.out.println("Parsenado datos por materia");

            ArrayList<Inclusion> inclusionesCurso = DataHolder.getInstance().getInclusionesMapPorMateria().get(codCurso);
            Map<Integer, List<Inclusion>> inclusionesPorGrupo = new HashMap<>();


            for (Inclusion inclusion : inclusionesCurso){ //Separando por grupos para trabajar cada lista por seprado
                int numeroGrupo = inclusion.getGrupo().getNumGrupo();
                List<Inclusion> inclusionesDelGrupo = inclusionesPorGrupo.get(numeroGrupo);

                if ( inclusionesDelGrupo == null ){
                    inclusionesDelGrupo = new ArrayList<>();
                }

                inclusionesDelGrupo.add(inclusion); //Se anañaden todos, solo que separado
                inclusionesPorGrupo.put(numeroGrupo, inclusionesDelGrupo);
            }

            //Con los datos debidamente separados por grupo
            for (int numeroGrupo : inclusionesPorGrupo.keySet()){
                List<Inclusion> inclusionesDelGrupo = inclusionesPorGrupo.get(numeroGrupo);
                adjuntarResultados(inclusionesDelGrupo);
            }
        }

        save();
    }

    private void escribirTitulos(){
        String[] resultText = new String[8];

            XWPFTableRow resultRow =  table.getRow(0); //Fila en turno

            resultText[0] = "Número de Carnet";
            resultText[1] = "Seleccione el curso que requiere matricular por inclusión:";
            resultText[2] = "Grupo";
            resultText[3] = "Resultado";
            resultText[4] = "Levanta RN";
            resultText[5] = "Levanta RQ";
            resultText[6] = "Choque horario";
            resultText[7] = "Comentario";

            for (int index = 0; index < 8; index++){
                XWPFParagraph bodyParagraph = textoSimple(resultRow.getCell(index), resultText[index]);

                for (int c = 0; c < 6; c++){
                    resultRow.getCell(c).setWidth(columnsSizes[c]);
                }
            }

    }


    private void adjuntarResultados(List<Inclusion> inclusions) {

        String[] resultText = new String[8];
        for (Inclusion inclusion : inclusions){
            XWPFTableRow resultRow =  table.createRow(); //Fila en turno
            Estudiante estudiante = inclusion.getEstudiante();
            Grupo grupo = inclusion.getGrupo();

            resultText[0] = Integer.toString(estudiante.getCarnet());
            resultText[1] = grupo.getCurso().getNombre();
            resultText[2] = Integer.toString(grupo.getNumGrupo());
            resultText[3] = inclusion.getEstado().toString();
            resultText[4] = tieneRn(inclusion)? "X" : "";
            resultText[5] = cumpleCorequisitos(inclusion)? "" : "X";
            resultText[6] = tieneChoqueHorario(inclusion)? "X" : "";
            resultText[7] = inclusion.getComentarioAdmin();

            for (int index = 0; index < 8; index++){
                XWPFParagraph bodyParagraph = textoSimple(resultRow.getCell(index), resultText[index]);

                for (int c = 0; c < 6; c++){
                    resultRow.getCell(c).setWidth(columnsSizes[c]);
                }
            }
        }
    }

    private void textoEnriquecido(XWPFRun run, String texto, boolean bold){
        // Format as desired
        run.setFontSize(11);
        run.setFontFamily("Calibri (Body)");
        run.setText(texto);
        run.setBold(bold);
    }

    private XWPFParagraph textoSimple(XWPFTableCell cell, String texto, boolean bold){
        XWPFParagraph paragraph = cell.addParagraph();
        cell.removeParagraph(0);
        paragraph.setIndentationLeft(2);
        paragraph.setSpacingAfter(10);
        paragraph.setSpacingBeforeLines(10);
        textoEnriquecido(paragraph.createRun(), texto, bold);

        return paragraph;
    }

    private XWPFParagraph textoSimple(XWPFTableCell cell, String texto){
        return textoSimple(cell, texto, false);
    }

}
