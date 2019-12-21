package Module;

import Module.Controller.Clock;
import Emulator.Computer;
import Model.EEPROM;
import Module.Controller.CycleCounter;
import Model.Util;
import Model.Util.BIT;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author MrCapybara
 */
public final class Control {

    private final Clock CLOCK = new Clock();
    private final CycleCounter CYCLE_COUNTER = new CycleCounter();

    private final EEPROM EEPROM0;
    private final EEPROM EEPROM1;
    private final EEPROM EEPROM2;

    //
    //|----------------------------------|----------------------------------|----------------------------------|//
    //|        EEPROM 2 - LIGAÇÕES       |        EEPROM 1 - LIGAÇÕES       |        EEPROM 0 - LIGAÇÕES       |//
    //|  D0  D1  D2  D3  D4  D5  D6  D7  |  D0  D1  D2  D3  D4  D5  D6  D7  |  D0  D1  D2  D3  D4  D5  D6  D7  |//
    //|  IRI RAO RAI MAI PCO JMP PCI HLT |  NOT ALU AL0 AL1 SUB ACI ACO IRO |                          ORI BRI |//
    //|----------------------------------|----------------------------------|----------------------------------|//
    // Lista -> D0 ... D7
    private final List<BIT> EEPROM2_BITS = Arrays.asList(
            BIT.INS_IN, BIT.RAM_OUT, BIT.RAM_IN, BIT.MAR_IN, BIT.PC_OUT, BIT.PC_JMP, BIT.PC_INC, BIT.HALT
    );

    private final List<BIT> EEPROM1_BITS = Arrays.asList(
            BIT.XOR_NOT, BIT.ALU_OUT, BIT.ALU_ACC0, BIT.ALU_ACC1, BIT.ADD_SUB, BIT.ACC_IN, BIT.ACC_OUT, BIT.INS_OUT
    );

    private final List<BIT> EEPROM0_BITS = Arrays.asList(
            BIT.NULL, BIT.NULL, BIT.NULL, BIT.NULL, BIT.NULL, BIT.NULL, BIT.OUT_IN, BIT.BR_IN
    );

    public Control() {
        this.EEPROM2 = new EEPROM(Util.readInternalFile("/Resource/EEPROM2.bin"));
        this.EEPROM1 = new EEPROM(Util.readInternalFile("/Resource/EEPROM1.bin"));
        this.EEPROM0 = new EEPROM(Util.readInternalFile("/Resource/EEPROM0.bin"));
    }

    public void refreshControlBits() {
        // Agrupa os bits do contador de ciclos com o registrador de instruções
        byte cycle = getCycleCounter().getValue();
        byte instruction = Computer.instruction.getMsb();
        byte result = (byte) (((instruction & 0xF0) >> 0x01) | (cycle & 0x07)); // Simula a ligação em paralelo das memórias

        setBitFromEEPROM(EEPROM0.getValue(result), EEPROM0_BITS);
        setBitFromEEPROM(EEPROM1.getValue(result), EEPROM1_BITS);
        setBitFromEEPROM(EEPROM2.getValue(result), EEPROM2_BITS);
    }

    private void setBitFromEEPROM(byte value, List<BIT> listBits) {
        for (int i = 0; i < listBits.size(); i++)
            Util.setBit(listBits.get(i), ((value >> i) & 0x01) != 0x00);

    }

    public void reset() {
        Computer.ram.reset();
        Computer.mar.reset();
        Computer.output.reset();
        Computer.counter.reset();
        Computer.bRegister.reset();
        Computer.accumulator.reset();
        Computer.instruction.reset();
        Computer.control.getCycleCounter().reset();
        
        // Carregar os estados iniciais
        Computer.refreshParallelBits();
        Computer.control.refreshControlBits();
    }

    // Gets e sets padrões
    public Clock getClock() {
        return CLOCK;
    }

    public CycleCounter getCycleCounter() {
        return CYCLE_COUNTER;
    }
}
