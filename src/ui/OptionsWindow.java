package src.ui;

import src.sound.Sonido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Ventana Opciones
 */
public class OptionsWindow extends JFrame{


    public OptionsWindow(){
        setLayout(new BorderLayout());

        // NORTE: Titulo
        JPanel panelN = new JPanel(new FlowLayout());
        JLabel lblTitle = new JLabel("OPCIONES");
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 22));
        panelN.add(lblTitle);
        add(panelN, BorderLayout.NORTH);

        //CENTRO

        JPanel panelC = new JPanel();
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.Y_AXIS));

        //Sonido
        JPanel pSonido = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pSonido.add(new JLabel("Sonido:"));
        JCheckBox sonido = new JCheckBox("",Sonido.getSonido());
        pSonido.add(sonido);
        panelC.add(pSonido);

        //Volver
        JPanel pacptar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton aceptar = new JButton("Aceptar");
        pacptar.add(aceptar);
        panelC.add(pacptar);

        aceptar.addActionListener(e->{
            dispose();
            CreateWindow.startSonido();
        });



        panelC.add(aceptar);

        sonido.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Sonido.setSonido(e.getStateChange() == 1);
            }
        });


        add(panelC, BorderLayout.CENTER);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
