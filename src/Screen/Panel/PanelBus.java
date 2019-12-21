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
public class PanelBus extends PanelWithCicle {

    public PanelBus() {
        super(8); // Chama a classe pai com a quantidade circulos desejada
        
        setBorder(new EtchedBorder());
        setLayout(new MigLayout("w 280:280:280"));

        add(Util.createTitleLabel("Bus"), "span, growx, pushx, wrap");
        add(Util.createLabel("Value: "), "newline");

        for (Cicle signal : value) {
            add(signal, "pushx");
        }

        txtValue.setEditable(false);
        txtValue.setHorizontalAlignment(JLabel.RIGHT);

        add(txtValue, "skip, newline, growx, pushx, span");
    }
}
