package Modules;

import Models.EdgeTrigger;
import Emulator.Computer;

/**
 * Program counter module.
 *
 * @author MrCapybara
 */
public class Counter implements EdgeTrigger {

    private boolean enable = true;
    private byte counter = 0x00;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public byte getCounter() {
        return counter;
    }

    public void setCounter(byte counter) {
        this.counter = counter;
    }

    public void increment() {
        if (enable) {
            counter++;

            // Zera o nibble
            if (counter > 0x0F) {
                counter = 0;
            }
        }
    }

    public void reset() {
        counter = 0;
    }

    public void jump() {
        setCounter(Computer.bus.getValue());
    }

    public void output() {
        // Mantém o msb do barramento e o une com o lsb do contador

        byte msb = (byte) (Computer.bus.getValue() & 0xF0);
        byte lsb = (byte) (counter & 0x0F);
        Computer.bus.setValue((byte) (msb | lsb));
    }

    @Override
    public void lowToHigh() {
        increment();
    }

    @Override
    public void highToLow() {
        
    }
}
