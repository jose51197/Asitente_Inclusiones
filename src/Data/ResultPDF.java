package Data;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;

public class ResultPDF {

    PDDocument document;
    PDPage page;

    public void write(){
        page = new PDPage();
        document = new PDDocument();
        document.addPage( page );
        try {
            document.save("BlankPage.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        }


    }
}
