package Emulator;

import Models.Register;
import Modules.Bus;
import Modules.Counter;
import Modules.Clock;

/**
 *
 * @author MrCapybara
 */
public class Computer {

    // ********* 8 bits PC ********* //
    public static Clock clock = new Clock();
    public static Counter counter = new Counter();
    public static Bus bus = new Bus();
    public static Register accumulator = new Register();
    public static Register bRegister = new Register();

    public static void setup() {

    }

    /* General computer loop.
        Esta função é executada a cada meio ciclo de clock. 
     */
    public static void loop() {

        if (clock.getOutput()) {    // LOW -> HIGH
            counter.lowToHigh();
            accumulator.lowToHigh();
            bRegister.lowToHigh();
        }
        
        /*else {                    // HIGH -> LOW       
            counter.highToLow();
            accumulator.highToLow();
            b.highToLow();
        }*/
    }

    // ********* Simulator ********* //
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        terminal.runLoop();
    }
}
