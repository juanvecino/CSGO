package src.reflection;

import src.data.Util;
import src.ui.CreateWindow;
import src.ui.GameWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Aqui es donde se dibuja todo
 */
public class Drawings extends JPanel implements Runnable {

    private BufferedImage imageAux,imggameover,imggamewinner,health;
    public static int FPS = 60;
    public Util generadorMapas;
    Figuras figuras = new Figuras();
    private boolean active = false;
    private static GameWindow gameWindow;
    private Personaje personaje;
    private int Level = 1;
    private boolean gameover,gamewinner;

    static Enviroment environment = new Enviroment();

    /** Aqui es donde vamos a hacer toda la parte visual
     * @param gameWindow - Pantalla Padre
     */
    public Drawings(GameWindow gameWindow) {
        super();
        generadorMapas = new Util();

        //FONDO
        //Prefiero que se espere a que este todo cargado
        try {
            imageAux = ImageIO.read(new File("res/Background/mapa.png"));
            imggameover = ImageIO.read(new File("res/Background/game_over.png"));
            imggamewinner = ImageIO.read(new File("res/Background/victory.png"));
            health = ImageIO.read(new File("res/objetos/vida.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();
        Drawings.gameWindow = gameWindow;
        this.setVisible(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    gameWindow.quit();
                }
                else if (personaje.getTeclasDireccion().containsKey(e.getKeyCode())) {
                    personaje.putTeclasDireccion(e.getKeyCode(),true);
                }
            }
            @Override
            public void keyReleased(KeyEvent e){
                if (personaje.getTeclasDireccion().containsKey(e.getKeyCode())) {
                    personaje.putTeclasDireccion(e.getKeyCode(),false);
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                personaje.putTeclasDireccion(0,true);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                personaje.putTeclasDireccion(0,false);
            }

        });
    }

    static Dimension WindowDimension() {
        return gameWindow.getContentPane().getSize();
    }

    static Enviroment getEnvironment() {
        return environment;
    }

    /** Esto se va a ejecutar al crear la clase
     */
    public void init(){
        setMapa();
        active = true;
        new Thread(this).start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //FONDO
        g.drawImage(imageAux, 0, 0, null);
        //GAME_OVER
        if(gameover){
            g.drawImage(imggameover, gameWindow.getWidth()/2-400, gameWindow.getHeight()/2-410, null);
        }
        if(gamewinner){
            g.drawImage(imggamewinner, gameWindow.getWidth()/2-600, gameWindow.getHeight()/2-410, null);
        }
        ArrayList<Objeto> figurasDibujo = figuras.getFiguras();
        for (int i = 0; i<figurasDibujo.size(); i++){
            figurasDibujo.get(i).pintar(g);
        }

        g.drawImage(health,50,gameWindow.getHeight()-100,null);
        g.setColor(Color.RED);
        g.fillRect(100,gameWindow.getHeight()-100,personaje.getVida()*5,50);

        //Mejoras de Rendimiento
        g.dispose();
        Toolkit.getDefaultToolkit().sync();

    }

    @Override
    public void run() {
        double refreshRate = (double) 1000000000 / FPS;
        double nextPaint = System.nanoTime() + refreshRate;

        int countFpsPainted = 0;
        int secCounter = (int) (System.nanoTime()/1000000000);

        while(active) {
            reComponents();
            repaint();
            double remainingTime = nextPaint - System.nanoTime();
            if(remainingTime<0)
                remainingTime = 0;
            try {
                Thread.sleep((long) remainingTime/1000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            nextPaint += refreshRate;
            countFpsPainted+=1;
            if((int) (System.nanoTime()/1000000000) != secCounter){
                System.out.println("FPS:" + countFpsPainted);
                countFpsPainted = 0;
                secCounter = (int) (System.nanoTime()/1000000000);
            }

        }
    }

    /** Recalcular todos los objetos
     */
    private void reComponents() {
        Thread pantallafin = new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(gameover){
                gameWindow.gameOver();
            }
            if(gamewinner){
                gameWindow.gameWinner();

            }
        });

        environment.setArea(figuras.getFiguras());
        figuras.getFiguras().forEach(e-> e.recalcular());

        if(personaje.getVida() <= 0) {
            figuras.reset();
            gameover = true;
            active = false;
            pantallafin.start();
        }
        if(personaje.getX() > CreateWindow.WIDTH){
            active = false;
            if(Level < 2 ){
                Level++;
                init();
            }
            else{
                gamewinner = true;
                pantallafin.start();
            }

        }
    }

    /** LLama a generadorMapas.getJson() que nos devuelve un ArrayList con las figuras
     */
    private void setMapa() {
        figuras.reset();
        figuras.addAllFiguras(generadorMapas.getJson(Level-1));
        personaje = new Personaje(200,470,0);
        figuras.addFigura(personaje);
    }
}

