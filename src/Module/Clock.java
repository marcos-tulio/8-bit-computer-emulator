package Module;

import Emulator.Computer;

/**
 *
 * @author MrCapybara
 */
public class Clock {

    private boolean auto = false;
    private boolean halt = false;

    private boolean output = false;

    private float frequency = 10;
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

    public float halfCycle() {
        long startTime = System.nanoTime();
        float actualFrequency;

        do {
            actualFrequency = timeToFrequency((System.nanoTime() - startTime));
        } while (actualFrequency > getHalfFrequency());

        if (isHalt())
            setOutput(false);
        else {
            setOutput(!getOutput());
            Computer.clockSync();
        }

        Computer.screen.refresh();

        return actualFrequency;
    }

    public float fullCycle() {
        float actualFrequency = 0;
        actualFrequency = halfCycle();
        actualFrequency += halfCycle();

        return actualFrequency;
    }

    private float timeToFrequency(long nano) {
        float millis = (nano / 1000f) / 1000f;
        return 1f / (millis / 1000f);
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
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
