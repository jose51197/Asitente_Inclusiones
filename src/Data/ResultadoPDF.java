package Data;

import Model.*;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultadoPDF {

    PDDocument document;
    PDPage page;
    final float inicioTabla = 50;
    final float anchoTabla = 550;

    public void write() throws IOException, COSVisitorException {
        document = new PDDocument();
        Map<String, List<Grupo>> cursos = new HashMap<>(); //Necesito un query de esto :/
        for (String codCurso : DataHolder.getInstance().getGrupos().keySet()){

            Grupo grupo = DataHolder.getInstance().getGrupos().get(codCurso);
            List<Grupo> gruposGuardados = cursos.get(grupo.getCurso().getId());

            if ( gruposGuardados == null ){
                gruposGuardados = new ArrayList<>();
            }

            gruposGuardados.add(grupo);
            cursos.put(grupo.getCurso().getId(), gruposGuardados);
        }

        ///Fin de funcion para pruebas

        for (String codCurso : cursos.keySet()){
            escribirCurso(cursos.get(codCurso));
        }

        pruebas();

        document.save("BlankPage.pdf");
        document.close();

    }

    private void pruebas() throws IOException {
        page = new PDPage(PDPage.PAGE_SIZE_A4);

        document.addPage( page );
        PDPageContentStream cos = new PDPageContentStream(document, page);
        System.out.println("Pruebas");
        escribirDatosGrupo(page, cos);
        cos.close();

    }



    public void escribirCurso(List<Grupo> grupos) throws IOException {
        for (Grupo grupo : grupos){
            escribirGrupo(grupo);
        }
    }

    public void escribirGrupo(Grupo grupo) throws IOException {
        page = new PDPage(PDPage.PAGE_SIZE_A4);
        PDRectangle rect = page.getMediaBox();
        PDPageContentStream cos;
        System.out.println("Pruebas");
        document.addPage( page );
    }

    ///DE PRUEBA
    public void escribirDatosGrupo(PDPage page, PDPageContentStream contentStream) throws IOException {
        //draw the rows
        float nexty = page.getMediaBox().getHeight() - 200; //Inicio de la tabla

        ArrayList<String[]> headerContent = new ArrayList<>();
        headerContent.add( new String[]{"SEDE", "CA", "Año "} );
        headerContent.add( new String[]{"Código", "Descripción", "Periodo", "Modalidad"} );
        headerContent.add( new String[]{"ATI-7001", "Inteligencia Artificial", "2S-19", "Semestre"} );
        headerContent.add( new String[]{"Grupo", "2"} );
        headerContent.add( new String[]{"Profesor", "Uno de ATI", "Se autoriza inclusión con"} );

        //Filas
        for (int i = 0; i <= 5; i++) {
            contentStream.drawLine(inicioTabla,nexty,anchoTabla,nexty);
            nexty+= 25f;
        }

        nexty = page.getMediaBox().getHeight() - 200; //Inicio de la tabla

        //draw the columns
        //Columnas
        float nextx = inicioTabla;
        contentStream.drawLine(nextx,nexty,inicioTabla,nexty+125); //Izquierda
        contentStream.drawLine(nextx+100,nexty,nextx+100,nexty+125);
        contentStream.drawLine(nextx+350,nexty,nextx+350,nexty+125);
        contentStream.drawLine(nextx+415,nexty+50,nextx+415,nexty+100);
        contentStream.drawLine(anchoTabla,nexty,anchoTabla,nexty+125); //Derecha

        nexty = page.getMediaBox().getHeight() - 90; //Inicio de la tabla

        float xPositions[] = {inicioTabla+10, inicioTabla+110, inicioTabla+360};
        contentStream.setFont(PDType1Font.HELVETICA,12);
        for (int index = 0; index < 3; index++){
            escribirLinea(contentStream, xPositions[index], nexty, headerContent.get(0)[index]);
        }

        xPositions = new float[] {inicioTabla+5, inicioTabla+105, inicioTabla+360, inicioTabla+425};
        for (int index = 0; index < 4; index++){
            contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(xPositions[index], nexty-25);
            contentStream.drawString(headerContent.get(1)[index]);
            contentStream.endText();
        }

        xPositions = new float[] {inicioTabla+5, inicioTabla+105, inicioTabla+360, inicioTabla+425};
        for (int index = 0; index < 4; index++){
            contentStream.setFont(PDType1Font.HELVETICA,12);
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(xPositions[index], nexty-50);
            contentStream.drawString(headerContent.get(2)[index]);
            contentStream.endText();
        }

        xPositions = new float[] {inicioTabla+5, inicioTabla+105};
        for (int index = 0; index < 2; index++){
            contentStream.setFont(PDType1Font.HELVETICA,12);
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(xPositions[index], nexty-75);
            contentStream.drawString(headerContent.get(3)[index]);
            contentStream.endText();
        }

        xPositions = new float[] {inicioTabla+5, inicioTabla+105, inicioTabla+360};
        for (int index = 0; index < 3; index++){
            contentStream.setFont(PDType1Font.HELVETICA,12);
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(xPositions[index], nexty-100);
            contentStream.drawString(headerContent.get(4)[index]);
            contentStream.endText();
        }

        String body[] = {"Carné", "Nombre del estudiante", "RN", "LR", "LC", "CH"};

        //Filas
        nexty = page.getMediaBox().getHeight() - 200; //Inicio de la tabla
        for (int i = 0; i <= 5; i++) {
            contentStream.drawLine(inicioTabla,nexty,anchoTabla,nexty);
            nexty-= 25f;
        }

        xPositions = new float[] {inicioTabla+5, inicioTabla+105, inicioTabla+360, inicioTabla+425};




    }

    private void escribirLinea(PDPageContentStream cs, float x, float y, String texto) throws IOException {
        cs.beginText();
        cs.moveTextPositionByAmount(x, y);
        cs.drawString(texto);
        cs.endText();
    }

    public void escribirDatosGrupo(PDPage page, PDPageContentStream contentStream, Grupo grupo) throws IOException {
        //draw the rows
        float nexty = 50;
        ArrayList<String[]> headerContent = new ArrayList<>();
        headerContent.add( new String[]{"SEDE", "CA", "Año "} );
        headerContent.add( new String[]{"Código", "Descripción", "Periodo", "Modalidad"} );
        headerContent.add( new String[]{grupo.getCurso().getId(), grupo.getCurso().getNombre(), "Periodo", "Semestre"} );

        for (int i = 0; i <= 4; i++) {
            contentStream.drawLine(50,nexty,500,nexty);
            nexty-= 20f;
        }

        //draw the columns
        float nextx = 50;
        for (int i = 0; i <= 4; i++) {
            contentStream.drawLine(nextx,50,nextx,200);
            nextx += 25;
        }
    }

    public void escribirResultadosGrupo(PDPage page, PDPageContentStream contentStream, Grupo grupo) throws IOException {
        //draw the rows
        float nexty = 500;
        ArrayList<String[]> headerContent = new ArrayList<>();
        headerContent.add( new String[]{"SEDE", "CA", "Año "} );
        headerContent.add( new String[]{"Código", "Descripción", "Periodo", "Modalidad"} );
        //headerContent.add( new String[]{grupo.getCurso().getId(), grupo.getCurso().getNombre(), "Periodo", "Semestre"} );

        float posicionX = inicioTabla;
        float posicionY = 500;
        for (int i = 0; i <= 4; i++) {
            contentStream.drawLine(posicionX,nexty,anchoTabla,nexty);
            nexty-= 20f;
        }

        //draw the columns
        float nextx = 50;
        for (int i = 0; i <= 4; i++) {
            contentStream.drawLine(nextx,50,nextx,200);
            nextx += 25;
        }
    }


    private class Linea{
        float x, y, xEnd, yEnd;

        public Linea(float x, float y, float xEnd, float yEnd) {
            this.x = x;
            this.y = y;
            this.xEnd = xEnd;
            this.yEnd = yEnd;
        }
    }
}
