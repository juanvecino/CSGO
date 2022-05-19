package src.reflection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Objeto {
    /**
     * Posicion Ejex
     */
    private int x;
    /**
     * Posicion Ejey
     */
    private int y;
    /**
     * Url del obecjto
     */
    private String url;
    private final int width;
    private boolean esobjeto = true;
    public void setUrl(String url) {
        this.url = url;
    }

    private final int height;
    private double angulo;
    private boolean collision;
    private Objeto objetoColisionado;
    private Personaje user;

    public static ArrayList<Bala> balas = new ArrayList<>();


    /** Construcctor creador de Objetso todos heredan de el
     * @param x - Eje x
     * @param y - Eje y
     * @param url - Url de la imagen del obejcto
     * @param width- Anchura objecto
     * @param height - Altura objecto
     * @param angulo - Angulo del objecto
     */
    public Objeto(int x, int y,String url, int width, int height, double angulo) {
        this.x = x;
        this.y = y;
        this.url = url;
        this.width = width;
        this.height = height;
        this.angulo = angulo;
        if (this instanceof Personaje){
            this.user = (Personaje) this;
        }
        if(this instanceof Personaje || this instanceof Bala){
            this.esobjeto =false;
        }
    }

    public void setEsobjeto(boolean esobjeto, String url) {
        this.esobjeto = esobjeto;
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }

    public double getAngulo() {
        return angulo;
    }

    public void pintar(Graphics g){
        Image image = this.getNormalImage();
        double rotationRequired = Math.toRadians (this.getAngulo());
        double locationX = this.getWidth() / 2;
        double locationY = this.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        // Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter((BufferedImage) image, null), this.getX(), this.getY(), null);
    }

    /**
     * Uso para comprobaciones de movimiento
     */
    public abstract void tick();
    public  String getUrl(){
        return url;
    }

    /** getNormalImage()
     * @return Returnea la imagen en base al url
     */
    public Image getNormalImage() {
        try {
            return  ImageIO.read(new File(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public BufferedImage getImage(){
        try {
            BufferedImage img = ImageIO.read(new File(url));
           return rotateImageByDegrees(img,angulo);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Objeto getObjetoColisionado() {
        return objetoColisionado;
    }

    /** Rotacion de la imagen
     * @param img - Imagen inicial
     * @param angle - Angulo que necesitamos rotar
     * @return Nos devuelve la imagen final
     */
    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        int x = w / 2;
        int y = h / 2;
        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage( img, 0, 0, null);

        return rotated;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        Objeto collision = Drawings.getEnvironment().getColision(new Rectangle(x, this.getY(),this.getWidth(), this.getWidth()));
        if(!(collision == null) || (x < 0 )){
            makeColision(collision);
        }
        else{
            this.x = x;
        }
    }

    /** Colision con un objecto. Va a hacer diferentes funciones en funcion del tipo
     * @param objeto - Objecto con el que ha colisionado
     */
    private void makeColision(Objeto objeto) {
        if(this instanceof Bala && (objeto instanceof Soldado || objeto instanceof Robot)){
            this.istHit(objeto);
            this.setCollision(true);
        }
        else if(this instanceof Bala && (objeto instanceof Personaje)){
            this.setCollision(true);
            Personaje user = (Personaje) objeto;
            user.setVida(user.getVida() - 50);
        }
        else {
            this.setCollision(true);
        }
    }

    private void istHit(Objeto objeto) {
        this.objetoColisionado = objeto;
    }

    public boolean isCollision() {
        return collision;
    }

    private void setCollision(boolean b) {
        this.collision = b;
    }

    /** getArea()
     * @return Area de colision
     */
    public Rectangle getArea() {
        return new Rectangle(getX(),getY(),getWidth(),getHeight());
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        Objeto collision = Drawings.getEnvironment().getColision(new Rectangle(getX(),y,getWidth(),getWidth()));
        if(!(collision == null) || Drawings.WindowDimension().getHeight() - getImage().getHeight() < y || y < 0 ){
            makeColision(collision);
        }
        else{
            this.y = y;
        }
    }

    public boolean isObject() {
        return esobjeto;
    }

    /** Nueva posicion del personaje
     * @param x - Ejex
     * @param y - Ejey
     */
    public void newPos(int x, int y) {
        Objeto collision = Drawings.getEnvironment().getColision(new Rectangle(x, y, getWidth(), getWidth()));
        Objeto per = Drawings.getEnvironment().getColisionPersonaje(new Rectangle(x, y, getWidth(), getWidth()));
        if( per != null){
            makeColision(per);
        }
        if(!(collision == null) || Drawings.WindowDimension().getHeight() - getImage().getHeight() < y || y < 0 ){
            makeColision(collision);
        }
        else{
            this.y = y;
            this.x = x;
        }
    }

    public abstract  void recalcular();
}
