package Module;

/**
 *
 * @author MrCapybara
 */
public class Bus {

    private byte value = 0x00;

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public byte getMsb() {
        return (byte) (this.value & 0xF0);
    }

    public byte getLsb() {
        return (byte) (this.value & 0x0F);
    }

    public void setMsb(byte value) {
        setValue((byte) ((getValue() & 0x0F) | (value & 0xF0)));
    }

    public void setLsb(byte value) {
        setValue((byte) ((getValue() & 0xF0) | (value & 0x0F)));
    }

    public void clear() {
        clearLsb();
        clearMsb();
    }

    public void clearLsb() {
        setLsb((byte) 0x00);
    }

    public void clearMsb() {
        setMsb((byte) 0x00);
    }
}
