package Screen.Panel;

import Model.PanelWithCicle;
import Screen.Cicle;
import Screen.Util;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author MrCapybara
 */
public class PanelALU extends PanelWithCicle {

    private final Cicle output = new Cicle();

    private final Cicle accZero = new Cicle();
    private final Cicle accOne = new Cicle();
    private final Cicle xorNot = new Cicle();
    private final Cicle addSub = new Cicle();

    private final JTextField txtOperation = Util.txt(JLabel.CENTER);
 
    public PanelALU() {
        super(8); // Chama a classe pai com a quantidade circulos desejada

        setBorder(new EtchedBorder());
        setLayout(new MigLayout("w 280:280:280"));

        add(Util.lblTitle("ALU"), "span, growx, pushx, wrap");
        add(Util.label("X/N"), "skip 5");
        add(Util.label("A/S"), "");
        add(Util.label("AC1"), "");
        add(Util.label("AC0"), "wrap");
        add(Util.label("State:"), "pushx, growx, right");
        add(txtOperation, "pushx, growx, span 3");
        add(xorNot, "skip");
        add(addSub);
        add(accOne);
        add(accZero, "wrap");

        add(Util.label("Value: "), "newline, right");

        for (Cicle signal : value) {
            add(signal, "pushx");
        }

        txtValue.setHorizontalAlignment(JLabel.RIGHT);
        add(txtValue, "skip, newline, growx, pushx, span, wrap");
        add(Util.label("Output: "), "split, span, right");
        add(output);
    }

    public void setAddSub(boolean value) {
        if (value)
            addSub.setGreen();
        else
            addSub.setGray();

        repaint();
    }

    public void setXorNot(boolean value) {
        if (value)
            xorNot.setGreen();
        else
            xorNot.setGray();

        repaint();
    }

    public void setAccZero(boolean value) {
        if (value)
            accZero.setGreen();
        else
            accZero.setGray();

        repaint();
    }

    public void setAccOne(boolean value) {
        if (value)
            accOne.setGreen();
        else
            accOne.setGray();

        repaint();
    }

    public void setOutput(boolean value) {
        if (value)
            output.setGreen();
        else
            output.setGray();

        repaint();
    }

    public void setOperation(String operation) {
        txtOperation.setText(operation.toUpperCase());
    }
    
    
}
