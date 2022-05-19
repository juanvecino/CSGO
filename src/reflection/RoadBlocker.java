package src.reflection;

import java.awt.*;

/**
 * RoadBlocker tiene practicamente lo mismo que personaje
 */
public class RoadBlocker extends Objeto {
    static final int WIDTH = 80;
    static final int HEIGHT = 236;
    public RoadBlocker(int x, int y, int angulo){
        super(x, y, "res/objetos/roadblocker.png", WIDTH, HEIGHT, angulo);
    }


    @Override
    public void tick() {

    }

    @Override
    public void recalcular() {

    }

    @Override
    public String toString() {
        return "RoadBlocker en x:"+getX()+" y:"+getY();
    }
}
