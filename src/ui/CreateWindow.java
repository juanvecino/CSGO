package src.ui;

import src.sound.Sonido;

import javax.swing.*;
import java.awt.*;

/** Aqui es donde se va a crear la ventana
 */

public class CreateWindow extends JFrame {
    public static int WIDTH = 0;
    static Sonido sonido;
    static int DIFICULTAD = 1;
    public static void main(String[] args) {
        new CreateWindow();
    }

    public CreateWindow(){
        setTitle("CSGO");
         startSonido();
        //Create Menu
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu,BoxLayout.Y_AXIS));
        menu.setOpaque(false);
        //MENU
        JButton btnSingle = new JButton("SinglePlayer");
        JButton btnOptions = new JButton("Options");

        styleButton(btnSingle);

        styleButton(btnOptions);

        menu.add(btnSingle);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));

        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(btnOptions);

        //Listener
        btnSingle.addActionListener(e ->PlayervsComputer());

        btnOptions.addActionListener(e ->Options());


        //Icon Game
        final Taskbar taskbar = Taskbar.getTaskbar();
        ImageIcon gameIcon = new ImageIcon("res/icon/logo.png");
        try{

            taskbar.setIconImage(gameIcon.getImage());
        }
        catch(Exception e){
            System.out.println("The os does not support: 'taskbar.setIconImage'");
        }
        setIconImage(gameIcon.getImage());

        //Set Background
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon("res/Background/main.png"));
        add(background);
        background.setLayout(new BorderLayout());

        //Full Screen
        background.add(menu,BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        WIDTH = getWidth();
    }

    public static void startSonido() {
        sonido = new Sonido(Sonido.INICIO);
    }

    private void Options() {
        try{
            try{
                sonido.iniciostop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            new OptionsWindow();
        }
        catch(Exception e){

        }

    }

    private void exit() {
        setVisible(false);
        dispose();
    }



    private void PlayervsComputer() {
        try{
            try{sonido.iniciostop();} catch (Exception e) {
                e.printStackTrace();
            }
            new GameWindow();
            exit();
        }
        catch(Exception e){

        }
    }

    private static void styleButton(JButton btn) {
        btn.setAlignmentX(JButton.CENTER_ALIGNMENT);

        btn.setFocusable(false);

        btn.setPreferredSize(new Dimension(100,100));

        btn.setFont(new Font("Arial", Font.BOLD,20));

        btn.setMargin(new Insets(10, 10, 10, 10));

        btn.setBackground(new Color(0xBE565656));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setForeground(new Color(0xFFFFFF));

    }
}
