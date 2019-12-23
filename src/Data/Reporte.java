package Data;

import Model.Curso;
import Model.Grupo;
import Model.Inclusion;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class Reporte {
    protected XWPFDocument document = new XWPFDocument();

    public abstract void write();

    protected void save(){

        File path = null;

        try{
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word files (*.docx)", "*.docx");
            fileChooser.getExtensionFilters().add(extFilter);

            path = fileChooser.showSaveDialog(stage);

            FileOutputStream out = new FileOutputStream(path);
            document.write(out);
            out.close();

        }
        catch (FileNotFoundException e){
            System.out.println( "F not found " + e.toString());
        } catch (IOException e){
            System.out.println("IO " + e.toString());
        }
    }

    protected boolean tieneRn(Inclusion inclusion){
        return inclusion.getEstudiante().isRn();
    }

    protected boolean cumpleRequisitos(Inclusion inclusion){
        boolean cumple = true;
        for (Curso curso: inclusion.getGrupo().getCurso().getRequisitos()) {
            String estado = inclusion.getEstudiante().getCursos().get(curso.getId());

            if (estado == null) {
                //Add warning box here
                //"Sin info";
                //No se encuentra la informacion del estudiante con respecto al curso xx, se coloca como que cumple los requisitos
                continue;
            }

            if (estado.equals("aprobado") || estado.equals("en curso")) {
                continue;
            }

            cumple = false;
        }
        return cumple;
    }

    protected boolean cumpleCorequisitos(Inclusion inclusion){
        boolean cumple = true;
        for (Curso curso: inclusion.getGrupo().getCurso().getCorequisitos()) {
            String estado = inclusion.getEstudiante().getCursos().get(curso.getId());

            if (estado == null) {
                //Add warning box here
                //"Sin info";
                //No se encuentra la informacion del estudiante con respecto al curso xx, se coloca como que cumple los requisitos
                continue;
            }

            if (estado.equals("aprobado") || estado.equals("en curso")) {
                continue;
            }

            cumple = false;
        }
        return cumple;
    }

    protected boolean tieneChoqueHorario(Inclusion inclusion) {
        Grupo grupoInlcusion = inclusion.getGrupo();
        Map<String, Grupo> gruposMatriculados = inclusion.getEstudiante().getGrupos();

        for (String key : gruposMatriculados.keySet()) {
            Grupo grupoActual = gruposMatriculados.get(key);
            if (grupoActual.equals(inclusion.getGrupo())) continue; //Caso extraño

            if (grupoActual.tieneChoqueDeHorario(grupoInlcusion)) return true;
        }

        return false;
    }

    protected void showErrorLog(String erorrs){

    }
}
