package Screen.Panel;

import Model.PanelWithCicle;
import Screen.Cicle;
import Model.Util;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 * JPanel da memória RAM.
 *
 *
 * @author MrCapybara
 */
public final class PanelRAM extends PanelWithCicle {

    private final Cicle input = new Cicle();
    private final Cicle output = new Cicle();
    private final Cicle wr = new Cicle();
    private final Cicle[] content;

    public PanelRAM() {
        super(8);
        content = new Cicle[16 * 8];

        for (int i = 0; i < this.content.length; i++) {
            content[i] = new Cicle();
        }

        setBorder(new EtchedBorder());
        setLayout(new MigLayout("w 280:280:280"));

        // MAP
        JPanel panelContent = new JPanel(new MigLayout());
        for (char i = 0; i < content.length; i++) {
            if (i % 8 == 7) {

                panelContent.add(content[i], "split, wrap");
                if (i < content.length - 1)
                    panelContent.add(Util.createTextField("0x0" + Integer.toHexString((i / 8) + 1).toUpperCase()), "w 40:40:40");
            } else {
                if (i == 0)
                    panelContent.add(Util.createTextField("0x00"), "w 40:40:40");
                panelContent.add(content[i]);
            }
        }

        add(Util.createTitleLabel("RAM"), "span, growx, pushx, wrap");

        // VALUE
        add(Util.createLabel("Value: "), "newline, right");
        for (char i = 0; i < value.length; i++) {
            if (i == 1)
                add(value[i], "span, split " + (value.length - 1));
            else
                add(value[i]);
        }
        add(txtValue, "newline, growx, span, skip");

        // OTHER
        add(Util.createTextField("Memory Map"), "wrap, center, growx, pushx, span");
        add(new JScrollPane(panelContent), "h 110:110:110, span, w 265:265:265");
        add(Util.createLabel("W/R: "), "right");
        add(wr, "");
        add(Util.createLabel("Input: "), "split 2, gapx 10");
        add(input, "growx, pushx");
        add(Util.createLabel("Output: "), "split 2, right");
        add(output, "wrap");
    }

    public void setInput(boolean value) {
        if (value)
            input.setGreen();
        else
            input.setGray();

        //repaint();
    }

    public void setOutput(boolean value) {
        if (value)
            output.setGreen();
        else
            output.setGray();

        //repaint();
    }

    public void setWriteRead(boolean value) {
        if (value)
            wr.setGreen();
        else
            wr.setGray();
    }

    public void setContent(int index, byte value) {
        index = index * 8;
        int length = (index + 8);
        char mod;

        // Troca a cor co circulo de acordo o valor do byte
        for (int i = index; i < length; i++) {
            mod = (char) (i % 8);

            if ((value & (0x01 << mod)) > 0)
                this.content[(length - 1) - mod].setGreen(); // Pos. complementar
            else
                this.content[(length - 1) - mod].setGray();  // Pos. complementar 
        }
    }
}
