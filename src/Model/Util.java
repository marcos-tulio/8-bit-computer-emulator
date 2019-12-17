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
            //Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return values;
    }

    public static String formatByte(byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    public static String formatNibble(byte b) {
        return String.format("%4s", Integer.toBinaryString(b & 0xF)).replace(' ', '0');
    }

    public static byte compileLine(String line) throws Exception {
        line = line.toLowerCase();
        line = line.replace("\t", " ");
        String ins = "", value = "0000";

        if (line.startsWith(" "))
            line = line.substring(1);

        // Retirar os comentários
        if (line.contains(";"))
            line = line.substring(0, line.indexOf(";")) + "\n";

        // Identificar a instrução
        if (line.contains(" "))
            ins = line.substring(0, line.indexOf(" "));
        else if (line.contains("\n"))
            ins = line.substring(0, line.indexOf("\n"));
        else
            ins = line;

        if (ins.isEmpty())
            throw new Exception("only comment line");

        // Identificar o valor
        if (line.contains("0x"))
            value = line.substring(ins.length()).replace("0x", "");

        else if (line.contains("h") && !line.contains("hlt"))
            value = line.substring(ins.length(), line.indexOf("h"));

        value = value.replace("\n", "").replace(" ", "");

        return (byte) ((getInstruction(ins) << 4) | (Integer.valueOf(value, 16) & 0x0F));
    }

    public static byte getInstruction(String ins) throws Exception {
        switch (ins) {
            case "nop":
                return 0b0000;
            case "lda":
                return 0b0001;
            case "ldi":
                return 0b0010;
            case "sta":
                return 0b0011;
            case "add":
                return 0b0100;
            case "sub":
                return 0b0101;
            case "and":
                return 0b0110;
            case "orl":
                return 0b0111;
            case "xor":
                return 0b1000;
            case "not":
                return 0b1001;
            case "jmp":
                return 0b1010;
            case "out":
                return 0b1110;
            case "hlt":
                return 0b1111;
            case ".org":
                return 0b0000;
            default:
                throw new Exception();
        }

        /*
        .---------------------------------------. 
        | Possíveis operações para o computador |
        |---------------------------------------| 
        | NOP | 0000 |  0Xh  | P. Counter++     |
        | LDA | 0001 | (0Xh) | ACC = (XXh)      |
        | LDI | 0010 |  0Xh  | ACC = 0Xh        |  
        | STA | 0011 | (0Xh) | (XXh) = ACC      |     
        | ADD | 0100 | (0Xh) | ACC += (XXh)     |    
        | SUB | 0101 | (0Xh) | ACC -= (XXh)     |  
        | AND | 0110 | (0Xh) | ACC &= (XXh)     |  
        | ORL | 0111 | (0Xh) | ACC |= (XXh)     |   
        | XOR | 1000 | (0Xh) | ACC ^= (XXh)     |  
        | NOT | 1001 |  0Xh  | ACC = /(ACC)     |     
        | JMP | 1010 |  0Xh  | P. counter = XXh |   
        | --- | 1011 |  0Xh  | ---------------- |
        | --- | 1100 |  0Xh  | ---------------- |
        | --- | 1101 |  0Xh  | ---------------- |
        | OUT | 1110 |  0Xh  | OUT = ACC        |
        | HLT | 1111 |  0Xh  | Des. o clock     |
        *---------------------------------------*
         */
    }

}
