package Model;

import Emulator.Computer;
import Screen.Cicle;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author MrCapybara
 */
public class Util {

    public static final Font FONT = new Font("Monospaced", Font.PLAIN, 12);

    public static enum BIT {
        HALT, PC_INC, PC_JMP, PC_OUT, MAR_IN, RAM_IN, RAM_OUT, INS_IN, INS_OUT,
        ACC_OUT, ACC_IN, ADD_SUB, ALU_ACC1, ALU_ACC0, ALU_OUT, XOR_NOT, BR_IN, OUT_IN,
        NULL
    };

    public static void setBit(BIT bit, boolean value) {
        switch (bit) {
            case ACC_IN:
                Computer.accumulator.setInput(value);
                return;
            case ACC_OUT:
                Computer.accumulator.setOutput(value);
                return;
            case ADD_SUB:
                Computer.alu.setAddSub(value);
                return;
            case ALU_ACC0:
                Computer.alu.setAccZero(value);
                return;
            case ALU_ACC1:
                Computer.alu.setAccOne(value);
                return;
            case ALU_OUT:
                Computer.alu.setOutput(value);
                return;
            case BR_IN:
                Computer.bRegister.setInput(value);
                return;
            case HALT:
                Computer.control.getClock().setHalt(value);
                return;
            case INS_IN:
                Computer.instruction.setInput(value);
                return;
            case INS_OUT:
                Computer.instruction.setOutput(value);
                return;
            case PC_JMP:
                Computer.counter.setJump(value);
                return;
            case MAR_IN:
                Computer.mar.setInput(value);
                return;
            case OUT_IN:
                Computer.output.setInput(value);
                return;
            case PC_INC:
                Computer.counter.setIncrement(value);
                return;
            case PC_OUT:
                Computer.counter.setOutput(value);
                return;
            case RAM_IN:
                Computer.ram.setInput(value);
                return;
            case RAM_OUT:
                Computer.ram.setOutput(value);
                return;
            case XOR_NOT:
                Computer.alu.setXorNot(value);
                return;
        }
    }

    public static JLabel createTitleLabel(String txt) {
        JLabel lbl = new JLabel(txt);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setFont(FONT.deriveFont(Font.BOLD));
        return lbl;
    }

    public static JLabel createLabel(String txt) {
        JLabel lbl = new JLabel(txt);
        lbl.setFont(FONT);
        return lbl;
    }

    public static JTextField createTextField(int alignment) {
        JTextField txt = new JTextField();
        txt.setFont(FONT);
        txt.setEditable(false);
        txt.setHorizontalAlignment(alignment);
        txt.setFocusable(false);
        return txt;
    }

    public static JTextField createTextField() {
        return createTextField(JLabel.RIGHT);
    }

    public static JTextField createTextField(String str) {
        JTextField textField = createTextField(JLabel.CENTER);
        textField.setText(str);
        return textField;
    }

    public static Cicle[] createCicles(int size) {
        Cicle cicles[] = new Cicle[size];

        for (char i = 0; i < cicles.length; i++)
            cicles[i] = new Cicle();

        return cicles;
    }

    public static byte[] readInternalFile(String path) {
        byte[] values = null;

        try (InputStream in = Util.class.getResourceAsStream(path)) {
            values = in.readAllBytes();
            in.close();
        } catch (IOException ex) {
            System.err.println("Não foi possível lê o arquivo '" + path + "'.");
        }

        return values;
    }

    public static String formatByte(byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    public static String formatNibble(byte b) {
        return String.format("%4s", Integer.toBinaryString(b & 0xF)).replace(' ', '0');
    }

}
