package Modules;

import Emulator.Computer;

/**
 *
 * @author MrCapybara
 */
public class Clock {

    private boolean auto = true;
    private boolean halt = false;

    private boolean output = false;

    private float frequency = 1000;
    private float halfFrequency = frequency / 2f;

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
        this.halfFrequency = frequency / 2f;
    }

    public float getHalfFrequency() {
        return halfFrequency;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public float halfCycle() {
        long startTime = System.nanoTime();
        float actualFrequency;

        do {
            actualFrequency = timeToFrequency((System.nanoTime() - startTime));
        } while (actualFrequency > halfFrequency);

        if (halt) {
            output = false;
        } else {
            output = !output;
            Computer.loop();
        }

        return actualFrequency;
    }

    public float fullCycle() {
        float actualFrequency = 0;
        actualFrequency = halfCycle();
        actualFrequency += halfCycle();

        /*if (!halt) {
            Computer.loop();
        }*/
        return actualFrequency;
    }

    private float timeToFrequency(long nano) {
        float millis = (nano / 1000f) / 1000f;
        return 1f / (millis / 1000f);
    }

    public boolean isHalt() {
        return halt;
    }

    public void setHalt(boolean halt) {
        this.halt = halt;
    }

    public boolean getOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }
}
