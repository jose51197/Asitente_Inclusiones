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

        //Filas
        for (int i = 0; i <= 5; i++) {
            contentStream.drawLine(inicioTabla,nexty,anchoTabla,nexty);
            nexty+= 25f;
        }

        nexty = page.getMediaBox().getHeight() - 200; //Inicio de la tabla

        //draw the columns
        float nextx = inicioTabla;
        contentStream.drawLine(inicioTabla,nexty,inicioTabla,nexty+125); //Izquierda
        contentStream.drawLine(inicioTabla+100,nexty,inicioTabla+100,nexty+125);
        contentStream.drawLine(inicioTabla+350,nexty,inicioTabla+350,nexty+125);
        contentStream.drawLine(inicioTabla+400,nexty,inicioTabla+400,nexty+100);
        contentStream.drawLine(anchoTabla,nexty,anchoTabla,nexty+125); //Derecha


        float xPositions[] = {inicioTabla+20, inicioTabla+120, inicioTabla+370};
        for (int index = 0; index < 3; index++){
            contentStream.setFont(PDType1Font.HELVETICA,12);
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(xPositions[index], nexty);
            contentStream.drawString(headerContent.get(0)[index]);
            contentStream.endText();
        }
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
