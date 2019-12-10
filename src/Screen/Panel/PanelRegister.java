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
public class PanelRegister extends PanelWithCicle {

    private final Cicle input = new Cicle();
    private final Cicle output = new Cicle();

    public PanelRegister(String txt) {
        super(8); // Chama a classe pai com a quantidade circulos desejada
        
        setBorder(new EtchedBorder());
        setLayout(new MigLayout("w 280:280:280"));

        add(Util.lblTitle(txt), "span, growx, pushx, wrap");
        
        add(Util.label("Value: "));
        for (Cicle signal : value) {
            add(signal, "pushx");
        }

        txtValue.setEditable(false);
        txtValue.setHorizontalAlignment(JLabel.RIGHT);

        add(txtValue, "skip, newline, growx, pushx, span");
        add(Util.label("Input: "), "newline");
        add(input);
        add(Util.label("Output: "), "split, span, right");
        add(output);
    }

    public void setInput(boolean value) {
        if (value)
            input.setGreen();
        else
            input.setGray();

        repaint();
    }

    public void setOutput(boolean value) {
        if (value)
            output.setGreen();
        else
            output.setGray();

        repaint();
    }
}
