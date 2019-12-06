package Emulator;


import Modules.Clock;


/**
 *
 * @author MrCapybara
 */

@Deprecated
public class Loop implements Runnable {

    /* Modules */
    Clock clock = new Clock();

    @Override
    public void run() {
        float actualFrequency = 0;
        
        while (true) {
            actualFrequency = clock.halfCycle();   
            actualFrequency += clock.halfCycle();
            
            System.out.println(formatFrequency(actualFrequency));
        }
    }

    private String formatFrequency(float frequency) {
        if (frequency < 1000) {
            return frequency + "Hz";
        } else if (frequency < 1000000) {
            return (frequency / 1000) + "MHz";
        } else {
            return (frequency / 1000000) + "GHz";
        }
    }
}
