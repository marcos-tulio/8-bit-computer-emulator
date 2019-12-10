package Screen;

import javax.swing.JTextArea;

/**
 *
 * @author MrCapybara
 */
public final class TerminalIO extends JTextArea {

    private final String CMD_PREFIX = "8-bit << ";
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

    public void printError() {
        setText(getText() + PREFIX + "Command invalid!\n");
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

        return line.replace(CMD_PREFIX, "").replaceAll("\n", "");
    }

    public void newLine() {
        print(CMD_PREFIX);
    }

    public String getPrefix() {
        return CMD_PREFIX;
    }
}
