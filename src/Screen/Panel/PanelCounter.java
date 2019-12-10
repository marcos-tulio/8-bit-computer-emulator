package Screen.Panel;

import Model.PanelWithCicle;
import Screen.Cicle;
import Screen.Util;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author MrCapybara
 */
public class PanelCounter extends PanelWithCicle {

    private final Cicle increment = new Cicle();
    private final Cicle output = new Cicle();
    private final Cicle enable = new Cicle();
    private final Cicle jump = new Cicle();

    public PanelCounter() {
        super(4); // Chama a classe pai com a quantidade circulos desejada

        setBorder(new EtchedBorder());
        setLayout(new MigLayout("w 280:280:280"));

        add(Util.lblTitle("Program Counter"), "span, growx, pushx, wrap");
        add(Util.label("Enable: "), "right");
        add(enable, "w 100%, span");
        add(Util.label("Value: "), "newline, right");

        for (char i = 0; i < value.length; i++) {
            if (i == 1)
                add(value[i], "split " + (value.length - 1));
            else
                add(value[i]);
        }

        txtValue.setEditable(false);
        txtValue.setHorizontalAlignment(JLabel.RIGHT);

        add(txtValue, "w 100%,  span");
        add(Util.label("Inc: "), "newline, right");
        add(increment, "");
        add(Util.label("Jump: "), "right, span 2, pushx, split 2, center");
        add(jump);
        add(Util.label("Output: "), "right, split");
        add(output);
    }

    public void setIncrement(boolean value) {
        if (value)
            increment.setGreen();
        else
            increment.setGray();

        repaint();
    }

    public void setJump(boolean value) {
        if (value)
            jump.setGreen();
        else
            jump.setGray();

        repaint();
    }

    public void setOutput(boolean value) {
        if (value)
            output.setGreen();
        else
            output.setGray();

        repaint();
    }

    public void setEnable(boolean value) {
        if (value)
            enable.setGreen();
        else
            enable.setGray();

        repaint();
    }
}
