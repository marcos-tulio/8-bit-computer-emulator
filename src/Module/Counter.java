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
    protected boolean increment = true;
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

        if (isOutput()) {
            if (isEnable())
                Computer.bus.setLsb(getValue());
            else
                Computer.bus.clearLsb();
        }
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

        if (isOutput())
            Computer.bus.setValue(getValue());
        else
            Computer.bus.clear();
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public void increment() {
        if (isEnable()) {
            setValue((byte) (getValue() + 1));

            // Zera o nibble
            if (getValue() > 0x0F)
                setValue((byte) 0x00);
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

            if (isOutput())
                Computer.bus.setLsb(getValue());
        }
    }

    @Override
    public void fallingEdge() {

    }
}
