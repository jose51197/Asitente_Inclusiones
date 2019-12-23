package Data;

import Model.Curso;
import Model.DataHolder;
import Model.Grupo;
import Model.Inclusion;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.awt.*;
import java.awt.Button;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Reporte {
    protected List<String> warningLog;
    protected List<String> errorLog;

    protected XWPFDocument document = new XWPFDocument();

    public Reporte(){
        warningLog = new ArrayList<>();
        errorLog = new ArrayList<>();
    }

    public abstract void write();

    protected void save(){
        if (warningLog.size() > 0) showWarningLog();
        if (errorLog.size() > 0){
            showErrorLog();
            return;
        }

        File path = null;
        try{
            Desktop desktop = Desktop.getDesktop();
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Ruta para guardar el archivo");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word files (*.docx)", "*.docx");
            fileChooser.getExtensionFilters().add(extFilter);

            path = fileChooser.showSaveDialog(stage);

            if (path == null) return;

            FileOutputStream out = new FileOutputStream(path);
            document.write(out);
            out.close();

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("¿Desea abrir el documento?");
            a.setTitle("Se guardó con exito");

            Optional<ButtonType> result = a.showAndWait();

            if (result.get() == ButtonType.OK){
                desktop.open(path);
            }

            stage.close();
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
                String warning = "[Requisito]: No se encuentra información sobre el estudiante " + inclusion.getEstudiante().getCarnet()
                + " para el curso de " + inclusion.getGrupo().getCurso().getNombre() + " con el número de grupo" + inclusion.getGrupo().getNumGrupo()
                        + ". Se guarda como \"Cumple requisitos \".";
                warningLog.add(warning);
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
                String warning = "[Corequisito]: No se encuentra información sobre el estudiante " + inclusion.getEstudiante().getCarnet()
                        + " para el curso de " + inclusion.getGrupo().getCurso().getNombre() + " con el número de grupo" + inclusion.getGrupo().getNumGrupo()
                        + ". Se guarda como \"Cumple corequisitos \".";
                warningLog.add(warning);
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

    protected void showErrorLog(){
        String listadoErrores = "";
        for(String error: errorLog){
            listadoErrores += error + '\n';
            System.out.println(error);
        }

        if (listadoErrores.equals("")) return;

        TextArea area = new TextArea(listadoErrores);
        area.setWrapText(true);
        area.setEditable(false);

        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.ERROR);
        a.getDialogPane().setContent(area);
        a.setTitle("Errores al escribir el archivo");
        a.show();
    }

    protected void showWarningLog(){
        String contenido = "";
        for(String error: warningLog){
            contenido += error + '\n';
            System.out.println(error);
        }

        if (contenido.equals("")) return;

        TextArea area = new TextArea(contenido);
        area.setWrapText(true);
        area.setEditable(false);

        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.ERROR);
        a.getDialogPane().setContent(area);
        a.setTitle("Alertas al escribir el archivo");
        a.show();
    }
}
