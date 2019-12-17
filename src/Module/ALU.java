package Module;

import Emulator.Computer;

/**
 *
 * @author MrCapybara
 */
public class ALU {

    private byte value = 0x00;
    private boolean output = false;

    private boolean xorNot = false;
    private boolean addSub = false;
    private boolean accOne = false;
    private boolean accZero = false;

    private String operation = "add";

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public boolean getAccZero() {
        return accZero;
    }

    public void setAccZero(boolean accZero) {
        this.accZero = accZero;
    }

    public boolean getAccOne() {
        return accOne;
    }

    public void setAccOne(boolean accOne) {
        this.accOne = accOne;
    }

    public boolean getXorNot() {
        return xorNot;
    }

    public void setXorNot(boolean xorNot) {
        this.xorNot = xorNot;
    }

    public boolean getAddSub() {
        return addSub;
    }

    public void setAddSub(boolean addSub) {
        this.addSub = addSub;
    }

    public byte getValue() {
        return value;
    }

    public void refresh() {
        // Tabela verdade
        // XN  AS  A1  A0  OUT
        // x   0   0   0   ADD
        // x   1   0   0   SUB
        // x   x   0   1   AND
        // x   x   1   0   OR
        // 0   x   1   1   XOR
        // 1   x   1   1   NOT(ACC)

        if (!accZero && !accOne && !addSub) {
            // ADD
            value = (byte) (Computer.accumulator.getValue() + Computer.bRegister.getValue());
            operation = "add";
        } else if (!accZero && !accOne && addSub) {
            // SUB
            value = (byte) (Computer.accumulator.getValue() - Computer.bRegister.getValue());
            operation = "sub";
        } else if (accZero && accOne && xorNot) {
            // NOT Acc
            value = (byte) (Computer.accumulator.getValue() ^ 0xFF);
            operation = "not";
        } else if (accZero && accOne && !xorNot) {
            // XOR
            value = (byte) (Computer.accumulator.getValue() ^ Computer.bRegister.getValue());
            operation = "xor";
        } else if (accZero && !accOne) {
            // AND            
            value = (byte) (Computer.accumulator.getValue() & Computer.bRegister.getValue());
            operation = "and";
        } else {
            // OR
            value = (byte) (Computer.accumulator.getValue() | Computer.bRegister.getValue());
            operation = "or";
        }
    }

    public String getOperation() {
        return operation;
    }
}
