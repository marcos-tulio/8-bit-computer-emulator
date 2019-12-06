package Emulator;

import Models.Register;
import java.util.Scanner;

/**
 *
 * @author MrCapybara
 */
public class Terminal {

    private final Scanner scanner = new Scanner(System.in);
    private Thread tLoop = null;

    public void runLoop() {
        if (tLoop == null || !tLoop.isAlive()) {
            tLoop = new Thread(loop());
            tLoop.start();
        }
    }

    private Runnable loop() {
        return () -> {
            String line;

            while (true) {
                System.out.print("8_BITS_PC -> ");
                line = scanner.nextLine().toLowerCase();
                line = line.replaceAll(" ", "");

                if (line.startsWith("clock")) {
                    clock(line.replace("clock", ""));

                } else if (line.startsWith("counter")) {
                    counter(line.replace("counter", ""));

                } else if (line.startsWith("bus")) {
                    bus(line.replace("bus", ""));

                } else if (line.startsWith("acc")) {
                    register(line.replace("acc", ""), Computer.accumulator);

                } else if (line.startsWith("regb")) {
                    register(line.replace("regb", ""), Computer.bRegister);

                } else if (line.startsWith("state")) {
                    state(line.replace("state", ""));

                } else {
                    general(line);
                }
            }
        };
    }

    private void state(String line) {
        System.out.println(":: Bus ::");
        printByte(Computer.bus.getValue());
        System.out.println("");

        System.out.println(":: Accumulator ::");
        System.out.println("In: " + Computer.accumulator.isInput());
        System.out.println("Out: " + Computer.accumulator.isOutput());
        printByte(Computer.accumulator.getValue());
        System.out.println("");

        System.out.println(":: B Register ::");
        System.out.println("In: " + Computer.bRegister.isInput());
        System.out.println("Out: " + Computer.bRegister.isOutput());
        printByte(Computer.bRegister.getValue());
        System.out.println("");
    }

    private void clock(String line) {
        if (line.startsWith("frequency")) {
            // ---- FREQUENCY ----//
            line = line.replace("frequency", "");

            if (line.isEmpty()) {
                System.out.println(Computer.clock.getFrequency() + "Hz");
            } else {
                Computer.clock.setFrequency(Float.parseFloat(line));
            }

        } else if (line.startsWith("auto")) {
            // ---- AUTO ----//
            line = line.replace("auto", "");

            if (line.isEmpty()) {
                System.out.println(Computer.clock.isAuto());
            } else {
                Computer.clock.setAuto(Boolean.parseBoolean(line));
            }

        } else if (line.startsWith("halt")) {
            // ---- HALT ----//
            line = line.replace("halt", "");

            if (line.isEmpty()) {
                System.out.println(Computer.clock.isHalt());
            } else {
                Computer.clock.setHalt(Boolean.parseBoolean(line));
            }
        } else if (line.startsWith("info")) {
            // ---- INFO ----//
            System.out.println("Auto: " + Computer.clock.isAuto());
            System.out.println("Halt: " + Computer.clock.isHalt());
            System.out.println("Frequency: " + Computer.clock.getFrequency() + "Hz");
        } else {
            Computer.clock.fullCycle();
        }
    }

    private void counter(String line) {
        if (line.startsWith("reset")) {
            Computer.counter.reset();
            return;

        } else if (line.startsWith("jump")) {
            Computer.counter.jump();
            return;

        } else if (line.startsWith("out")) {
            Computer.counter.output();
            return;
        }

        printNibble(Computer.counter.getCounter());
    }

    private void bus(String line) {
        if (line.startsWith("load")) {
            // ---- JUMP ----//
            line = line.replace("load", "");

            if (line.isEmpty()) {
                printByte(Computer.bus.getValue());
            } else {
                // Binary format
                if (line.startsWith("b")) {
                    line = line.replace("b", "");
                    Computer.bus.setValue((byte) (Integer.parseInt(line, 2) & 0xFF));
                } else {
                    // Decimal format
                    Computer.bus.setValue((byte) (Byte.parseByte(line) & 0xFF));
                }
            }
        } else {
            printByte(Computer.bus.getValue());
        }
    }

    private void register(String line, Register register) {

        if (line.startsWith("load")) {
            line = line.replace("load", "");

            if (line.isEmpty()) {
                printByte(register.getValue());
            } else {
                // Binary format
                if (line.startsWith("b")) {
                    line = line.replace("b", "");
                    register.setValue((byte) (Integer.parseInt(line, 2) & 0xFF));
                } else {
                    // Decimal format
                    register.setValue((byte) (Byte.parseByte(line) & 0xFF));
                }
            }
        } else if (line.startsWith("in")) {
            // ---- input ----//
            line = line.replace("in", "");

            if (line.isEmpty()) {
                System.out.println(register.isInput());
            } else {
                register.setInput(Boolean.parseBoolean(line));
            }

        } else if (line.startsWith("out")) {
            // ---- ouput ----//
            line = line.replace("out", "");

            if (line.isEmpty()) {
                System.out.println(register.isOutput());
            } else {
                register.setOutput(Boolean.parseBoolean(line));
            }
        } else {
            printByte(register.getValue());
        }
    }

    private void general(String line) {
        switch (line) {
            case "exit":
                System.exit(0);

            default:
                System.out.println("Comando inválido!");
        }
    }

    private String formatByte(byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    private String formatNibble(byte b) {
        return String.format("%4s", Integer.toBinaryString(b & 0xF)).replace(' ', '0');
    }

    private void printNibble(byte b) {
        System.out.print("Binary: " + formatNibble(b));
        System.out.println(" :: Decimal: " + (b & 0xFF));
    }

    private void printByte(byte b) {
        System.out.print("Binary: " + formatByte(b));
        System.out.println(" :: Decimal: " + (b & 0xFF));
    }
}
