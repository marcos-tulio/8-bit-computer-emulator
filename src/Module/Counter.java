package Module;

import Emulator.Computer;
import Model.EdgeTrigger;

/**
 * Program counter module.
 *
 * @author MrCapybara
 */
public final class Counter implements EdgeTrigger {

    protected byte value = 0x00;

    protected boolean enable = true;
    protected boolean increment = false;
    protected boolean jump = false;
    protected boolean output = false;

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isJump() {
        return this.jump;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setIncrement(boolean inc) {
        increment = inc;
    }

    public boolean isIncrement() {
        return increment;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = (byte) (value & 0x0F); // Limitar a 15
    }

    public void increment() {
        if (isEnable()) {
            // Contador ciclo 0 a 15
            if (getValue() + 1 > 0x0F)
                reset();
            else
                setValue((byte) (getValue() + 1));
        }
    }

    public void reset() {
        value = 0x00;
    }

    @Override
    public void risingEdge() {
        if (isEnable()) {
            if (isIncrement())
                increment();

            if (isJump())
                setValue(Computer.bus.getLsb());
        }
    }

    @Override
    public void fallingEdge() {

    }
}
