package src.reflection;

import com.eduworks.ec.array.toString;
import src.sound.Sonido;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_LEFT;


/**
 * Personaje que va a utilizar el jugador
 */
public class Personaje extends Objeto implements MouseListener, KeyListener {
    static final int WIDTH = 200;
    static final int HEIGHT = 200;
    static final int LONG_PASO = 5;
    private int vida = 100;
    private boolean muerto = false;
    private boolean disparo = false;
    public static ArrayList<Bala> balasEliminadas;
    public double ultimabala;

    private final Map<Integer,Boolean> teclasDireccion = new HashMap<>(Map.of(
            KeyEvent.VK_UP,false,
            KeyEvent.VK_DOWN,false,
            KeyEvent.VK_LEFT,false,
            KeyEvent.VK_RIGHT,false,
            0,false));

    /** Constructor de Personaje
     * @param x - Posicion x
     * @param y - Posicion y
     * @param angulo - Angulo inicial
     */
    Personaje(int x, int y, int angulo){
        super(x, y, "res/objetos/survivor.png", WIDTH, HEIGHT, angulo);
    }

    /** Getter de vida
     * @return vida del jugador
     */
    public int getVida() {
        return vida;
    }

    public boolean isMuerto() {
        return muerto;
    }

    /** Cambiar la vida del Jugador
     * @param vida - Nueva vida
     */
    public void setVida(int vida) {
        if(vida <= 0){
            this.vida = 0;
            muerto = true;
        }
        else if(vida > 100){
            this.vida = this.vida;
        }
        else{
            this.vida = vida;
        }
    }


    @Override
    public void tick() {
        double i,v,deg;
        Point info = MouseInfo.getPointerInfo().getLocation();

        i = getX() - info.getX();
        v = getY() - info.getY();
        if(i ==0){
            deg = 270;
        }
        else{
            deg = Math.toDegrees(Math.atan(Math.abs(v/i)));

            if(i<0 && v>0){
                deg = 360 -deg;
            }
            else if(i>0 && v>0){
                deg = 180 + deg;
            }
            else if(i>0 && v<0){
                deg = 180 - deg;
            }
        }

        setAngulo(deg);
        getTeclasDireccion().forEach( (tecla, bool) ->{
            if(tecla == VK_UP && bool){
                desplazay(-LONG_PASO);
            }
            else if(tecla == VK_DOWN && bool){
                desplazay(LONG_PASO);
            }
            else if(tecla == VK_RIGHT && bool){
                desplazax(LONG_PASO);
            }
            else if(tecla == VK_LEFT && bool){
                desplazax(-LONG_PASO);
            }
        });


    }

    /**
     * Recalcular la poscion en la que esta en funcion si se esta moviendo a la derecha,izq,... Tambien hace la rotacion del objecto en funcion del cursor
     */
    @Override
    public void recalcular() {
        //Rotacion Persona
        Point info = MouseInfo.getPointerInfo().getLocation();
        double i,v,deg;
        i = this.getX()-info.getX();
        v = this.getY()-info.getY();
        if(i ==0){
            deg = 270;
        }
        else{
            deg = Math.toDegrees(Math.atan(Math.abs(v/i)));

            if(i<0 && v>0){
                deg = 360 -deg;
            }
            else if(i>0 && v>0){
                deg = 180 + deg;
            }
            else if(i>0 && v<0){
                deg = 180 - deg;
            }
        }
        this.setAngulo(deg);
        this.getTeclasDireccion().forEach( (tecla, bool) ->{
            if(tecla == VK_UP && bool){
                this.desplazay( -LONG_PASO);
            }
            else if(tecla == VK_DOWN && bool){
                this.desplazay(LONG_PASO);
            }
            else if(tecla == VK_RIGHT && bool){
                this.desplazax(  LONG_PASO);
            }
            else if(tecla == VK_LEFT && bool){
                this.desplazax(-LONG_PASO);
            }
            else if(tecla == 0 && bool) {
                if (System.currentTimeMillis() > ultimabala + 20) {
                    ultimabala = System.currentTimeMillis();
                    ArrayList<Integer> salida = salidaDisparo(getWidth(), getHeight());
                    balas.add(new Bala(getX() + salida.get(0), getY() + salida.get(1), getAngulo()));
                    new Sonido(Sonido.DISPARO);
                }
            }
            });

        //Recalcular las balas que hay
        balasEliminadas = new ArrayList<>();
        balas.forEach(bala-> bala.recalcular());
        balas.removeAll(balasEliminadas);
    }

