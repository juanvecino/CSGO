package src.reflection;

import java.awt.*;
import java.util.ArrayList;

/**
 * Comprobación de colisiones
 */
public class Enviroment {
    private ArrayList<Objeto> colisiones = new ArrayList<>();
    private Personaje personaje;

    public void setArea(ArrayList<Objeto> figuras) {
        colisiones = new ArrayList<>();
        for(Objeto object: figuras){
            if(object instanceof Personaje){
                this.personaje = (Personaje)object;
            }
            if(object.isObject()){
                colisiones.add(object);
            }
        }
    }


    public Objeto getColision(Rectangle r) {
        Objeto onFloor;
        for(Objeto obj: colisiones){
            if(obj != null){
                if(obj.getArea().intersects(r)){
                    //No es conatins buscar otra opcion
                    onFloor = obj;
                    return onFloor;
                }
            }
        }
        return null;
    }

    public Objeto getColisionPersonaje(Rectangle r){
        Objeto onFloor;
        if(personaje.getArea().intersects(r)){
                //No es conatins buscar otra opcion
                onFloor = personaje;
                return onFloor;
        }
        return null;
    }
}
