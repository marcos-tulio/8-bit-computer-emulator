package Module;

import Emulator.Computer;

/**
 *
 * @author MrCapybara
 */
public class RAM extends Register {

    private final byte content[] = new byte[16];
    private boolean writeRead = true;           // write = false

    public boolean isWriteRead() {
        return writeRead;
    }

    public boolean isRead() {
        return isWriteRead();
    }

    public boolean isWrite() {
        return isWriteRead() == false;
    }

    public void setWriteRead(boolean writeRead) {
        this.writeRead = writeRead;
    }

    public byte getContent(int index) {
        return content[index];
    }

    public void setContentInAddress(int address) {
        this.content[address] = getValue();
    }

    public void setContent(int index, byte value) {
        this.content[index] = value;
    }

    public byte[] getAllValues() {
        return content;
    }

    public int getSize() {
        return content.length;
    }

    @Override
    public void risingEdge() {
        if (isInput()) {
            setValue(Computer.bus.getValue());
            setContentInAddress(Computer.mar.getValue());
        }
    }

    @Override
    public void reset() {
        setWriteRead(true);
    }

    public void clear() {
        for (int i = 0; i < getSize(); i++)
            content[i] = 0x00;
    }
}
