package Modules;

/**
 *
 * @author MrCapybara
 */
public class Bus {

    private byte value = 0x00;
    private boolean busy = false;

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
    
    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}
