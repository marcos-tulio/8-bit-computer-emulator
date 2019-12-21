package Model;

/**
 *
 * @author MrCapybara
 */
public final class EEPROM {

    private final byte[] CONTENT;

    public EEPROM(byte[] content) {
        this.CONTENT = content;
    }

    public byte[] getContent() {
        return CONTENT;
    }

    public byte getValue(int address) {
        return CONTENT[address];
    }
}
