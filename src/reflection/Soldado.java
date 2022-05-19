package src.reflection;

import src.sound.Sonido;

import java.awt.*;

/**
 * Enmigo tiene practicamente los mismo que Personaje
 */
public class Soldado extends Objeto {
    static final int WIDTH = 200;
    static final int HEIGHT = 200;
    private double ultima = 0;

    public Soldado(int x, int y, int angulo){

        super(x, y, "res/objetos/soldado.png", WIDTH, HEIGHT, angulo);
    }

    @Override
    public void tick() {
    }
    @Override
    public void recalcular() {
        if(System.currentTimeMillis() > ultima+ 1000 && isObject()){
            ultima = System.currentTimeMillis();

            int salidaX= getX();
            int salidaY =getY();

            double changeX = Math.cos(Math.toRadians(getAngulo()+270));
            double changeY = Math.sin(Math.toRadians(getAngulo()+270));
            while((getArea().intersects(new Bala(salidaX,salidaY,getAngulo()+270).getArea()))){
                salidaX += changeX;
                salidaY += changeY;
            }
            balas.add(new Bala(salidaX,salidaY,getAngulo()+270));
            new Sonido(Sonido.ENEMIGOS);
        }
    }
}
