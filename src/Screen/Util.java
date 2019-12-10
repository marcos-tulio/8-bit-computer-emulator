/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screen;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author MrCapybara
 */
public class Util {

    public static final Font FONT = new Font("Monospaced", Font.PLAIN, 12);

    public static JLabel lblTitle(String txt) {
        JLabel lbl = new JLabel(txt);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setFont(FONT.deriveFont(Font.BOLD));
        return lbl;
    }

    public static JLabel label(String txt) {
        JLabel lbl = new JLabel(txt);
        lbl.setFont(FONT);
        return lbl;
    }

    public static JTextField txt(int alignment) {
        JTextField txt = new JTextField();
        txt.setFont(FONT);
        txt.setEditable(false);
        txt.setHorizontalAlignment(alignment);
        return txt;
    }

    public static JTextField txt() {
        return txt(JLabel.RIGHT);
    }
    
    public static JTextField txt(String str) {
        JTextField textField = txt(JLabel.CENTER);
        textField.setText(str);        
        return textField;
    }
    
}
