package Screen.Panel;

import Model.PanelWithCicle;
import Screen.Cicle;
import Model.Util;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 * JPanel da memória RAM.
 *
 *
 * @author MrCapybara
 */
public final class PanelControl extends PanelWithCicle {

    private final Cicle[] counter = Util.createCicles(5);
    private final JTextField txtCounter = Util.createTextField("");
    private final List<String> labels = Arrays.asList(
            "ORI", "BRI", "NOT", "ALU", "AL0", "AL1", "SUB", "ACI", "ACO",
            "IRO", "IRI", "RMO", "RMI", "MAI", "PCO", "JMP", "PCI", "HLT"
    );

    public PanelControl() {
        super(18);

        initProperties();
        initComponents();
    }

    private void initProperties() {
        setBorder(new EtchedBorder());
        setLayout(new MigLayout("w 280:280:280"));
    }

    private void initComponents() {
        add(Util.createTitleLabel("Control"), "span, growx, pushx, wrap");

        // Contador de ciclos
        add(Util.createLabel("Cycle counter: "), "newline, right, split, span");
        for (Cicle cicle : counter)
            add(cicle);
        add(txtCounter, "growx, wrap 10");

        int i;
        for (i = 0; i < (value.length / 2); i++) {
            add(Util.createTextField(labels.get(i)));
        }

        for (i = 0; i < value.length; i++) {
            if (i % (value.length / 2) == 0)
                add(value[i], "newline, center");
            else
                add(value[i], "center");
        }

        for (i = (value.length / 2); i < value.length; i++) {
            if (i == (value.length / 2))
                add(Util.createTextField(labels.get(i)), "newline");
            else
                add(Util.createTextField(labels.get(i)));
        }
    }

    public void setCycleCounter(byte value) {
        for (byte i = 0; i < counter.length; i++) {
            if (i == value)
                changeColor(true, counter[counter.length - 1 - i]);
            else
                changeColor(false, counter[counter.length - 1 - i]);
        }

        txtCounter.setText("T" + value);
    }

    public void changeColor(boolean value, String label) {
        int index = labels.indexOf(label.toUpperCase());

        if (index == -1 || index > this.value.length) {
            System.err.println("Índice da lista não encontrado!");
            return;
        }

        super.changeColor(value, this.value[index]);
    }
}
