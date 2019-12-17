package Screen.Panel;

import Screen.Cicle;
import Screen.Oscillator;
import Model.Util;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author MrCapybara
 */
public final class PanelClock extends JPanel {
    
    private final Cicle halt = new Cicle();
    private final Cicle output = new Cicle();
    
    private final JTextField txtAuto = Util.createTextField(JLabel.CENTER);
    private final JTextField txtFrequency = Util.createTextField(JLabel.CENTER);
    
    private final Oscillator oscillator = new Oscillator();
    
    public PanelClock() {
        setBorder(new EtchedBorder());
        setLayout(new MigLayout("w 280:280:280"));
        
        add(Util.createTitleLabel("Clock"), "span, growx, pushx, wrap");
        add(Util.createLabel("Mode: "), "newline, right");
        add(txtAuto, "growx, pushx, span");
        add(Util.createLabel("Frequency: "), "newline");
        add(txtFrequency, "w 100%, span");
        add(Util.createLabel("Halt: "), "newline, right");
        add(halt);
        add(Util.createLabel("Output: "), "split");
        add(output, "wrap");
        add(oscillator, "span, center");
    }
    
    public Oscillator getOscillator() {
        return oscillator;
    }
    
    public void setHalt(boolean value) {
        if (value)
            halt.setGreen();
        else
            halt.setGray();
        
       //repaint();
    }
    
    public void setOutput(boolean value) {
        if (value)
            output.setGreen();
        else
            output.setGray();
        
       //repaint();
    }
    
    public void setFrequency(float frequency) {
        txtFrequency.setText(frequency + "Hz");
    }
    
    public void setAuto(boolean value) {
        if (value)
            txtAuto.setText("Auto");
        else
            txtAuto.setText("Manual");
    }
    
}
