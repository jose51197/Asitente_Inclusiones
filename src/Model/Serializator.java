package Model;

import java.io.*;

public class Serializator {

    public void serializeObject( Object object, String name) throws IOException {
       /* FileOutputStream fileOut = new FileOutputStream(name+".ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();*/
    }


    public Object deserializeObject( String name) throws IOException, ClassNotFoundException {
        Object object = null;
        FileInputStream fileIn = new FileInputStream(name+".ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        object = (Object) in.readObject();
        in.close();
        fileIn.close();
        return object;
    }
}
