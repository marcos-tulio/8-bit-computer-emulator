package Module;

import Emulator.Computer;

/**
 *
 * @author MrCapybara
 */
public class MAR extends Register {

    /* A saída do MAR é ligada diretamente à RAM. */
    @Override
    public void setOutput(boolean output) {
    }

    @Override
    public void setValue(byte value) {
        this.value = (byte) (value & 0x0F); // Seta o valor limitando-o a 0x0F.
    }

    @Override
    public void risingEdge() {
        super.risingEdge();

        if (Computer.ram.isWrite())
            Computer.ram.setContentInAddress(getValue());
    }
}
