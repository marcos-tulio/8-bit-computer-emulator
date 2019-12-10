package Screen.Panel;

import Model.PanelWithCicle;
import Screen.Cicle;
import Screen.Util;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author MrCapybara
 */
public class PanelRAM extends JPanel {

    private final Cicle input = new Cicle();
    private final Cicle output = new Cicle();
    private final Cicle[] value;

    public PanelRAM() {
        value = new Cicle[16 * 8];

        for (int i = 0; i < this.value.length; i++) {
            value[i] = new Cicle();
        }

        setBorder(new EtchedBorder());
        setLayout(new MigLayout("w 280:280:280"));

        JPanel content = new JPanel(new MigLayout());
        for (char i = 0; i < value.length; i++) {
            if (i % 8 == 7) {

                content.add(value[i], "split, wrap");
                if (i < value.length - 1)
                    content.add(Util.txt("0x0" + Integer.toHexString((i / 8) + 1).toUpperCase()), "w 40:40:40");
            } else {
                if (i == 0)
                    content.add(Util.txt("0x00"), "w 40:40:40");
                content.add(value[i], "split");
            }
        }

        add(Util.lblTitle("RAM"), "span, growx, pushx, wrap");
        add(Util.txt("Addr"), "w 47:47:47, split");
        add(Util.txt("Value"), "wrap, center, growx, pushx");
        add(new JScrollPane(content), "h 180:180:180, span, w 265:265:265");
        add(Util.label("Input: "), "split");
        add(input, "growx");
        add(Util.label("Output: "));
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

    public int ind = 0;
    
    public void setValue(int index, byte value) {
        index = index * 8;
        int length = (index + 8);
        char mod;

        // Troca a cor co circulo de acordo o valor do byte
        for (int i = index; i < length; i++) {
            mod = (char) (i % 8);

            if ((value & (0x01 << mod)) > 0)
                this.value[(length - 1) - mod].setGreen(); // Pos. complementar
            else
                this.value[(length - 1) - mod].setGray();  // Pos. complementar 
        }

        repaint();
    }
}
