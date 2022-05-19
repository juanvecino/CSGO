/** Todos los Objectos Bala,Personaje, RoadBlocker, Enviroment, Figuras...
 */
package src.reflection;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Bala extends Objeto{
    static final int WIDTH = 29;
    static final int HEIGHT = 13;
    static final int VEL_BALA = 20;
    private Image imageAux;
    Bala(int x, int y, double angulo){
        super(x, y, "res/objetos/bala.png", WIDTH, HEIGHT, angulo);
    }

    public void execute(){
        double changeX = Math.cos(Math.toRadians(this.getAngulo()));
        double changeY = Math.sin(Math.toRadians(this.getAngulo())) ;

        movement(changeX, changeY);
    }

    /** Nueva posicion de la bala
     * @param changeX - Cambio en el EjeX en funcion del angulo
     * @param changeY - Cambio en el EJeY en funcion del angulo
     */
    private void movement(double changeX, double changeY) {
        this.newPos((int) (this.getX()+ VEL_BALA*changeX),(int) (this.getY()+ VEL_BALA*changeY) );
        if(isCollision()){
            Objeto obj = this.getObjetoColisionado();
            if(obj !=null){
                obj.setEsobjeto(false, "res/objetos/soldado_muerto.png");
            }
            Personaje.balasEliminadas.add(this);
        }

    }

    private void addBalas(Bala bala){
        balas.add(bala);
    }

    @Override
    public void tick() {
        for(Bala bala: balas){
            bala.execute();
            if(bala.isCollision()){
                balas.remove(bala);
                Objeto obj = bala.getObjetoColisionado();
                if(obj != null){
                    obj.setEsobjeto(false, "res/objetos/soldado_muerto.png");
                }
            }
        }


    }

    @Override
    public void recalcular() {
        double changeX = Math.cos(Math.toRadians(this.getAngulo()));
        double changeY = Math.sin(Math.toRadians(this.getAngulo())) ;

        movement(changeX, changeY);
    }
    @Override
    public void pintar(Graphics g){
        Image image = this.getNormalImage();
        double rotationRequired = Math.toRadians (this.getAngulo());
        double locationX = getWidth() / 2;
        double locationY = getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter((BufferedImage) image, null), this.getX(), this.getY(), null);
    }


}
