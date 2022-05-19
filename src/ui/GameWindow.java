package src.ui;

//import src.reflection.Dibujo;
import src.reflection.Drawings;
import src.sound.Sonido;

import javax.swing.*;
import java.awt.*;


/**
 * Ventana Game Lllama a Drawings
 */
public class GameWindow extends JFrame{
    public static final int PLAYERVSPLAYER = 1;
    public static final int PLAYERVSCOMPUTER = 0;
    private int tipo = 0;
    //private Dibujo dibujo;
    private Drawings draw;
    Sonido sound;

    public GameWindow() {
        this(PLAYERVSCOMPUTER);
    }
    public GameWindow(int tipo){
        this.tipo = tipo;
        sound = new Sonido(Sonido.BATALLA);

        //dibujo = new Dibujo(this, tipo);
        //this.add(dibujo);
        draw = new Drawings(this);
        this.add(draw);

        setUndecorated(true);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        //dibujo.requestFocus();
        draw.requestFocus();
    }

    /**
     * Eliminar pantalla
     */
    public void quit() {
        setVisible(false);
        new CreateWindow();
        dispose();
    }

    public void Winner(){
        setVisible(false);
        new CreateWindow();
        dispose();
    }

    /**
     * Fin de la Partida
     */
    public void gameOver(){
        setVisible(false);
        sound.batallastop();
        dispose();
        new CreateWindow();
    }

    /**
     * Ganador de Partida
     */
    public void gameWinner() {
        setVisible(false);
        sound.batallastop();
        dispose();
        new CreateWindow();
        JOptionPane.showMessageDialog(null,"ENHORABUENA HAS COMPLETADO LOS NIVELES");

    }
}
