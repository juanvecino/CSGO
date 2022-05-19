/** Conseguimos informacion a tarves de los JSON
 */
package src.data;

import src.reflection.Objeto;
import src.reflection.Personaje;
import src.reflection.RoadBlocker;
import src.reflection.Soldado;

import java.util.ArrayList;
import java.util.List;

public class GestionMapas {
    public List<RoadBlocker> roadblocker;
    public List<Soldado> soldado;
    ArrayList<Objeto> figuras = new ArrayList<Objeto>();

    @Override
    public String toString() {
        return roadblocker.toString() + soldado.toString();
    }

    public ArrayList<Objeto> getObjetos() {
        roadblocker.forEach(objecto ->
        {figuras.add(new RoadBlocker(objecto.getX(),objecto.getY(),(int)objecto.getAngulo()));}
                );
        soldado.forEach(objecto ->
                {figuras.add(new Soldado(objecto.getX(),objecto.getY(),(int)objecto.getAngulo()));}
        );
        return  figuras;

    }
}
