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
        System.out.println("Escribiendo PDF");

        for (String codCurso : DataHolder.getInstance().getInclusionesMapPorMateria().keySet()){
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
                //crear pagina de

                crearPaginaResultados(inclusionesCurso, inclusionesDelGrupo.get(0).getGrupo());
            }
        }


        //pruebas();

        document.save("../BlankPage.pdf");
        document.close();

    }

    private void pruebas() throws IOException {
        page = new PDPage(PDPage.PAGE_SIZE_A4);

        document.addPage( page );
        PDPageContentStream cos = new PDPageContentStream(document, page);
        //System.out.println("Pruebas");
        escribirDatosGrupo(page, cos);

        escribirAprobados(page, cos, new ArrayList<Inclusion>());
        cos.close();
    }

    private void crearPaginaResultados(List<Inclusion> inclusions, Grupo grupo) throws IOException {
        PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
        PDPageContentStream cos = new PDPageContentStream(document, page);


        document.addPage( page );
        escribirHeader(page, cos, grupo);
        escribirAprobados(page, cos, inclusions);
        //Create header
        //Create body
    }

    public void escribirHeader(PDPage page, PDPageContentStream contentStream, Grupo grupo) throws IOException {
        ArrayList<String[]> headerContent = new ArrayList<>();
        headerContent.add( new String[]{"SEDE", "CA", "Año "} );
        headerContent.add( new String[]{"Código", "Descripción", "Periodo", "Modalidad"} );
        headerContent.add( new String[]{grupo.getCurso().getId(), grupo.getCurso().getNombre(), "2S-19", "Semestre"} );
        headerContent.add( new String[]{"Grupo", Integer.toString(grupo.getNumGrupo())} );
        headerContent.add( new String[]{"Profesor", grupo.getProfesor(), "Se autoriza inclusión con"} );
        headerContent.add( new String[]{"Carné", "Nombre del estudiante", "RN", "LR", "LC", "CH"});

        float cellSize = 25f;
        float headerTop = page.getMediaBox().getHeight() - 75; //Inicio de la tabla
        float headerBottom = page.getMediaBox().getHeight() - 225;
        float headerStart = inicioTabla;
        float headerEnd = anchoTabla;

        //draw the rows
        float nexty = headerTop; //Inicio de la tabla
        //Filas
        for (int i = 0; i <= 6; i++) {
            contentStream.drawLine(headerStart,nexty,headerEnd,nexty);
            nexty-= cellSize; //Move down on the canvas
        }

        nexty = headerTop; //Inicio de la tabla

        //draw the columns
        //Columnas
        float nextx = inicioTabla;
        contentStream.drawLine(headerStart, headerTop, headerStart, headerBottom); //Izquierda
        contentStream.drawLine(headerStart+100,nexty,headerStart+100,headerBottom);
        contentStream.drawLine(headerStart+350,nexty,headerStart+350,headerBottom);
        contentStream.drawLine(headerStart+415,nexty-25,headerStart+415,nexty-75);

        contentStream.drawLine(nextx+350,headerBottom,nextx+350,headerBottom+25);

        float baseY = page.getMediaBox().getHeight() - 225;

        contentStream.drawLine(nextx+387.5f,baseY + 25,nextx+387.5f,baseY);
        contentStream.drawLine(nextx+425,baseY + 25,nextx+425,baseY);
        contentStream.drawLine(nextx+462.5f,baseY + 25,nextx+462.5f,baseY);
        contentStream.drawLine(nextx+500,baseY + 25,nextx+500,baseY);

        contentStream.drawLine(headerEnd,nexty,headerEnd,headerBottom); //Derecha

        nexty = page.getMediaBox().getHeight() - 90; //Inicio de la tabla

        float xPositions[] = {inicioTabla+10, inicioTabla+110, inicioTabla+360};
        contentStream.setFont(PDType1Font.HELVETICA,12);
        escribirFila(contentStream, xPositions, headerTop - 17.5f,headerContent.get(0) );

        //Segunda fila
        contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
        xPositions = new float[] {inicioTabla+5, inicioTabla+105, inicioTabla+360, inicioTabla+425};
        escribirFila(contentStream, xPositions, headerTop - 44.5f,headerContent.get(1) );

        //Tercera fila -- contenido de la anterior, por eso el mismo xPositions
        contentStream.setFont(PDType1Font.HELVETICA,12);
        escribirFila(contentStream, xPositions, headerTop - 44.5f - 25f,headerContent.get(2) );

        //Cuarta Fila
        contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
        xPositions = new float[] {inicioTabla+5, inicioTabla+105};
        escribirFila(contentStream, xPositions, headerTop - 44.5f - 50f,headerContent.get(3) );

        //Quinta fila
        contentStream.setFont(PDType1Font.HELVETICA,12);
        xPositions = new float[] {inicioTabla+5, inicioTabla+105, inicioTabla+360};
        escribirFila(contentStream, xPositions, headerTop - 44.5f - 75f,headerContent.get(4) );

        //Sexta fila
        xPositions = new float[] {inicioTabla+5, inicioTabla+105, inicioTabla+360};
        escribirFila(contentStream, xPositions, headerTop - 44.5f - 75f,headerContent.get(4) );

        xPositions = new float[] {headerStart+5, headerStart+105, headerStart+355, headerStart+392.5f, headerStart+430, headerStart+467.5f};
        escribirFila(contentStream, xPositions, headerTop - 44.5f - 100f, headerContent.get(5) );
    }




    ///DE PRUEBA
    public void escribirDatosGrupo(PDPage page, PDPageContentStream contentStream) throws IOException {
        //Test values
        ArrayList<String[]> headerContent = new ArrayList<>();
        headerContent.add( new String[]{"SEDE", "CA", "Año "} );
        headerContent.add( new String[]{"Código", "Descripción", "Periodo", "Modalidad"} );
        headerContent.add( new String[]{"ATI-7001", "Inteligencia Artificial", "2S-19", "Semestre"} );
        headerContent.add( new String[]{"Grupo", "2"} );
        headerContent.add( new String[]{"Profesor", "Uno de ATI", "Se autoriza inclusión con"} );
        headerContent.add( new String[]{"Carné", "Nombre del estudiante", "RN", "LR", "LC", "CH"});

        float cellSize = 25f;
        float headerTop = page.getMediaBox().getHeight() - 75; //Inicio de la tabla
        float headerBottom = page.getMediaBox().getHeight() - 225;
        float headerStart = inicioTabla;
        float headerEnd = anchoTabla;

        //draw the rows
        float nexty = headerTop; //Inicio de la tabla
        //Filas
        for (int i = 0; i <= 6; i++) {
            contentStream.drawLine(headerStart,nexty,headerEnd,nexty);
            nexty-= cellSize; //Move down on the canvas
        }

        nexty = headerTop; //Inicio de la tabla

        //draw the columns
        //Columnas
        float nextx = inicioTabla;
        contentStream.drawLine(headerStart, headerTop, headerStart, headerBottom); //Izquierda
        contentStream.drawLine(headerStart+100,nexty,headerStart+100,headerBottom);
        contentStream.drawLine(headerStart+350,nexty,headerStart+350,headerBottom);
        contentStream.drawLine(headerStart+415,nexty-25,headerStart+415,nexty-75);

        contentStream.drawLine(nextx+350,headerBottom,nextx+350,headerBottom+25);

        float baseY = page.getMediaBox().getHeight() - 225;

        contentStream.drawLine(nextx+387.5f,baseY + 25,nextx+387.5f,baseY);
        contentStream.drawLine(nextx+425,baseY + 25,nextx+425,baseY);
        contentStream.drawLine(nextx+462.5f,baseY + 25,nextx+462.5f,baseY);
        contentStream.drawLine(nextx+500,baseY + 25,nextx+500,baseY);

        contentStream.drawLine(headerEnd,nexty,headerEnd,headerBottom); //Derecha

        nexty = page.getMediaBox().getHeight() - 90; //Inicio de la tabla

        float xPositions[] = {inicioTabla+10, inicioTabla+110, inicioTabla+360};
        contentStream.setFont(PDType1Font.HELVETICA,12);
        escribirFila(contentStream, xPositions, headerTop - 17.5f,headerContent.get(0) );

        //Segunda fila
        contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
        xPositions = new float[] {inicioTabla+5, inicioTabla+105, inicioTabla+360, inicioTabla+425};
        escribirFila(contentStream, xPositions, headerTop - 44.5f,headerContent.get(1) );

        //Tercera fila -- contenido de la anterior, por eso el mismo xPositions
        contentStream.setFont(PDType1Font.HELVETICA,12);
        escribirFila(contentStream, xPositions, headerTop - 44.5f - 25f,headerContent.get(2) );

        //Cuarta Fila
        contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
        xPositions = new float[] {inicioTabla+5, inicioTabla+105};
        escribirFila(contentStream, xPositions, headerTop - 44.5f - 50f,headerContent.get(3) );

        //Quinta fila
        contentStream.setFont(PDType1Font.HELVETICA,12);
        xPositions = new float[] {inicioTabla+5, inicioTabla+105, inicioTabla+360};
        escribirFila(contentStream, xPositions, headerTop - 44.5f - 75f,headerContent.get(4) );

        //Sexta fila
        xPositions = new float[] {inicioTabla+5, inicioTabla+105, inicioTabla+360};
        escribirFila(contentStream, xPositions, headerTop - 44.5f - 75f,headerContent.get(4) );

        xPositions = new float[] {headerStart+5, headerStart+105, headerStart+355, headerStart+392.5f, headerStart+430, headerStart+467.5f};
        escribirFila(contentStream, xPositions, headerTop - 44.5f - 100f, headerContent.get(5) );
    }

    private void escribirFila(PDPageContentStream cs, float[] xPositions, float rowY, String[] textos) throws IOException {

        for (int index = 0; index < xPositions.length; index++){
            cs.beginText();
            cs.moveTextPositionByAmount(xPositions[index], rowY);
            cs.drawString(textos[index]);
            cs.endText();
        }
    }

    private void escribirAprobados(PDPage page, PDPageContentStream cs, List<Inclusion> inclusiones) throws IOException {

        int cantidadFilas = inclusiones.size();

        float cellSize = 25f;
        float contentTop = page.getMediaBox().getHeight() - 225; //Inicio de la tabla
        float contentBottom = contentTop - cellSize * cantidadFilas;
        float contentStart = inicioTabla;
        float contentEnd = anchoTabla;

        float rowYIndex = contentTop;
        for (int i = 0; i <= cantidadFilas; i++) {
            cs.drawLine(contentStart, rowYIndex, contentEnd, rowYIndex);
            rowYIndex-= cellSize; //Move down on the canvas
        }

        rowYIndex = contentTop;
        float xPositions[] = new float[] {contentStart+5, contentStart+105, contentStart+355, contentStart+392.5f, contentStart+430, contentStart+467.5f};

        for (Inclusion inclusion : inclusiones){
            String textos[] = new String[6];
            textos[0] = Integer.toString(inclusion.getEstudiante().getCarnet());
            textos[1] = inclusion.getEstudiante().getNombre();
            textos[2] = "";
            textos[3] = "";
            textos[4] = "";
            textos[5] = "";

            escribirFila(cs, xPositions, rowYIndex, textos);
            rowYIndex-=cellSize;
        }
    }




}
