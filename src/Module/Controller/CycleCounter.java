package Module.Controller;

import Emulator.Computer;
import Model.EdgeTrigger;

/**
 *
 * @author MrCapybara
 */
public class CycleCounter implements EdgeTrigger {

    private byte value = 0x00;

    @Override
    public void risingEdge() {
        Computer.refreshParallelBits();
    }

    @Override
    public void fallingEdge() {
        if (++value > 4)
            value = 0;

        Computer.control.refreshControlBits();         // Atualizar o controle
        Computer.refreshParallelBits();
    }

    public byte getValue() {
        return value;
    }

    public void reset() {
        this.value = 0x00;
    }
}
