package src.reflection;

import java.awt.*;

public class Robot extends Objeto {
    static final int WIDTH = 192;
    static final int HEIGHT = 361;
    private Image imageAux;
    Robot(int x, int y, int angulo){

        super(x, y, "res/objetos/robot.png", WIDTH, HEIGHT, angulo);
    }



    @Override
    public void tick() {

    }

    @Override
    public void recalcular() {

    }
}
