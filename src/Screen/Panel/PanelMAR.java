package Screen.Panel;

import Model.PanelWithCicle;
import Screen.Cicle;
import Model.Util;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 * JPanel da memória RAM.
 *
 *
 * @author MrCapybara
 */
public final class PanelMAR extends PanelWithCicle {

    private final Cicle input = new Cicle();

    public PanelMAR() {
        super(4);

        setBorder(new EtchedBorder());
        setLayout(new MigLayout("w 280:280:280"));

        add(Util.createTitleLabel("MAR"), "span, growx, pushx, wrap");
        add(Util.createLabel("Value: "), "newline, right");

        for (char i = 0; i < value.length; i++) {
            if (i == 1)
                add(value[i], "split " + (value.length - 1));
            else
                add(value[i]);
        }

        add(txtValue, "w 100%,  span");
        add(Util.createLabel("Input: "), "newline, right");
        add(input, "");
    }

    public void setInput(boolean value) {
        if (value)
            input.setGreen();
        else
            input.setGray();
    }
}
