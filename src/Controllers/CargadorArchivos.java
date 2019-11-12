package Controllers;

import Model.CSVreader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CargadorArchivos {
    private CSVreader reader= new CSVreader();

    void serializeObject( Object object){
        /*try {
            FileOutputStream fileOut = new FileOutputStream("plan.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(malla);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("estudiantes.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(estudiantes);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("aulas.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(aulas);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("grupos.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(grupos);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("inclusionesMap.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(inclusionesMap);
            out.close();
            fileOut.close();
            fileOut = new FileOutputStream("inclusiones.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(inclusiones);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        /*Map<String, Curso> malla;
        Map<Integer, Estudiante> estudiantes;
        Map<String, Aula> aulas;
        Map<String, Grupo> grupos;
        Map <Integer,ArrayList<Inclusion>> inclusionesMap;
        ArrayList<Inclusion> inclusiones;
        try {
            FileInputStream fileIn = new FileInputStream("plan.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            malla = (Map<String, Curso>) in.readObject();
            in.close();
            fileIn.close();
            fileIn = new FileInputStream("aulas.ser");
            in = new ObjectInputStream(fileIn);
            aulas = (Map<String, Aula>) in.readObject();
            in.close();
            fileIn.close();
            fileIn = new FileInputStream("estudiantes.ser");
            in = new ObjectInputStream(fileIn);
            estudiantes = (Map<Integer, Estudiante> ) in.readObject();
            in.close();
            fileIn.close();
            fileIn = new FileInputStream("grupos.ser");
            in = new ObjectInputStream(fileIn);
            grupos = (Map<String, Grupo>) in.readObject();
            in.close();
            fileIn.close();
            fileIn = new FileInputStream("inclusionesMap.ser");
            in = new ObjectInputStream(fileIn);
            inclusionesMap = (Map <Integer,ArrayList<Inclusion>> ) in.readObject();
            in.close();
            fileIn.close();
            fileIn = new FileInputStream("inclusiones.ser");
            in = new ObjectInputStream(fileIn);
            inclusiones = (ArrayList<Inclusion> ) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }*/
    }

}
