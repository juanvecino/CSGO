package src.sound;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sonido {

    public static final int INICIO = 1;
    public static final int BATALLA = 2;
    public static final int DISPARO = 3;
    public static final int ENEMIGOS = 4;
    public static boolean sonido = true;
    Clip inicio;
    Clip batalla;
    Clip shoot;
    Clip enemigos;
    public Sonido(int tipo) {
       if(sonido){
           if(tipo == INICIO) {
               incioSound();
           }
           else if(tipo == BATALLA){
               batallaSound();
           }
           else if(tipo == DISPARO){
               disparaoSound();
           }
           else{
               enemigoSound();
           }
       }
    }

    public static void setSonido(boolean b) {
        sonido = b;
    }

    public static boolean getSonido() {
        return sonido;
    }

    private void disparaoSound() {
        try{
            File file = new File("/Users/juanvecino/Desktop/Game CSGO/CSGO/res/audio/shoot.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            shoot = AudioSystem.getClip();
            shoot.open(sound);
            shoot.setFramePosition(0);
            shoot.start();
        }
        catch(Exception ex)
        {
        }
    }

    private void enemigoSound() {
        try{
            File file = new File("/Users/juanvecino/Desktop/Game CSGO/CSGO/res/audio/enemigos.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            enemigos = AudioSystem.getClip();
            enemigos.open(sound);
            enemigos.setFramePosition(0);
            enemigos.start();
        }
        catch(Exception ex)
        {
        }
    }

    private void batallaSound() {
        try{
            File file = new File("/Users/juanvecino/Desktop/Game CSGO/CSGO/res/audio/battle.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            batalla = AudioSystem.getClip();
            batalla.open(sound);
            batalla.setFramePosition(0);
            batalla.start();
        }
        catch(Exception ex)
        {
        }
    }

    private void incioSound() {
        try{
            File file = new File("/Users/juanvecino/Desktop/Game CSGO/CSGO/res/audio/csgo.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            inicio = AudioSystem.getClip();
            inicio.open(sound);
            inicio.setFramePosition(0);
            inicio.start();
        }
        catch(Exception ex)
        {
        }
    }

    public void iniciostop() {
        try{if(inicio != null)
            inicio.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void batallastop() {
        try{
            if(batalla != null)
            batalla.stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
