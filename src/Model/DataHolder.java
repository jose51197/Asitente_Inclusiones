package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataHolder {
    private Map<String,Map<String, Curso>> malla;
    private Map<Integer, Estudiante> estudiantes;
    private Map<String, Aula> aulas;
    private Map<String, Grupo> grupos;
    private Map <Integer, ArrayList<Inclusion>> inclusionesMapPorEstudiante;
    private Map <String, ArrayList<Inclusion>> inclusionesMapPorMateria;
    private ArrayList<Inclusion> inclusiones;
    private ArrayList<String> errores;
    private static DataHolder instance ;

    private DataHolder (){
        malla= new HashMap<>();
        malla.put("N/A",new HashMap<>());
        estudiantes= new HashMap<>();
        aulas = new HashMap<>();
        grupos = new HashMap<>();
        inclusionesMapPorEstudiante= new HashMap<>();
        inclusionesMapPorMateria= new HashMap<>();
        inclusiones = new ArrayList<>();
        errores = new ArrayList<>();
    }

    public void resetDataHolder(){
        malla= new HashMap<>();
        malla.put("N/A",new HashMap<>());
        estudiantes= new HashMap<>();
        aulas = new HashMap<>();
        grupos = new HashMap<>();
        inclusionesMapPorEstudiante= new HashMap<>();
        inclusionesMapPorMateria= new HashMap<>();
        inclusiones = new ArrayList<>();
        errores = new ArrayList<>();
    }

    public void resetInclusiones(){
        inclusionesMapPorEstudiante= new HashMap<>();
        inclusionesMapPorMateria= new HashMap<>();
        inclusiones = new ArrayList<>();
    }

    public static DataHolder getInstance(){
        if (instance==null){
            instance= new DataHolder();
        }
        return instance;
    }

    public Map<String,Map<String, Curso>> getMalla() {
        return malla;
    }

    public void setMalla(Map<String,Map<String, Curso>> malla) {
        this.malla = malla;
    }

    public Map<Integer, Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Map<Integer, Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Map<String, Aula> getAulas() {
        return aulas;
    }

    public void setAulas(Map<String, Aula> aulas) {
        this.aulas = aulas;
    }

    public Map<String, Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(Map<String, Grupo> grupos) {
        this.grupos = grupos;
    }

    public Map<Integer, ArrayList<Inclusion>> getInclusionesMapPorEstudiante() {
        return inclusionesMapPorEstudiante;
    }

    public void setInclusionesMapPorEstudiante(Map<Integer, ArrayList<Inclusion>> inclusionesMap) {
        this.inclusionesMapPorEstudiante = inclusionesMap;
    }

    public ArrayList<Inclusion> getInclusiones() {
        return inclusiones;
    }

    public void setInclusiones(ArrayList<Inclusion> inclusiones) {
        this.inclusiones = inclusiones;
    }

    public Map<String, ArrayList<Inclusion>> getInclusionesMapPorMateria() {
        return inclusionesMapPorMateria;
    }

    public void setInclusionesMapPorMateria(Map<String, ArrayList<Inclusion>> inclusionesMapPorMateria) {
        this.inclusionesMapPorMateria = inclusionesMapPorMateria;
    }

    public ArrayList<String> getErrores() {
        return errores;
    }

    public void setErrores(ArrayList<String> errores) {
        this.errores = errores;
    }

    public void clearErrores() {
        this.errores = new ArrayList<>();
    }

    public void addError(String error) {
        this.errores.add(error);
    }

    public Curso getCursoInPlanes(String nombreCurso){
        Curso curso=null;
        for(String plan:this.malla.keySet()){
            curso=malla.get(plan).get(nombreCurso);
            if(curso != null){
                return curso;
            }
        }
        return curso;
    }

    public void saveStatus() throws IOException {
        FileWriter csvWriter = new FileWriter("inclusionesResult.csv");
        csvWriter.append("Email,Carnet,Nombre,Grupo,Plan B,Detalle,Estado,Comentario");
        csvWriter.append("\n");
        String planB;
        for (Inclusion inclusion: inclusiones){
            planB = inclusion.isPlanB() ? "Si" : "No";
            csvWriter.append(inclusion.getCorreo()+","+inclusion.getCarne()+","+inclusion.getEstudiante().getNombre()+","+inclusion.getCodGrupo()+","+planB+","+inclusion.getDetalle()+","+inclusion.getEstado().toString()+","+inclusion.getComentarioAdmin()+"\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }


    public void saveGroups() {
        //Guardar otra info
        for (String key : DataHolder.getInstance().getGrupos().keySet()) {
            Grupo grupo = DataHolder.getInstance().getGrupos().get(key);

        }
    }
}
