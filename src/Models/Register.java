/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Emulator.Computer;

/**
 *
 * @author MrCapybara
 */
public class Register implements EdgeTrigger {

    protected byte value = 0x00;
    protected boolean input = false;
    protected boolean output = false;

    public boolean isInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public void reset() {
        value = 0x00;
    }

    @Override
    public void lowToHigh() {
        //atualiza os estados
        if (input) {
            value = Computer.bus.getValue();
        }

        if (output) {
            Computer.bus.setValue(value);
        }
    }

    @Override
    public void highToLow() {

    }
}
