package Module;

/**
 *
 * @author MrCapybara
 */
public class RAM {

    private final byte value[] = new byte[16];
    private boolean input = false;
    private boolean output = false;

    public byte getValue(int index) {
        return value[index];
    }

    public void setValue(int index, byte value) {
        this.value[index] = value;
    }

    public byte[] getAllValues() {
        return value;
    }

    public int getSize(){
        return value.length;
    }
    
    public boolean isInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

}
