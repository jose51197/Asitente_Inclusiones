package Model;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.*;

public class DataLoader {
    private Excelreader excelreader = new Excelreader();
    private DataHolder dataHolder= DataHolder.getInstance();


    public void getEstudiantes(String filepathStudents,String filepathRN) throws IOException {
        Map<String, ArrayList<ArrayList<String>>> data= excelreader.readXlsxFile(filepathStudents);
        Map<String, Curso> plan = null;
        for(String sheetName: data.keySet()){
            ArrayList<ArrayList<String>> sheet = data.get(sheetName);
            if(sheetName.toLowerCase().contains("plan")){
                addEstudiante(sheet,sheetName.toLowerCase());
            }
        }
        for(String sheetName: data.keySet()){
            ArrayList<ArrayList<String>> sheet = data.get(sheetName);
            if(sheetName.toLowerCase().contains("horarios")){
                addHorario(sheet,sheetName.toLowerCase());
            }
        }
        addRN(filepathRN);
    }

    private void addEstudiante(ArrayList<ArrayList<String>> sheet,String sheetName){
        Map<Integer, Estudiante> estudiantes= DataHolder.getInstance().getEstudiantes();
        ArrayList<String> columns= sheet.get(0);
        for(int i=1; i <sheet.size();i++){
            try {
                ArrayList<String> row = sheet.get(i);
                int carnet = new BigDecimal(row.get(0)).setScale(0, RoundingMode.HALF_UP).intValue();
                String nombre = row.get(1);
                Estudiante estudiante = new Estudiante(carnet, nombre, sheetName);
                estudiante.setPonderado(Double.valueOf(row.get(2)));
                estudiante.setPlan(sheetName);
                Map<String, String> cursos = estudiante.getCursos();
                for (int j = 2; j < row.size(); j++) {
                    cursos.put(columns.get(j), row.get(j));
                }
                estudiantes.put(carnet, estudiante);
            }
            catch (Exception e){
                System.out.println("estudiante invalido");
            }
        }
    }

    private void addRN(String filepath) throws IOException {
        ArrayList<ArrayList<String>> data= excelreader.readCsvFiles(filepath);
        Map<Integer, Estudiante> estudiantes= DataHolder.getInstance().getEstudiantes();
        int carnet=-1;
        data.remove(0);
        Estudiante estudiante = null;
        for(ArrayList<String> row: data){
            int carnetAux= Integer.valueOf(row.get(0));
            if(carnetAux!=carnet){
                carnet=carnetAux;
                estudiante=estudiantes.get(carnet);
            }
            if(!row.get(2).equals("1.0")){
                if(estudiante!=null){
                    estudiante.setRn(true);
                }
            }
        }
    }

    private void addHorario(ArrayList<ArrayList<String>> sheet,String sheetName){
        Map<String, Grupo> grupos = dataHolder.getGrupos();
        Map<Integer, Estudiante> estudiantes= DataHolder.getInstance().getEstudiantes();
        Map<String, Curso> plan= dataHolder.getMalla().get(sheetName.replace("horarios","plan"));
        int carnet=-1;
        Estudiante estudiante=null;
        Map<String, Grupo> gruposEstudiante=null;
        for(int i=1; i <sheet.size();i++){
            ArrayList<String> dataRow= sheet.get(i);
            try{
                int carnetAux= new BigDecimal(dataRow.get(0)).setScale(0, RoundingMode.HALF_UP).intValue();
                if(carnetAux!=carnet){
                    carnet=carnetAux;
                    estudiante=estudiantes.get(carnet);
                }
            }
            catch (Exception e){
                System.out.println("Se detecto estudiante invalido");
            }

            String idGrupo="GR"+String.valueOf(Double.valueOf(dataRow.get(3)).intValue())+dataRow.get(1);
            Grupo grupo=grupos.get(idGrupo);
            if(grupo==null){
                grupo= new Grupo(Double.valueOf(dataRow.get(3)).intValue(),dataRow.get(4),plan.get(dataRow.get(1)));
                grupos.put(idGrupo,grupo);
            }
            LocalTime horaInicio= LocalTime.ofSecondOfDay(Double.valueOf(86401*Double.valueOf(dataRow.get(7))).longValue());
            LocalTime horaFinal= LocalTime.ofSecondOfDay(Double.valueOf(86401*Double.valueOf(dataRow.get(8))).longValue());
            Horario horario= new Horario(dataRow.get(6),dataRow.get(5),horaInicio,horaFinal);
            if(grupo.notContainsHorario(horario.getDia())){
                grupo.addHorario(horario);
            }
            if(estudiante!=null){
                gruposEstudiante = estudiante.getGrupos();
                gruposEstudiante.put(idGrupo,grupo);
            }
        }
    }

