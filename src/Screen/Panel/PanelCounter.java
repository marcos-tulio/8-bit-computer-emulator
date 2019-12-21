package Screen.Panel;

import Model.PanelWithCicle;
import Screen.Cicle;
import Model.Util;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author MrCapybara
 */
public final class PanelCounter extends PanelWithCicle {

    private final Cicle jump = new Cicle();
    private final Cicle enable = new Cicle();
    private final Cicle output = new Cicle();
    private final Cicle increment = new Cicle();

    public PanelCounter() {
        super(4); // Chama a classe pai com a quantidade circulos desejada

        setBorder(new EtchedBorder());
        setLayout(new MigLayout("w 280:280:280"));

        add(Util.createTitleLabel("Program Counter"), "span, growx, pushx, wrap");
        add(Util.createLabel("Enable: "), "right");
        add(enable, "w 100%, span");
        add(Util.createLabel("Value: "), "newline, right");

        for (char i = 0; i < value.length; i++) {
            if (i == 1)
                add(value[i], "split " + (value.length - 1));
            else
                add(value[i]);
        }

        txtValue.setEditable(false);
        txtValue.setHorizontalAlignment(JLabel.RIGHT);

        add(txtValue, "w 100%,  span");
        add(Util.createLabel("Inc: "), "newline, right");
        add(increment, "");
        add(Util.createLabel("Jump: "), "right, span 2, pushx, split 2, center");
        add(jump);
        add(Util.createLabel("Output: "), "right, split");
        add(output);
    }

    public void setIncrement(boolean value) {
        changeColor(value, increment);
    }

    public void setJump(boolean value) {
        changeColor(value, jump);
    }

    public void setOutput(boolean value) {
        changeColor(value, output);
    }

    public void setEnable(boolean value) {
        changeColor(value, enable);
    }
}
