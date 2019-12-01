package Data;

import Controllers.GenericFunctions;
import Model.*;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteDAR {
    private PDDocument document;
    private XWPFDocument wordDocument = new XWPFDocument();

    private PDPage page;
    private String path;
    final float tableStart = 50;
    final float tableEnd = 550;

    private String anio;
    private String periodo;

    private final String porcentange[] = {"16%", "46%", "9.5%", "9.5%", "9.5%", "9.5%"};

    public ReporteDAR(String path, String periodo, String anio){
        this.path = path;
        this.periodo = periodo;
        this.anio = anio;
    }

    public void write() throws IOException, COSVisitorException {
        wordDocument = new XWPFDocument();
        document = new PDDocument();

        escribirHeader();
        escribirFooter();

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

                //if (inclusion.getEstado() == EstadoInclusion.ACEPTADA){ //Solo importan las aceptadas :p
                    inclusionesDelGrupo.add(inclusion);
                    inclusionesPorGrupo.put(numeroGrupo, inclusionesDelGrupo);
                //}
            }

            //Con los datos debidamente separados

            for (int numeroGrupo : inclusionesPorGrupo.keySet()){
                List<Inclusion> inclusionesDelGrupo = inclusionesPorGrupo.get(numeroGrupo);

                crearPaginaResultados(inclusionesCurso, inclusionesDelGrupo.get(0).getGrupo());
            }
        }




        //pruebas();

        //wordDocument = new XWPFDocument();
        FileOutputStream out = new FileOutputStream(new File("create_table.docx"));
        wordDocument.write(out);
        out.close();
        //wordDocument.close();
        document.save(path);
        //document.save("../BlankPage.pdf");
        document.close();
    }

    public void pruebas() throws IOException {

    }


    private boolean tieneRn(Inclusion inclusion){
        return inclusion.getEstudiante().isRn();
    }

    private boolean cumpleRequisitos(Inclusion inclusion){
        boolean cumple = true;

        for (Curso curso: inclusion.getGrupo().getCurso().getRequisitos()) {
            if (curso.getNombre().length() > 20) {
                String estado = inclusion.getEstudiante().getCursos().get((curso.getId()));
                if (estado == null) return true;

                estado = estado.toLowerCase();

                if (estado.equals("aprobado") || estado.equals("en curso")){
                    continue;
                }
                cumple = false;
            }

        }
        return cumple;
    }

    private boolean cumpleCorequisitos(Inclusion inclusion){
        boolean cumple = true;

        for (Curso curso: inclusion.getGrupo().getCurso().getCorequisitos()) {
            if (curso.getNombre().length() > 20) {
                String estado = inclusion.getEstudiante().getCursos().get((curso.getId()));
                if (estado == null) return true;

                estado = estado.toLowerCase();

                if (estado.equals("aprobado") || estado.equals("en curso")){
                    continue;
                }
                cumple = false;
            }

        }

        return cumple;
    }

    private boolean tieneChoqueHorario(Inclusion inclusion){
        String requisitos="";
        for (Curso curso: inclusion.getGrupo().getCurso().getRequisitos()) {
            if(curso.getNombre().length()>20){
                String estado = inclusion.getEstudiante().getCursos().get((curso.getId()));
                if (estado==null){
                    estado="Sin info";
                }
                requisitos+= GenericFunctions.splitByNumber(curso.getNombre(),20)[0] + "-"+estado;
                continue;
            }
            requisitos+=curso.getNombre() + "\n";
        }
        return false;
    }

    private void crearPaginaResultados(List<Inclusion> inclusions, Grupo grupo) throws IOException {
        XWPFParagraph paragraph = wordDocument.createParagraph();
        paragraph.setPageBreak(true);

        XWPFTable table = wordDocument.createTable(1, 6); //Base for the upper part
        table.setWidthType(TableWidthType.PCT);
        table.setWidth("100%");
        //Set the first 6 rows

        escribirTitulo(table, grupo);
        escribirAprobados(table, inclusions);
    }

    public void escribirTitulo(XWPFTable table, Grupo grupo) throws IOException {
        XWPFTableRow tableRowOne = table.getRow(0); //Primer fila

        String firstRowText[] = new String[3];

        textoSimple(tableRowOne.getCell(0), "SEDE", true);
        textoSimple(tableRowOne.getCell(1), "CA");
        textoSimple(tableRowOne.getCell(2), "Año " + this.anio, true);
        for (int i = 0; i < 6; i++){
            tableRowOne.getCell(i).setWidth(porcentange[i]);
        }

        XWPFTableRow tableRowTwo = table.createRow(); //Segunda fila
        textoSimple(tableRowTwo.getCell(0), "Código", true);
        textoSimple(tableRowTwo.getCell(1), "Descripción", true);
        textoSimple(tableRowTwo.getCell(2), "Periodo", true);
        textoSimple(tableRowTwo.getCell(4), "Modalidad", true);
        for (int i = 0; i < 6; i++){
            tableRowTwo.getCell(i).setWidth(porcentange[i]);
        }

        XWPFTableRow tableRowThree = table.createRow(); //Segunda fila
        textoSimple(tableRowThree.getCell(0), grupo.getCurso().getId());
        textoSimple(tableRowThree.getCell(1), grupo.getCurso().getNombre());
        textoSimple(tableRowThree.getCell(2), this.periodo);
        textoSimple(tableRowThree.getCell(4), "Semestre");
        for (int i = 0; i < 6; i++){
            tableRowThree.getCell(i).setWidth(porcentange[i]);
        }

        XWPFTableRow tableRowFour = table.createRow(); //Segunda fila
        textoSimple(tableRowFour.getCell(0), "Grupo", true);
        textoSimple(tableRowFour.getCell(1), Integer.toString(grupo.getNumGrupo()));
        for (int i = 0; i < 6; i++){
            tableRowFour.getCell(i).setWidth(porcentange[i]);
        }

        XWPFTableRow tableRowFive = table.createRow(); //Segunda fila
        textoSimple(tableRowFive.getCell(0), "Profesor", true);
        textoSimple(tableRowFive.getCell(1), grupo.getProfesor());
        for (int i = 0; i < 6; i++){
            tableRowFive.getCell(i).setWidth(porcentange[i]);
        }


        //Merging de columnas
        CTHMerge rowOneMerge = CTHMerge.Factory.newInstance();
        rowOneMerge.setVal(STMerge.RESTART);
        tableRowOne.getCell(2).getCTTc().getTcPr().setHMerge(rowOneMerge);

        // Secound Row cell will be merged/"deleted"
        CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
        rowOneMerge.setVal(STMerge.CONTINUE);
        tableRowOne.getCell(3).getCTTc().getTcPr().setHMerge(hMerge1);
        tableRowOne.getCell(4).getCTTc().getTcPr().setHMerge(hMerge1);
        tableRowOne.getCell(5).getCTTc().getTcPr().setHMerge(hMerge1);


        CTHMerge rowTwoMerge1A = CTHMerge.Factory.newInstance();
        rowTwoMerge1A.setVal(STMerge.RESTART);
        tableRowTwo.getCell(2).getCTTc().getTcPr().setHMerge(rowTwoMerge1A);
        tableRowTwo.getCell(4).getCTTc().getTcPr().setHMerge(rowTwoMerge1A);
        tableRowThree.getCell(2).getCTTc().getTcPr().setHMerge(rowTwoMerge1A);
        tableRowThree.getCell(4).getCTTc().getTcPr().setHMerge(rowTwoMerge1A);

        CTHMerge rowTwoMerge1B = CTHMerge.Factory.newInstance();
        rowTwoMerge1B.setVal(STMerge.CONTINUE);
        tableRowTwo.getCell(3).getCTTc().getTcPr().setHMerge(rowTwoMerge1B);
        tableRowTwo.getCell(5).getCTTc().getTcPr().setHMerge(rowTwoMerge1B);
        tableRowThree.getCell(3).getCTTc().getTcPr().setHMerge(rowTwoMerge1B);
        tableRowThree.getCell(5).getCTTc().getTcPr().setHMerge(rowTwoMerge1B);

        CTHMerge rowThreeFourMergeA = CTHMerge.Factory.newInstance();
        rowThreeFourMergeA.setVal(STMerge.RESTART);
        tableRowFour.getCell(1).getCTTc().getTcPr().setHMerge(rowThreeFourMergeA);
        tableRowFive.getCell(1).getCTTc().getTcPr().setHMerge(rowThreeFourMergeA);

        CTHMerge rowThreeFourMergeB = CTHMerge.Factory.newInstance();
        rowThreeFourMergeB.setVal(STMerge.CONTINUE);
        tableRowFour.getCell(2).getCTTc().getTcPr().setHMerge(rowThreeFourMergeB);
        tableRowFour.getCell(3).getCTTc().getTcPr().setHMerge(rowThreeFourMergeB);
        tableRowFour.getCell(4).getCTTc().getTcPr().setHMerge(rowThreeFourMergeB);
        tableRowFour.getCell(5).getCTTc().getTcPr().setHMerge(rowThreeFourMergeB);
        tableRowFive.getCell(2).getCTTc().getTcPr().setHMerge(rowThreeFourMergeB);
        tableRowFive.getCell(3).getCTTc().getTcPr().setHMerge(rowThreeFourMergeB);
        tableRowFive.getCell(4).getCTTc().getTcPr().setHMerge(rowThreeFourMergeB);
        tableRowFive.getCell(5).getCTTc().getTcPr().setHMerge(rowThreeFourMergeB);
    }

    private void escribirAprobados(XWPFTable table, List<Inclusion> inclusiones) throws IOException {
        int cantidadFilas = inclusiones.size();

        String titleText[] = {"Carné", "Nombre del estudiante", "RN", "LR", "LC", "CH"};

        XWPFTableRow titleRow =  table.createRow(); //Segunda fila

        for (int i = 0; i < 6; i++){
            XWPFParagraph titleParagraph = textoSimple(titleRow.getCell(i), titleText[i], true);
            if (i > 1){
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            }
        }

        for (int current = 0; current < cantidadFilas; current++){
            Inclusion inclusion = inclusiones.get(current);
            Estudiante estudiante = inclusion.getEstudiante();
            XWPFTableRow currentRow =  table.createRow(); //Segunda fila
            String bodyText[] = new String[6];

            bodyText[0] = Integer.toString(estudiante.getCarnet());
            bodyText[1] = estudiante.getNombre();
            bodyText[2] = tieneRn(inclusion)? "X" : "";
            bodyText[3] = cumpleRequisitos(inclusion)? "" : "X";
            bodyText[4] = cumpleCorequisitos(inclusion)? "" : "X";
            bodyText[5] = tieneChoqueHorario(inclusion)? "X" : "";

            for (int i = 0; i < 6; i++){
                XWPFParagraph bodyParagraph = textoSimple(currentRow.getCell(i), bodyText[i]);

                if (i > 1){
                    bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
                    //textoEnriquecido(bodyParagraph.createRun(), bodyText[i]);
                }

                for (int c = 0; c < 6; c++){
                    currentRow.getCell(c).setWidth(porcentange[i]);
                }
            }

        }

    }

    private void escribirHeader(){
        wordDocument = new XWPFDocument();
        XWPFHeaderFooterPolicy headerFooterPolicy = wordDocument.getHeaderFooterPolicy();
        System.out.println(headerFooterPolicy == null);
        if (headerFooterPolicy == null) headerFooterPolicy = wordDocument.createHeaderFooterPolicy();
        // create header start
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

        XWPFParagraph paragraph = header.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        paragraph.createRun().setText("INSTITUTO TECNOLOGICO DE COSTA RICA");
        paragraph.setSpacingAfter(0);
        paragraph = header.createParagraph();
        paragraph.createRun().setText("DEPARTAMENTO ADMISION Y REGISTRO");
        paragraph.setSpacingAfter(0);
        paragraph = header.createParagraph();
        paragraph.createRun().setText("AREA DE MATRICULA");


        // create footer start
        XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);

        paragraph = footer.createParagraph();
        //paragraph.setAlignment(ParagraphAlignment.LEFT);


        paragraph.createRun().setText("Rn: Autorizar la no aplicación de los reglamentos por reprobación de asignaturas");
        paragraph.setSpacingAfter(0);
        paragraph = footer.createParagraph();
        paragraph.createRun().setText("LR: Permitir matrícula sin cumplir con los Requisito");
        paragraph.setSpacingAfter(0);
        paragraph = footer.createParagraph();
        paragraph.createRun().setText("LC: Permitir matrícula sin tener matriculado el Correquisito" );
        paragraph.setSpacingAfter(0);
        paragraph = footer.createParagraph();
        paragraph.createRun().setText("CH: Permitir matrícula con choque de horario");


    }

    private void escribirFooter() throws IOException {

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

    private void textoEnriquecido(XWPFRun run, String texto){
        textoEnriquecido(run, texto, false);
    }
}
