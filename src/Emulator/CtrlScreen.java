package Emulator;

import Screen.TerminalIO;
import Screen.ViewScreen;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author MrCapybara
 */
public class CtrlScreen extends ViewScreen {

    public void refreshOscillator() {
        pClock.getOscillator().setValue(Computer.control.getClock().getOutput());
    }

    public void refresh() {
        // Program counter
        pCounter.setJump(Computer.counter.isJump());
        pCounter.setValue(Computer.counter.getValue());
        pCounter.setEnable(Computer.counter.isEnable());
        pCounter.setOutput(Computer.counter.isOutput());
        pCounter.setIncrement(Computer.counter.isIncrement());

        // Bus
        pBus.setValue(Computer.bus.getValue());

        // Clock
        pClock.setHalt(Computer.control.getClock().isHalt());
        pClock.setOutput(Computer.control.getClock().getOutput());
        pClock.setFrequency(Computer.control.getClock().getFrequency());
        pClock.setAuto(Computer.control.getClock().isAuto());

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
        pALU.setOutput(Computer.alu.isOutput());
        pALU.setValue(Computer.alu.getValue());
        pALU.setAccOne(Computer.alu.getAccOne());
        pALU.setAccZero(Computer.alu.getAccZero());
        pALU.setAddSub(Computer.alu.getAddSub());
        pALU.setXorNot(Computer.alu.getXorNot());

        // Instruction
        pInstruction.setOutput(Computer.instruction.isOutput());
        pInstruction.setInput(Computer.instruction.isInput());
        pInstruction.setValue(Computer.instruction.getValue());

        // Instruction
        pOutRegister.setOutput(Computer.output.isOutput());
        pOutRegister.setInput(Computer.output.isInput());
        pOutRegister.setValue(Computer.output.getValue());

        // MAR
        pMAR.setInput(Computer.mar.isInput());
        pMAR.setValue(Computer.mar.getValue());

        //RAM
        pRAM.setInput(Computer.ram.isInput());
        pRAM.setOutput(Computer.ram.isOutput());
        pRAM.setValue(Computer.ram.getValue());
        pRAM.setWriteRead(Computer.ram.isWriteRead());
        for (int i = 0; i < Computer.ram.getSize(); i++) {
            pRAM.setContent(i, Computer.ram.getContent(i));
        }

        // Control
        pControl.setCycleCounter(Computer.control.getCycleCounter().getValue());
        pControl.changeColor(Computer.control.getClock().isHalt(), "HLT");
        pControl.changeColor(Computer.counter.isIncrement(), "PCI");
        pControl.changeColor(Computer.counter.isJump(), "JMP");
        pControl.changeColor(Computer.counter.isOutput(), "PCO");
        pControl.changeColor(Computer.mar.isInput(), "MAI");
        pControl.changeColor(Computer.ram.isInput(), "RMI");
        pControl.changeColor(Computer.ram.isOutput(), "RMO");
        pControl.changeColor(Computer.instruction.isInput(), "IRI");
        pControl.changeColor(Computer.instruction.isOutput(), "IRO");
        pControl.changeColor(Computer.accumulator.isOutput(), "ACO");
        pControl.changeColor(Computer.accumulator.isInput(), "ACI");
        pControl.changeColor(Computer.alu.getAddSub(), "SUB");
        pControl.changeColor(Computer.alu.getAccOne(), "AL1");
        pControl.changeColor(Computer.alu.getAccZero(), "AL0");
        pControl.changeColor(Computer.alu.isOutput(), "ALU");
        pControl.changeColor(Computer.alu.getXorNot(), "NOT");
        pControl.changeColor(Computer.bRegister.isInput(), "BRI");
        pControl.changeColor(Computer.output.isInput(), "ORI");

        repaint();
    }

    public TerminalIO getTerminal() {
        return txtTerminal;
    }

    public File chooseFile() {
        File file = null;

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            getTerminal().outln("Importing \"" + chooser.getSelectedFile().getName() + "\"...");
            file = chooser.getSelectedFile();
        }

        return file;
    }
}
