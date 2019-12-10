/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Screen.Cicle;
import Screen.Util;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Painel que contém modelo e funções para usar os círculos como saída.
 *
 * @author MrCapybara
 */
public class PanelWithCicle extends JPanel {

    protected final Cicle[] value;
    protected final JTextField txtValue = Util.txt();

    public PanelWithCicle(int cicleAmount) {
        value = new Cicle[cicleAmount];

        for (int i = 0; i < this.value.length; i++) {
            value[i] = new Cicle();
        }
    }

    public void setValue(byte value) {
        // Troca a cor co circulo de acordo o valor do byte
        for (int i = 0; i < this.value.length; i++) {
            if ((value & (0x01 << i)) > 0)
                this.value[(this.value.length - 1) - i].setGreen(); // Pos. complementar
            else
                this.value[(this.value.length - 1) - i].setGray();  // Pos. complementar 
        }
        
        txtValue.setText((value & 0xFF) + "");
        repaint();
    }
}
