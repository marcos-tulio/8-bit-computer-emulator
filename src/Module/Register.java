package Module;

import Emulator.Computer;
import Model.EdgeTrigger;

/**
 *
 * @author MrCapybara
 */
public class Register implements EdgeTrigger {

    protected byte value = 0x00;
    protected boolean input = false;
    protected boolean output = false;

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

    public byte getMsb() {
        return (byte) (this.value & 0xF0);
    }

    public byte getLsb() {
        return (byte) (this.value & 0x0F);
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public void reset() {
        value = 0x00;
    }

    @Override
    public void risingEdge() {
        // Carrega os valores do barramento para este registrador.
        if (isInput())
            setValue(Computer.bus.getValue());
    }

    @Override
    public void fallingEdge() {
        
    }
}
