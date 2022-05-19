package src.reflection;

import java.util.ArrayList;

/**
 * Guardamos las figuras actuales en el nivel
 */
public class Figuras {
    private ArrayList<Objeto> figuras;

    public Figuras() {
        figuras = new ArrayList<>();
    }

    public void reset(){
        figuras = new ArrayList<>();
    }

    public ArrayList<Objeto> getFiguras() {
        return figuras;
    }

    public void addFigura(Objeto figura) {
        figuras.add(figura);
    }

    public void addAllFiguras(ArrayList<Objeto> collision) {
        figuras.addAll(collision);
    }

    public void removeFigura(ArrayList<Objeto> collision) {
        figuras.removeAll(collision);
    }
}
