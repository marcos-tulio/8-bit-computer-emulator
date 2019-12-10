package Emulator;

import Model.Register;
import Screen.TerminalIO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author MrCapybara
 */
public class CtrlTerminal {

    private TerminalIO terminal;
    private String line;

    public void setInput(TerminalIO terminal) {
        this.terminal = terminal;

        terminal.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_L) && e.isControlDown()) {
                    terminal.clear();
                    terminal.newLine();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    terminal.setCaretPosition(terminal.getDocument().getLength());
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    execCommand();
                }
            }
        });
    }

    public void execCommand() {
        line = terminal.nextLine().replaceAll(" ", "");

        try {
            if (line.startsWith("clock")) {
                removeFlag("clock");

                //Retorna true se já atualizou a tela. 
                if (clock()) {
                    terminal.newLine();
                    return;
                }
            } else if (line.startsWith("run")) {
                run();

            } else if (line.startsWith("ram")) {
                removeFlag("ram");
                ram();

            } else if (line.startsWith("counter")) {
                removeFlag("counter");
                counter();

            } else if (line.startsWith("acc")) {
                removeFlag("acc");
                register(Computer.accumulator);

            } else if (line.startsWith("areg")) {
                removeFlag("areg");
                register(Computer.accumulator);

            } else if (line.startsWith("breg")) {
                removeFlag("breg");
                register(Computer.bRegister);

            } else if (line.startsWith("ins")) {
                removeFlag("ins");
                register(Computer.instruction);

            } else if (line.startsWith("bus")) {
                removeFlag("bus");
                bus();

            } else if (line.startsWith("alu")) {
                removeFlag("alu");
                alu();

            } else if (line.startsWith("state")) {
                removeFlag("state");
                state();

            } else {
                general();
            }
        } catch (Exception e) {
            terminal.printError();
        }
        Computer.screen.refresh();
        terminal.newLine();
    }

    private void removeFlag(String flag) {
        line = line.replace(flag, "");
    }

    private void run() {
        new Thread(() -> {
            while (true) {
                Computer.clock.fullCycle();
                Computer.clock.setAuto(true);
            }
        }).start();
    }

    private void state() {
        /*terminal.println("");
        terminal.println("::::::::::::: Bus ::::::::::::::");
        terminal.print("  ");
        printByte(Computer.bus.getValue());
        terminal.println("::::::::::::::::::::::::::::::::\n");

        terminal.println(":::::::: Program Counter :::::::");
        terminal.println("     In: " + getBit(Computer.counter.isInput()));
        terminal.println("    Out: " + getBit(Computer.counter.isOutput()));
        terminal.println(" Enable: " + getBit(Computer.counter.isEnable()));
        terminal.print("  ");
        printNibble(Computer.counter.getValue());
        terminal.println("::::::::::::::::::::::::::::::::\n");

        terminal.println("::::::::: Accumulator ::::::::::");
        terminal.println("     In: " + getBit(Computer.accumulator.isInput()));
        terminal.println("    Out: " + getBit(Computer.accumulator.isOutput()));
        terminal.print("  ");
        printByte(Computer.accumulator.getValue());
        terminal.println("::::::::::::::::::::::::::::::::\n");

        terminal.println(":::::::::: B Register ::::::::::");
        terminal.println("     In: " + getBit(Computer.bRegister.isInput()));
        terminal.println("    Out: " + getBit(Computer.bRegister.isOutput()));
        terminal.print("  ");
        printByte(Computer.bRegister.getValue());
        terminal.println("::::::::::::::::::::::::::::::::\n");

        terminal.println("::::::::::::: ALU ::::::::::::::");
        terminal.println("    Out: " + getBit(Computer.alu.getOutput()));
        terminal.println("  Acc 0: " + getBit(Computer.alu.getAccZero()));
        terminal.println("  Acc 1: " + getBit(Computer.alu.getAccOne()));
        terminal.println("Add_Sub: " + getBit(Computer.alu.getAddSub()));
        terminal.println("Xor_Not: " + getBit(Computer.alu.getXorNot()));
        terminal.print("  ");
        printByte(Computer.alu.getValue());
        terminal.println("::::::::::::::::::::::::::::::::\n");*/
    }

    private boolean clock() {
        if (line.startsWith("frequency")) {
            // ---- FREQUENCY ----//
            line = line.replace("frequency", "");

            if (line.isEmpty()) {
                terminal.println(Computer.clock.getFrequency() + "Hz");
            } else {
                Computer.clock.setFrequency(Float.parseFloat(line));
            }

        } else if (line.startsWith("auto")) {
            line = line.replace("auto", "");

            if (line.isEmpty())
                terminal.println(getBit(Computer.clock.isAuto()));
            else
                Computer.clock.setAuto(toBool(line));

        } else if (line.startsWith("halt")) {
            line = line.replace("halt", "");

            if (line.isEmpty())
                terminal.println(getBit(Computer.clock.isHalt()));
            else
                Computer.clock.setHalt(toBool(line));

        } else if (line.startsWith("info")) {
            // ---- INFO ----//
            terminal.println("Auto: " + getBit(Computer.clock.isAuto()));
            terminal.println("Halt: " + getBit(Computer.clock.isHalt()));
            terminal.println("Frequency: " + Computer.clock.getFrequency() + "Hz");

        } else if (line.startsWith("toggle")) {
            Computer.clock.halfCycle();
            return true;
        } else {
            Computer.clock.fullCycle();
            return true;
        }

        return false;
    }

    private void alu() {
        if (line.startsWith("acc0")) {
            line = line.replace("acc0", "");

            if (line.isEmpty())
                terminal.println(getBit(Computer.alu.getAccZero()));
            else
                Computer.alu.setAccZero(toBool(line));
        } else if (line.startsWith("acc1")) {
            line = line.replace("acc1", "");

            if (line.isEmpty())
                terminal.println(getBit(Computer.alu.getAccOne()));
            else
                Computer.alu.setAccOne(toBool(line));
        } else if (line.startsWith("add_sub")) {
            line = line.replace("add_sub", "");

            if (line.isEmpty())
                terminal.println(getBit(Computer.alu.getAddSub()));
            else
                Computer.alu.setAddSub(toBool(line));
        } else if (line.startsWith("xor_not")) {
            line = line.replace("xor_not", "");

            if (line.isEmpty())
                terminal.println(getBit(Computer.alu.getXorNot()));
            else
                Computer.alu.setXorNot(toBool(line));

        } else if (line.startsWith("out")) {
            // ---- ouput ----//
            line = line.replace("out", "");

            if (line.isEmpty()) {
                terminal.println(getBit(Computer.alu.getOutput()));
            } else {
                Computer.alu.setOutput(toBool(line));
            }

        } else
            printByte(Computer.alu.getValue());
    }

    private void bus() {
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
                    Computer.bus.setValue((byte) (Integer.parseInt(line) & 0xFF));
                }
            }
        } else {
            printByte(Computer.bus.getValue());
        }
    }

    private void register(Register register) {
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
                    register.setValue((byte) (Integer.parseInt(line) & 0xFF));
                }
            }
        } else if (line.startsWith("in")) {
            // ---- input ----//
            line = line.replace("in", "");

            if (line.isEmpty()) {
                terminal.println(getBit(register.isInput()));
            } else {
                register.setInput(toBool(line));
            }

        } else if (line.startsWith("out")) {
            // ---- ouput ----//
            line = line.replace("out", "");

            if (line.isEmpty()) {
                terminal.println(getBit(register.isOutput()));
            } else {
                register.setOutput(toBool(line));
            }

        } else if (line.startsWith("reset")) {
            register.reset();

        } else {
            printByte(register.getValue());
        }
    }

    private void general() {
        switch (line) {
            case "exit":
                System.exit(0);
                break;

            case "":
                break;

            case "clear":
                terminal.clear();
                break;

            default:
                terminal.println("Comando inválido!");
        }
    }

    private String formatByte(byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    private String formatNibble(byte b) {
        return String.format("%4s", Integer.toBinaryString(b & 0xF)).replace(' ', '0');
    }

    private void printNibble(byte b) {
        terminal.print("Value: " + formatNibble(b));
        terminal.println("(2) :: " + (b & 0xFF) + "(10)");
    }

    private void printByte(byte b) {
        terminal.print("Value: " + formatByte(b));
        terminal.println("(2) :: " + (b & 0xFF) + "(10)");
    }

    private String getBit(boolean b) {
        if (b)
            return "1";

        return "0";
    }

    private boolean toBool(String str) {
        return str.equalsIgnoreCase("true") || str.equals("1");
    }

    private void counter() {
        if (line.startsWith("load")) {
            removeFlag("load");

            if (line.isEmpty()) {
                printNibble(Computer.counter.getValue());
            } else {
                // Binary format
                if (line.startsWith("b")) {
                    line = line.replace("b", "");
                    Computer.counter.setValue((byte) (Integer.parseInt(line, 2) & 0xFF));
                } else {
                    // Decimal format
                    Computer.counter.setValue((byte) (Integer.parseInt(line) & 0xFF));
                }
            }
        } else if (line.startsWith("jump")) {
            removeFlag("jump");

            if (line.isEmpty()) {
                terminal.println(getBit(Computer.counter.isJump()));
            } else {
                Computer.counter.setJump(toBool(line));
            }

        } else if (line.startsWith("inc")) {
            removeFlag("inc");

            if (line.isEmpty())
                terminal.println(getBit(Computer.counter.isIncrement()));
            else
                Computer.counter.setIncrement(toBool(line));

        } else if (line.startsWith("out")) {
            removeFlag("out");

            if (line.isEmpty()) {
                terminal.println(getBit(Computer.counter.isOutput()));
            } else {
                Computer.counter.setOutput(toBool(line));
            }

        } else if (line.startsWith("reset")) {
            Computer.counter.reset();

        } else {
            printNibble(Computer.counter.getValue());
        }
    }

    private void ram() {
        if (line.startsWith("load")) {
            removeFlag("load");

            if (!line.isEmpty()) {
                int addr;
                byte value;

                if (line.startsWith("addr")) {
                    if (line.startsWith("addrb")) {
                        removeFlag("addrb");
                        addr = Integer.parseInt(line.substring(0, line.indexOf("val")), 2) & 0xFF;
                    } else {
                        removeFlag("addr");
                        addr = Integer.parseInt(line.substring(0, line.indexOf("val"))) & 0xFF;
                    }

                    line = line.substring(line.indexOf("v") + "val".length());

                    if (line.startsWith("b")) {
                        removeFlag("b");
                        value = (byte) (Integer.parseInt(line, 2) & 0xFF);
                    } else
                        value = (byte) (Integer.parseInt(line) & 0xFF);

                    Computer.ram.setValue(addr, value);
                } else
                    terminal.printError();

                /*terminal.print("Command invalid!");

                // Binary format
                if (line.startsWith("b")) {
                    line = line.replace("b", "");
                    Computer.counter.setValue((byte) (Integer.parseInt(line, 2) & 0xFF));
                } else {
                    // Decimal format
                    Computer.counter.setValue((byte) (Integer.parseInt(line) & 0xFF));
                }*/
            }
        }
    }
}