    public void getPlanes(String filepath) throws IOException {
        Map<String, ArrayList<ArrayList<String>>> data = excelreader.readXlsxFile(filepath);
        for(String sheetName: data.keySet()){
            ArrayList<ArrayList<String>> sheet = data.get(sheetName);
            Curso actual;
            Map<String, Curso> result = new HashMap<String, Curso>();
            sheet.remove(0);
            for(ArrayList<String> row :sheet){
                String codigo= row.get(0);
                actual=new Curso(codigo,row.get(1),Double.valueOf(row.get(2)).intValue(),Double.valueOf(row.get(5)).intValue());
                result.put(codigo,actual);
            }
            for(ArrayList<String> row :sheet){
                String codigo= row.get(0);
                String[] requisitos = row.get(3).split(",");
                String[] corequisitos = row.get(4).split(",");
                for(String requisito:requisitos){
                    if(result.containsKey(requisito)) {
                        Curso requisitoActual = result.get(requisito);
                        actual = result.get(codigo);
                        actual.addRequisito(requisitoActual);
                    }
                }
                for(String corequisito:corequisitos){
                    if(result.containsKey(corequisito)) {
                        Curso corequisitoActual = result.get(corequisito);
                        actual = result.get(codigo);
                        actual.addCorequisito(corequisitoActual);
                    }
                }
            }
            dataHolder.getMalla().put(sheetName.toLowerCase(),result);
        }

    }




    public void getInclusiones(String filepath ) throws IOException {
        ArrayList<ArrayList<String>> data= excelreader.readCsvFiles(filepath);
        Map<Integer, Estudiante> estudiantes = dataHolder.getEstudiantes();
        Map<String, Grupo> grupos = dataHolder.getGrupos();
        Map<Integer, ArrayList<Inclusion>> inclusionesEstudianteMap = dataHolder.getInclusionesMapPorEstudiante();
        Map<String, ArrayList<Inclusion>> inclusionesMateriaMap = dataHolder.getInclusionesMapPorMateria();
        data.remove(0);
        ArrayList<Inclusion>  result = dataHolder.getInclusiones();
        int linea=0;
        Estudiante estudiante=null;
        for(ArrayList<String> row :data){
            linea++;
            try{
                estudiante= estudiantes.get(Integer.valueOf(row.get(2)));
            }
            catch (Exception e){
                dataHolder.addError("Inclusión en la linea "+Integer.toString(linea)+" no tiene un carnet válido");
                continue;
            }
            if(estudiante== null){
                try
                {
                    int carnet=Integer.parseInt(row.get(2));
                    estudiante= new Estudiante(carnet,row.get(4),"N/A");
                    estudiantes.put(carnet,estudiante);
                    dataHolder.addError("Estudiante con carnet "+ Integer.toString(carnet)+", en la linea "+Integer.toString(linea)+" no se encontro, se creo nuevo perfil");
                }
                catch (NumberFormatException e)
                {
                    dataHolder.addError("Inclusión en la linea "+Integer.toString(linea)+" no tiene un carnet válido");
                    continue;
                }
            }
            String[] datosCurso=row.get(6).split(" - ");
            Grupo grupo = grupos.get(datosCurso[1]+datosCurso[0]);
            if(grupo== null){
                try
                {
                    int numGrupo=Integer.parseInt(datosCurso[1]);
                    Curso curso=dataHolder.getCursoInPlanes(datosCurso[0]);
                    if(curso==null){
                        curso= new Curso(datosCurso[0],datosCurso[2],-1,-1);
                        dataHolder.getMalla().get("N/A").put(datosCurso[0],curso);
                        dataHolder.addError("Curso código "+ datosCurso[0]+" no se encontro, se creo nuevo curso en plan N/A");
                    }
                    grupo = new Grupo(numGrupo,"No disponible",curso);

                }
                catch (Exception e)
                {
                    dataHolder.addError("Inclusión en la linea "+Integer.toString(linea)+" no tiene un grupo válido");
                    continue;
                }
            }
            boolean planB = (row.get(7).equals("Si"));
            Inclusion nuevaInclusion= new Inclusion(planB, grupo,estudiante,row.get(8),row.get(1));
            result.add(nuevaInclusion);
            ArrayList<Inclusion> inclusionesAux;
            if(inclusionesEstudianteMap.containsKey(estudiante.getCarnet())){
                inclusionesAux=inclusionesEstudianteMap.get(estudiante.getCarnet());
                inclusionesAux.add(nuevaInclusion);
            }
            else{
                inclusionesAux= new ArrayList<>();
                inclusionesAux.add(nuevaInclusion);
                inclusionesEstudianteMap.put(estudiante.getCarnet(),inclusionesAux);
            }
            if(inclusionesMateriaMap.containsKey(datosCurso[0])){
                inclusionesAux=inclusionesMateriaMap.get(datosCurso[0]);
                inclusionesAux.add(nuevaInclusion);
            }
            else{
                inclusionesAux= new ArrayList<>();
                inclusionesAux.add(nuevaInclusion);
                inclusionesMateriaMap.put(datosCurso[0],inclusionesAux);
            }

        }
    }
}
