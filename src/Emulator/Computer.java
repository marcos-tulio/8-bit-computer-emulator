package Emulator;

import Model.EdgeTrigger;
import Module.Register;
import Module.ALU;
import Module.Bus;
import Module.Counter;
import Module.Control;
import Module.MAR;
import Module.RAM;
import javax.swing.SwingUtilities;

/**
 *
 * @author MrCapybara
 */
public class Computer {

    /*
        .---------------------------------------. 
        |        Conjunto de instruções         |
        |---------------------------------------| 
        | NOP | 0000 |  XXh  | P. Counter++     |
        | LDA | 0001 | (XXh) | ACC = (XXh)      |
        | LDI | 0010 |  0Xh  | ACC = 0Xh        |  
        | STA | 0011 | (XXh) | (XXh) = ACC      |     
        | ADD | 0100 | (XXh) | ACC += (XXh)     |    
        | SUB | 0101 | (XXh) | ACC -= (XXh)     |  
        | AND | 0110 | (XXh) | ACC &= (XXh)     |  
        | ORL | 0111 | (XXh) | ACC |= (XXh)     |   
        | XOR | 1000 | (XXh) | ACC ^= (XXh)     |  
        | NOT | 1001 |  XXh  | ACC = /(ACC)     |     
        | JMP | 1010 |  0Xh  | P. counter = XXh |   
        | --- | 1011 |  XXh  | ---------------- |
        | --- | 1100 |  XXh  | ---------------- |
        | --- | 1101 |  XXh  | ---------------- |
        | OUT | 1110 |  XXh  | OUT = ACC        |
        | HLT | 1111 |  XXh  | Des. o clock     |
        *---------------------------------------*
     */
    // ******************************************************** //
    // *                      Emulador                        * //
    // ******************************************************** //
    public static CtrlScreen screen = new CtrlScreen();
    public static CtrlTerminal terminal = new CtrlTerminal();

    // ******************************************************** //
    // *                Módulos do computador                 * //
    // ******************************************************** //
    public static ALU alu;                                      // ULA
    public static RAM ram;                                      // RAM
    public static MAR mar;                                      // Registrador de endereços
    public static Bus bus;                                      // Barramento
    public static Control control;                              // Controle
    public static Counter counter;                              // Contador de programa
    public static Register output;                              // Registrador de saída
    public static Register bRegister;                           // Registrador B
    public static Register accumulator;                         // Registrador acumulador
    public static Register instruction;                         // Registrador de instruções

    /*  Triggers para os módulos que dependem de clock. Estes triggers são executados 
       dentro do módulo de clock. */
    public static EdgeTrigger[] edgeTrigger;

    public static void boot() {
        // Carregar os módulos
        Computer.alu = new ALU();
        Computer.ram = new RAM();
        Computer.mar = new MAR();
        Computer.bus = new Bus();
        Computer.control = new Control();
        Computer.output = new Register();
        Computer.counter = new Counter();
        Computer.bRegister = new Register();
        Computer.accumulator = new Register();
        Computer.instruction = new Register();

        // Carregar os triggers
        edgeTrigger = new EdgeTrigger[]{
            (EdgeTrigger) Computer.bRegister,
            (EdgeTrigger) Computer.accumulator,
            (EdgeTrigger) Computer.instruction,
            (EdgeTrigger) Computer.ram,
            (EdgeTrigger) Computer.mar,
            (EdgeTrigger) Computer.output,
            (EdgeTrigger) Computer.counter,
            (EdgeTrigger) Computer.control.getCycleCounter()
        };

        // Carregar os estados iniciais
        Computer.refreshParallelBits();
        Computer.control.refreshControlBits();

        // Carregar a tela
        Computer.screen.refresh();
        Computer.screen.setVisible(true);
        Computer.screen.getTerminal().resetCursor();
    }

    /**
     * Atualiza os componentes ligados em pararelo. Simula uma ligação física
     * que atualiza instantaneamente.
     */
    public static void refreshParallelBits() {
        // Atualizar a ULA
        Computer.alu.refresh();

        // Atualizar a RAM
        if (Computer.ram.isRead())
            Computer.ram.setValue(Computer.ram.getContent(Computer.mar.getValue()));

        // Atualizar o barramento
        byte busValue = 0x00;
        if (Computer.counter.isOutput())
            busValue |= Computer.counter.getValue();

        if (Computer.accumulator.isOutput())
            busValue |= Computer.accumulator.getValue();

        if (Computer.bRegister.isOutput())
            busValue |= Computer.bRegister.getValue();

        if (Computer.alu.isOutput())
            busValue |= Computer.alu.getValue();

        if (Computer.output.isOutput())
            busValue |= Computer.output.getValue();

        if (Computer.instruction.isOutput())
            busValue |= Computer.instruction.getLsb();

        if (Computer.ram.isOutput())
            busValue |= Computer.ram.getValue();

        Computer.bus.setValue(busValue);    // Atualiza o barramento
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Computer::boot);
    }
}
