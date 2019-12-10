package Emulator;

import Screen.TerminalIO;
import Screen.ViewScreen;

/**
 *
 * @author MrCapybara
 */
public class CtrlScreen extends ViewScreen {

    public void refresh() {
        // Program counter
        pCounter.setEnable(Computer.counter.isEnable());
        pCounter.setValue(Computer.counter.getValue());
        pCounter.setJump(Computer.counter.isJump());
        pCounter.setIncrement(Computer.counter.isIncrement());
        pCounter.setOutput(Computer.counter.isOutput());

        // Bus
        pBus.setValue(Computer.bus.getValue());

        // Clock
        pClock.setHalt(Computer.clock.isHalt());
        pClock.setOutput(Computer.clock.getOutput());
        pClock.getOscillator().setValue(Computer.clock.getOutput());
        pClock.setFrequency(Computer.clock.getFrequency());
        pClock.setAuto(Computer.clock.isAuto());

        // Accumulator
        pAccumulator.setInput(Computer.accumulator.isInput());
        pAccumulator.setOutput(Computer.accumulator.isOutput());
        pAccumulator.setValue(Computer.accumulator.getValue());

        // B Register
        pBRegister.setInput(Computer.bRegister.isInput());
        pBRegister.setOutput(Computer.bRegister.isOutput());
        pBRegister.setValue(Computer.bRegister.getValue());

        // ALU
        pALU.setOperation(Computer.alu.getOperation());
        pALU.setOutput(Computer.alu.getOutput());
        pALU.setValue(Computer.alu.getValue());
        pALU.setAccOne(Computer.alu.getAccOne());
        pALU.setAccZero(Computer.alu.getAccZero());
        pALU.setAddSub(Computer.alu.getAddSub());
        pALU.setXorNot(Computer.alu.getXorNot());

        // Instruction
        pInstruction.setOutput(Computer.instruction.isOutput());
        pInstruction.setInput(Computer.instruction.isInput());
        pInstruction.setValue(Computer.instruction.getValue());

        for (int i = 0; i < Computer.ram.getSize(); i++) {
            pRAM.setValue(i, Computer.ram.getValue(i));
        }
    }

    public TerminalIO getTerminal() {
        return txtTerminal;
    }
}