    /** Calculo salida de la bala. En este caso tenemos que tener en cuanta las dimensiones del objecto y saber cual es el angulo del mismo. Una vez tenemos estos datos podemos calcular cual seria la salida de un objecto cuadrado.
     * @param width - Anchura del objecto
     * @param height - Altura del objecto
     * @return - Nos devuelve la coord. x y la coord. y de salida
     */
    public ArrayList<Integer> salidaDisparo(int width, int height) {
        // The rectangle coordinates for the upper-left corner
        int rectX = -29;
        int rectY = -29;

        // Rectangle's size
        int rectW = width+29;
        int rectH = height+29;

        //Centro de la Figura
        int rectCenterX = 125;
        int rectCenterY = 125;

        // The line's angle in degrees counter clock wise
        double lineAngle = getAngulo();

        lineAngle = 360 -lineAngle;

        // Transform the angle from degrees to radians
        lineAngle *= Math.PI / 180;

        // Calculate the rectangle center
        rectCenterX = rectX + (rectW / 2);
        rectCenterY = rectY + (rectH / 2);

        double rectDiagAngle = Math.atan2(rectCenterY - rectY, rectX + rectW - rectCenterX);
        int colX, colY;
        double oppositeLegLength, adjacentLegLength;
        // angle <= 180°
        if (lineAngle <= Math.PI) {
            // The collision is between the top and the right edge
            if (lineAngle < (Math.PI / 2.0)) {
                // The line collides with the right edge
                if (lineAngle < rectDiagAngle) {
                    // For this collision you have the x coordinate, is the same as the right edge x coordinate
                    colX = rectX + rectW;
                    // Now you need to find the y coordinate for the collision, to do that you just need the opposite leg
                    oppositeLegLength = Math.tan(lineAngle) * (rectW / 2);
                    colY = (int) (rectCenterY - oppositeLegLength);
                } else {
                    // The line collides with the top edge
                    //
                    // For this collision you have the y coordinate, is the same as the top edge y coordinate
                    colY = rectY;
                    // Now you need to find the x coordinate for the collision, to do that you just need the adjacent leg
                    adjacentLegLength = (rectH / 2) / Math.tan(lineAngle);
                    colX = (int) (rectCenterX + adjacentLegLength);
                }
            } else {
                // // The collision is between the top and the left edge
                //
                // The line collides with the top edge
                if (lineAngle < (Math.PI - rectDiagAngle)) {
                    // For this collision you have the y coordinate, is the same as the top edge y coordinate
                    colY = rectY;
                    adjacentLegLength = (rectH / 2) / Math.tan(Math.PI - lineAngle);
                    colX = (int) (rectCenterX - adjacentLegLength);
                } else {
                    // The line collides with the left edge
                    //
                    // For this collision you have the x coordinate, is the same as the left edge x coordinate
                    colX = rectX;
                    oppositeLegLength = Math.tan(Math.PI - lineAngle) * (rectW / 2);
                    colY = (int) (rectCenterY - oppositeLegLength);
                }
            }
        } else {
            // angle > 180°
            //
            // The collision is between the lower and the left edge
            if (lineAngle < (3.0 * Math.PI / 2.0)) {
                //  The line collides with the left edge
                if (lineAngle < (rectDiagAngle + Math.PI)) {
                    // For this collision you have the x coordinate, is the same as the left edge x coordinate
                    colX = rectX;
                    oppositeLegLength = Math.tan(lineAngle - Math.PI) * (rectW / 2);
                    colY = (int) (rectCenterY + oppositeLegLength);
                } else {
                    // The line collides with the lower edge
                    //
                    // For this collision you have the y coordinate, is the same as the lower edge y coordinate
                    colY = rectY + rectH;
                    // Now you need to find the x coordinate for the collision, to do that you just need the adjacent leg
                    adjacentLegLength = (rectH / 2) / Math.tan(lineAngle - Math.PI);
                    colX = (int) (rectCenterX - adjacentLegLength);
                }
            } else {
                // The collision is between the lower and the right edge
                //
                // The line collides with the lower edge
                if (lineAngle < (2.0 * Math.PI - rectDiagAngle)) {
                    // For this collision you have the y coordinate, is the same as the lower edge y coordinate
                    colY = rectY + rectH;
                    // Now you need to find the x coordinate for the collision, to do that you just need the adjacent leg
                    adjacentLegLength = (rectH / 2) / Math.tan(2.0 * Math.PI - lineAngle);
                    colX = (int) (rectCenterX + adjacentLegLength);
                } else {
                    // The line collides with the lower right
                    //
                    // For this collision you have the x coordinate, is the same as the right edge x coordinate
                    colX = rectX + rectW;
                    // Now you need to find the y coordinate for the collision, to do that you just need the opposite leg
                    oppositeLegLength = Math.tan(2.0 * Math.PI - lineAngle) * (rectW / 2);
                    colY = (int) (rectCenterY + oppositeLegLength);
                }
            }
        }
        if(colX == 0){
            colY += -29;
        }
        else if(colY == 0){
            colX += -29;
        }
        ArrayList<Integer> salida = new ArrayList<>();
        salida.add(colX);
        salida.add(colY);
        return salida;
    }

    public Map<Integer, Boolean> getTeclasDireccion() {
        return teclasDireccion;
    }

    /** Cambio del HashMap indicando que tecla se ha pulsado
     * @param num  - tecla pulsada
     * @param bool - true(pulsada)/false(no pulsada)
     */
    public void putTeclasDireccion(int num,boolean bool) {
        teclasDireccion.put(num,bool);
    }

    public void desplazax(int x){
        super.setX(super.getX() +x);
    }
    public void desplazay(int y){
        super.setY(super.getY() + y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        disparo = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (getTeclasDireccion().containsKey(e.getKeyCode())) {
            putTeclasDireccion(e.getKeyCode(),true);
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        if (getTeclasDireccion().containsKey(e.getKeyCode())) {
            putTeclasDireccion(e.getKeyCode(),false);
        }
    }

    /** Utilizado para pintar el obejcto
     * @param g - Graphics
     */
    @Override
    public void pintar(Graphics g){
        Image image = this.getNormalImage();
        double rotationRequired = Math.toRadians (this.getAngulo());
        double locationX = getWidth()/2;
        double locationY = getHeight()/2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        // Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter((BufferedImage) image, null), this.getX(), this.getY(), null);
        for(int i = 0; i<balas.size();i++){
            balas.get(i).pintar(g);
        }
    }
    @Override
    public String toString(){
        return "Soy un personaje:\nx: "+getX()+"\t, y: "+getY();
    }
}
