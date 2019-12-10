package Emulator;

import Model.EdgeTrigger;
import Module.Instruction;
import Module.ALU;
import Module.Bus;
import Module.Counter;
import Module.Clock;
import Module.RAM;
import Module.RegisterALU;
import javax.swing.SwingUtilities;

/**
 *
 * @author MrCapybara
 */
public class Computer {
    //public static final int RAM_SIZE = 16; // Ram Bytes total
    
    // ******************************************************** //
    // *                      Emulador                        * //
    // ******************************************************** //
    public static CtrlScreen screen = new CtrlScreen();
    public static CtrlTerminal terminal = new CtrlTerminal();

    // ******************************************************** //
    // *                Módulos do computador                 * //
    // ******************************************************** //
    public static ALU alu = new ALU();
    public static RAM ram = new RAM();
    public static Bus bus = new Bus();                          // Barramento
    public static Clock clock = new Clock();                    // Clock
    public static Counter counter = new Counter();              // Contador de programa
    public static RegisterALU bRegister = new RegisterALU();    // Registrador B
    public static RegisterALU accumulator = new RegisterALU();  // Registrador acumulador
    public static Instruction instruction = new Instruction();  // Registrador de instruções

    // Módulos que dependem de clock.
    private static final EdgeTrigger[] EDGE_TRIGGER = {
        (EdgeTrigger) Computer.counter,
        (EdgeTrigger) Computer.accumulator,
        (EdgeTrigger) Computer.bRegister,
        (EdgeTrigger) Computer.instruction
    };

    public static void boot() {
        Computer.terminal.setInput(Computer.screen.getTerminal());
        Computer.screen.refresh();
        Computer.screen.setVisible(true);
    }

    /**
     * Esta função é executada a cada meio ciclo de
     * clock.
     */
    public static void clockSync() {
        // Simula as bordas
        for (EdgeTrigger trigger : Computer.EDGE_TRIGGER) {
            if (Computer.clock.getOutput())
                trigger.risingEdge();
            else
                trigger.fallingEdge();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Computer.boot();
            
            
        });
    }
}
