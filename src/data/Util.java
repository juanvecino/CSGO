package src.data;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import src.reflection.Objeto;

public class Util {

    public static ArrayList<Objeto> getJson(int i)
    {
        try {

            Gson gson = new Gson();
            String camino = "res/mapa"+Integer.toString(i)+".json";
            Reader reader = Files.newBufferedReader(Paths.get(camino));
            GestionMapas mapa = gson.fromJson(reader,GestionMapas.class);




            reader.close();
            return mapa.getObjetos();

        } catch (IOException e) {
            e.printStackTrace();

    }
        return null;
    }

}
