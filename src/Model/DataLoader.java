package Model;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.*;

public class DataLoader {
    private ExcelReader excelreader = new ExcelReader();
    private DataHolder dataHolder= DataHolder.getInstance();


    public void addEstudiantes(String filepathStudents) throws IOException {
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
                estudiante.setPlan(sheetName.toLowerCase());
                Map<String, String> cursos = estudiante.getCursos();
                for (int j = 2; j < row.size(); j++) {
                    cursos.put(columns.get(j), row.get(j));
                }
                estudiantes.put(carnet, estudiante);
            }
            catch (Exception e){

            }
        }
    }

    public void addRN(String filepath) throws IOException {
        ArrayList<ArrayList<String>> data= excelreader.readCsvFiles(filepath);
        Map<Integer, Estudiante> estudiantes= DataHolder.getInstance().getEstudiantes();
        int carnet=-1;
        data.remove(0);
        Estudiante estudiante = null;
        for(ArrayList<String> row: data){
            try{
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
            catch (Exception e){

            }
        }
    }

    public void addAulas(String filepath) throws IOException {
        Map<String, ArrayList<ArrayList<String>>> data= excelreader.readXlsxFile(filepath);
        Map<String, Aula> aulas = dataHolder.getAulas();
        for(String sheetName: data.keySet()){
            int linea=0;
            ArrayList<ArrayList<String>> sheet = data.get(sheetName);
            for(ArrayList<String> row:sheet){
                linea++;
                try{
                    if(row.get(1).split("-").length<2){
                        continue;
                    }
                    if(row.get(3)==""){
                        row.set(3,"0");
                    }
                    while(row.size()<8){
                        row.add(" ");
                    }
                    Aula aula= new Aula(row.get(1),row.get(2),Double.valueOf(row.get(3)).intValue(),row.get(4),row.get(5),row.get(6),row.get(7));
                    aulas.put(row.get(1),aula);
                }
                catch(Exception e){
                    dataHolder.addError("Error creando el aula "+ row.get(1)+" en la línea "+ String.valueOf(linea)+" de la hoja "+ sheetName);
                }

            }
        }
    }


    private void addHorario(ArrayList<ArrayList<String>> sheet,String sheetName){
        Map<String, Grupo> grupos = dataHolder.getGrupos();
        Map<String, Aula> aulas = dataHolder.getAulas();
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
                System.out.println("Se detecto estudiante inválido");
                continue;
            }
            System.out.println(dataRow.get(3));
            String idGrupo="GR"+String.valueOf(Double.valueOf(dataRow.get(3)).intValue())+dataRow.get(1);
            Grupo grupo=grupos.get(idGrupo);
            if(grupo==null){
                Curso curso= plan.get(dataRow.get(1));
                if (curso==null){
                    curso= new Curso(dataRow.get(1),dataRow.get(2),-1,-1);
                    plan.put(dataRow.get(1),curso);
                }
                grupo= new Grupo(Double.valueOf(dataRow.get(3)).intValue(),dataRow.get(4),curso);
                grupos.put(idGrupo,grupo);
            }
            LocalTime horaInicio= LocalTime.ofSecondOfDay(Double.valueOf(86401*Double.valueOf(dataRow.get(7))).longValue());
            LocalTime horaFinal= LocalTime.ofSecondOfDay(Double.valueOf(86401*Double.valueOf(dataRow.get(8))).longValue());
            Aula aula= aulas.get(dataRow.get(6));
            if(aula==null){
                aula=new Aula(dataRow.get(6),"N/A",-1,"N/A","N/A","N/A","N/A");
                dataHolder.addError("Aula código: "+dataRow.get(6)+" no se encontro en la lista de aulas, creando perfil nuevo");
                aulas.put(dataRow.get(6),aula);
            }
            Horario horario= new Horario(aula,dataRow.get(5),horaInicio,horaFinal);
            if(grupo.notContainsHorario(horario.getDia())){
                grupo.addHorario(horario);
            }
            if(estudiante!=null){
                gruposEstudiante = estudiante.getGrupos();
                if(!gruposEstudiante.containsKey(idGrupo)){
                    gruposEstudiante.put(idGrupo,grupo);
                    grupo.aumentarEstudiantes();
                }

            }
        }
    }

    public void addPlanes(String filepath) throws IOException {
        Map<String, ArrayList<ArrayList<String>>> data = excelreader.readXlsxFile(filepath);
        for(String sheetName: data.keySet()){
            ArrayList<ArrayList<String>> sheet = data.get(sheetName);
            Curso actual;
            Map<String, Curso> result = new HashMap<String, Curso>();
            sheet.remove(0);
            int linea=1;
            for(ArrayList<String> row :sheet){
                linea++;
                try {
                    String codigo = row.get(0);
                    actual = new Curso(codigo, row.get(1), Double.valueOf(row.get(2)).intValue(), Double.valueOf(row.get(5)).intValue());
                    result.put(codigo, actual);
                }
                catch (Exception e){
                    dataHolder.addError("Curso en la línea: "+linea+" hoja: "+sheetName +" del archivo de planes, presenta errores");
                }
            }
            linea=1;
            for(ArrayList<String> row :sheet){
                try {
                    linea++;
                    String codigo = row.get(0);
                    String[] requisitos = row.get(3).split(",");
                    String[] corequisitos = row.get(4).split(",");
                    for (String requisito : requisitos) {
                        if (result.containsKey(requisito)) {
                            Curso requisitoActual = result.get(requisito);
                            actual = result.get(codigo);
                            actual.addRequisito(requisitoActual);
                        }
                    }
                    for (String corequisito : corequisitos) {
                        if (result.containsKey(corequisito)) {
                            Curso corequisitoActual = result.get(corequisito);
                            actual = result.get(codigo);
                            actual.addCorequisito(corequisitoActual);
                        }
                    }
                }
                catch (Exception e){

                }
            }
            dataHolder.getMalla().put(sheetName.toLowerCase(),result);
        }

    }

    public void addInclusionesExistentes() throws IOException {
        ArrayList<ArrayList<String>> data= excelreader.readCsvFiles("inclusionesResult.csv");
        Map<Integer, Estudiante> estudiantes = dataHolder.getEstudiantes();
        Map<String, Grupo> grupos = dataHolder.getGrupos();
        Map<Integer, ArrayList<Inclusion>> inclusionesEstudianteMap = dataHolder.getInclusionesMapPorEstudiante();
        Map<String, ArrayList<Inclusion>> inclusionesMateriaMap = dataHolder.getInclusionesMapPorMateria();
        data.remove(0);
        ArrayList<Inclusion>  result = dataHolder.getInclusiones();
        int linea=1;
        Estudiante estudiante=null;
        for(ArrayList<String> row :data){
            linea++;
            try{
                estudiante= estudiantes.get(Integer.valueOf(row.get(1)));
            }
            catch (Exception e){
                dataHolder.addError("Inclusión en la línea "+Integer.toString(linea)+" no tiene un carnet válido");
                continue;
            }
            if(estudiante== null){
                try
                {
                    int carnet=Integer.parseInt(row.get(1));
                    estudiante= new Estudiante(carnet,row.get(2),"N/A");
                    estudiantes.put(carnet,estudiante);
                    dataHolder.addError("Estudiante con carnet "+ Integer.toString(carnet)+", en la línea "+Integer.toString(linea)+" no se encontro, se creo nuevo perfil");
                }
                catch (NumberFormatException e)
                {
                    dataHolder.addError("Inclusión en la línea "+Integer.toString(linea)+" no tiene un carnet válido");
                    continue;
                }
            }
            String[] datosCurso=row.get(3).split(" - ");
            Grupo grupo = grupos.get(datosCurso[1]+datosCurso[0]);

            if(grupo== null){
                System.out.println(datosCurso[1]+datosCurso[0]+"Aqui esta el problema1");
                try
                {
                    int numGrupo=Integer.parseInt(datosCurso[1].replace("GR",""));
                    Curso curso=dataHolder.getCursoInPlanes(datosCurso[0]);
                    if(curso==null){
                        System.out.println("llegue a curso");
                        curso= new Curso(datosCurso[0],datosCurso[2],-1,-1);
                        dataHolder.getMalla().get("N/A").put(datosCurso[0],curso);
                        dataHolder.addError("Curso código "+ datosCurso[0]+" no se encontro, se creo nuevo curso en plan N/A");
                    }
                    System.out.println("llegue a grupo");
                    grupo = new Grupo(numGrupo,"No disponible",curso);
                    System.out.println("llegue a anadir grupo");
                    grupos.put(datosCurso[1]+datosCurso[0],grupo);
                    dataHolder.addError("Grupo código "+ datosCurso[1]+" - "+datosCurso[0]+" no se encontro, se creo nuevo grupo");
                }
                catch (Exception e)
                {
                    dataHolder.addError("Inclusión en la línea "+Integer.toString(linea)+" no tiene un grupo válido");
                    continue;
                }
            }
            boolean planB = (row.get(4).equals("Si"));
            Inclusion nuevaInclusion= new Inclusion(planB, grupo,estudiante,row.get(5),row.get(0));
            nuevaInclusion.setEstado(EstadoInclusion.valueOf(row.get(6)));
            nuevaInclusion.setComentarioAdmin(row.get(7));
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


    public void addInclusionesNuevas(String filepath ) throws IOException {
        ArrayList<ArrayList<String>> data= excelreader.readCsvFiles(filepath);
        Map<Integer, Estudiante> estudiantes = dataHolder.getEstudiantes();
        Map<String, Grupo> grupos = dataHolder.getGrupos();
        Map<Integer, ArrayList<Inclusion>> inclusionesEstudianteMap = dataHolder.getInclusionesMapPorEstudiante();
        Map<String, ArrayList<Inclusion>> inclusionesMateriaMap = dataHolder.getInclusionesMapPorMateria();
        data.remove(0);
        ArrayList<Inclusion>  result = dataHolder.getInclusiones();
        int linea=1;
        Estudiante estudiante=null;
        for(ArrayList<String> row :data){
            linea++;
            try{
                try{
                    estudiante= estudiantes.get(Integer.valueOf(row.get(2)));
                }
                catch (Exception e){
                    dataHolder.addError("Inclusión en la línea "+Integer.toString(linea)+" no tiene un carnet válido");
                    continue;
                }
                if(estudiante== null){
                    try
                    {
                        int carnet=Integer.parseInt(row.get(2));
                        estudiante= new Estudiante(carnet,row.get(3),"N/A");
                        estudiantes.put(carnet,estudiante);
                        dataHolder.addError("Estudiante con carnet "+ Integer.toString(carnet)+", en la línea "+Integer.toString(linea)+" no se encontro, se creo nuevo perfil");
                    }
                    catch (NumberFormatException e)
                    {
                        dataHolder.addError("Inclusión en la línea "+Integer.toString(linea)+" no tiene un carnet válido");
                        continue;
                    }
                }
                String[] datosCurso=row.get(5).split("-");
                String idCurso=datosCurso[0].replace(" ","");
                String numGrupoString= datosCurso[2].replace(" ","");
                Grupo grupo = grupos.get(numGrupoString+idCurso);
                if(grupo== null){
                    try
                    {
                        int numGrupo=Integer.parseInt(numGrupoString.replace("GR",""));
                        Curso curso=dataHolder.getCursoInPlanes(idCurso);
                        if(curso==null){
                            curso= new Curso(idCurso,datosCurso[1],-1,-1);
                            dataHolder.getMalla().get("N/A").put(idCurso,curso);
                            dataHolder.addError("Curso código "+ idCurso+" no se encontro, se creo nuevo curso en plan N/A");
                        }
                        grupo = new Grupo(numGrupo,"No disponible",curso);
                        grupos.put(numGrupoString+idCurso,grupo);
                        dataHolder.addError("Grupo código "+ numGrupoString+" - "+idCurso+" no se encontro, se creo nuevo grupo");

                    }
                    catch (Exception e)
                    {
                        dataHolder.addError("Inclusión en la línea "+Integer.toString(linea)+" no tiene un grupo válido");
                        continue;
                    }
                }
                boolean planB = (!row.get(6).equals("No"));
                Inclusion nuevaInclusion= new Inclusion(planB, grupo,estudiante,row.get(7),row.get(1));
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
                if(inclusionesMateriaMap.containsKey(idCurso)){
                    inclusionesAux=inclusionesMateriaMap.get(idCurso);
                    inclusionesAux.add(nuevaInclusion);
                }
                else{
                    inclusionesAux= new ArrayList<>();
                    inclusionesAux.add(nuevaInclusion);
                    inclusionesMateriaMap.put(idCurso,inclusionesAux);
                }
            }
            catch (Exception e){
                dataHolder.addError("Inclusión en la línea "+Integer.toString(linea)+" tiene errores");
            }
        }
    }
}
