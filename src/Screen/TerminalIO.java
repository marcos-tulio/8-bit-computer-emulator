package Screen;

import javax.swing.JTextArea;

/**
 *
 * @author MrCapybara
 */
public final class TerminalIO extends JTextArea {

    private final String CMD_PREFIX_IN = "8-bit << ";
    private final String CMD_PREFIX_OUT = "8-bit >> ";
    private final String PREFIX = "";

    public TerminalIO() {
        newLine();
    }

    public void print(String txt) {
        setText(getText() + PREFIX + txt);
    }

    public void println(String txt) {
        setText(getText() + PREFIX + txt + "\n");
    }

    public void clear() {
        setText("");
    }

    public void out(String txt) {
        setText(getText() + PREFIX + CMD_PREFIX_OUT + txt);
    }

    public void outln(String txt) {
        setText(getText() + PREFIX + CMD_PREFIX_OUT + txt + "\n");
    }

    public void outError() {
        setText(getText() + PREFIX + CMD_PREFIX_OUT + "Error: invalid command!\n");
    }

    public void outError(String str) {
        setText(getText() + PREFIX + CMD_PREFIX_OUT + "Error: " + str + "\n");
    }

    public String nextLine() {
        String line = getText();
        line = line.substring(0, line.length() - 1);
        int i = line.length() - 1;

        while (i > 0) {
            if (line.substring(i, i + 1).equals("\n")) {
                line = line.substring(i);
                break;
            }
            i--;
        }

        return line.replace(CMD_PREFIX_IN, "").replaceAll("\n", "");
    }

    public void newLine() {
        print(CMD_PREFIX_IN);
    }

    public String getPrefix() {
        return CMD_PREFIX_IN;
    }

    public void resetCursor() {
        setCaretPosition(getDocument().getLength());
    }
}
