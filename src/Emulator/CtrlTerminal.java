package Emulator;

import Model.AssemblyCompiler;
import Module.Register;
import Screen.TerminalIO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author MrCapybara
 */
public class CtrlTerminal {

    /* Thread para rodar o computador. */
    private Thread threadRun = null;
    private boolean stopRun = false;

    private TerminalIO terminal;
    private String line;

    public CtrlTerminal() {
        this.terminal = Computer.screen.getTerminal();

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
                    terminal.resetCursor();
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

            } else if (line.startsWith("mar")) {
                removeFlag("mar");
                register(Computer.mar);

            } else if (line.startsWith("output")) {
                removeFlag("output");
                register(Computer.output);

            } else if (line.startsWith("acc") || line.startsWith("areg")) {
                removeFlag("areg");
                removeFlag("acc");
                register(Computer.accumulator);

            } else if (line.startsWith("breg")) {
                removeFlag("breg");
                register(Computer.bRegister);

            } else if (line.startsWith("ins")) {
                removeFlag("ins");
                register(Computer.instruction);

            } else if (line.startsWith("alu")) {
                removeFlag("alu");
                alu();

            } else {
                general();
            }
        } catch (Exception e) {
            terminal.outError();
        }

        Computer.refreshParallelBits();
        Computer.screen.refresh();
        terminal.newLine();
    }

    private void removeFlag(String flag) {
        line = line.replace(flag, "");
    }

    private void run() {
        if (threadRun == null || !threadRun.isAlive()) {
            threadRun = new Thread(() -> {
                stopRun = false;

                terminal.outln("Computer running...");

                Computer.control.getClock().setAuto(true);

                while (!stopRun)
                    Computer.control.getClock().fullCycle();

                Computer.control.getClock().setAuto(false);
            });
            threadRun.start();
        } else
            terminal.outln("Computer is already running!");
    }

    private boolean clock() {
        if (line.startsWith("frequency")) {
            removeFlag("frequency");

            if (line.isEmpty())
                terminal.println(Computer.control.getClock().getFrequency() + "Hz");
            else
                Computer.control.getClock().setFrequency(Float.parseFloat(line));

        } else if (line.startsWith("halt")) {
            removeFlag("halt");

            if (line.isEmpty())
                terminal.println(getBit(Computer.control.getClock().isHalt()));
            else
                Computer.control.getClock().setHalt(getBoolInLine());

        } else if (line.startsWith("toggle")) {
            Computer.control.getClock().halfCycle();
            return true;
        } else {
            Computer.control.getClock().fullCycle();
            return true;
        }

        return false;
    }

    private void alu() {
        if (line.startsWith("acc0")) {
            removeFlag("acc0");
            Computer.alu.setAccZero(getBoolInLine());

        } else if (line.startsWith("acc1")) {
            removeFlag("acc1");
            Computer.alu.setAccOne(getBoolInLine());

        } else if (line.startsWith("add")) {
            removeFlag("add_sub");
            removeFlag("addsub");
            removeFlag("add");
            Computer.alu.setAddSub(getBoolInLine());

        } else if (line.startsWith("xor")) {
            removeFlag("xor_not");
            removeFlag("xornot");
            removeFlag("xor");
            Computer.alu.setXorNot(getBoolInLine());

        } else if (line.startsWith("out")) {
            removeFlag("out");
            Computer.alu.setOutput(getBoolInLine());

        } else
            terminal.outError();
    }

    private void register(Register register) {
        if (line.startsWith("load")) {
            removeFlag("load");
            register.setValue(getByteInLine());

        } else if (line.startsWith("in")) {
            removeFlag("in");
            register.setInput(getBoolInLine());

        } else if (line.startsWith("out")) {
            removeFlag("out");
            register.setOutput(getBoolInLine());

        } else if (line.startsWith("reset")) {
            register.reset();

        } else {
            terminal.outError();
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

            case "reset":
                Computer.control.reset();
                break;

            case "repaint":
                Computer.screen.repaint();
                break;

            case "asm":
                asm();
                break;

            case "stop": {
                if (threadRun != null && threadRun.isAlive()) {
                    try {
                        stopRun = true;
                        threadRun.join();
                        terminal.outln("Computer stopped!");
                    } catch (InterruptedException ex) {
                        System.err.println("Thread stop error!");
                    }
                } else
                    terminal.outln("Computer is not running!");
            }
            break;

            default:
                terminal.outError();
        }
    }

    private void counter() {
        if (line.startsWith("load")) {
            removeFlag("load");
            Computer.counter.setValue(getByteInLine());

        } else if (line.startsWith("enable")) {
            removeFlag("enable");
            Computer.counter.setEnable(getBoolInLine());

        } else if (line.startsWith("jump")) {
            removeFlag("jump");
            Computer.counter.setJump(getBoolInLine());

        } else if (line.startsWith("inc")) {
            removeFlag("inc");
            Computer.counter.setIncrement(getBoolInLine());

        } else if (line.startsWith("out")) {
            removeFlag("out");
            Computer.counter.setOutput(getBoolInLine());

        } else if (line.startsWith("reset")) {
            Computer.counter.reset();

        } else {
            terminal.outError();
        }
    }

    private void ram() {
        if (line.startsWith("load")) {
            removeFlag("load");

            if (!line.isEmpty()) {
                if (line.startsWith("addr")) {
                    int addr;

                    if (line.startsWith("addrb")) {
                        removeFlag("addrb");
                        addr = Integer.parseInt(line.substring(0, line.indexOf("val")), 2) & 0xFF;
                    } else {
                        removeFlag("addr");
                        addr = Integer.parseInt(line.substring(0, line.indexOf("val"))) & 0xFF;
                    }

                    line = line.substring(line.indexOf("v") + "val".length());
                    Computer.ram.setContent(addr, getByteInLine());
                } else {
                    if (Computer.ram.isRead()) {
                        terminal.outln("Alert: ram is in read mode!");
                        return;
                    }

                    Computer.ram.setValue(getByteInLine());
                }
            } else {// Vazio
                terminal.outError();
            }
        } else if (line.startsWith("val")) {
            removeFlag("value");
            removeFlag("val");
            Computer.ram.setValue(getByteInLine());

        } else if (line.startsWith("in")) {
            removeFlag("in");
            Computer.ram.setInput(getBoolInLine());

        } else if (line.startsWith("reset") || line.startsWith("clear")) {
            removeFlag("reset");
            removeFlag("clear");
            Computer.ram.clear();

        } else if (line.startsWith("out")) {
            removeFlag("out");
            Computer.ram.setOutput(getBoolInLine());

        } else if (line.startsWith("write")) {
            removeFlag("write");
            Computer.ram.setWriteRead(false);

        } else if (line.startsWith("read")) {
            removeFlag("read");
            Computer.ram.setWriteRead(true);

        } else if (line.startsWith("wr")) {
            removeFlag("wr");
            Computer.ram.setWriteRead(getBoolInLine());
        } else {
            terminal.outError();
        }
    }

    private byte getByteInLine() {
        if (line.isEmpty())
            throw new NullPointerException();

        if (line.startsWith("b")) {
            removeFlag("b");
            return (byte) (Integer.parseInt(line, 2) & 0xFF);
        } else
            return (byte) (Integer.parseInt(line) & 0xFF);
    }

    private boolean getBoolInLine() {
        if (line.isEmpty())
            throw new NullPointerException();

        return line.equalsIgnoreCase("true") || line.equals("1");
    }

    private String getBit(boolean b) {
        if (b)
            return "1";

        return "0";
    }

    private void asm() {
        File file = Computer.screen.chooseFile();

        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                Computer.ram.clear(); // Limpar a memória
                
                String fileLine;
                int count = 1, compiled = 0;
                
                while ((fileLine = br.readLine()) != null) {
                    try {
                        if (fileLine.contains(".org"))
                            compiled = AssemblyCompiler.compileLine(fileLine);
                        else if (fileLine.contains(".end")) {
                            terminal.outln("Line " + count + " compiled!");
                            break;
                        } else {
                            if (compiled > Computer.ram.getSize()) {
                                terminal.outError("Maximum RAM size exceeded!");
                                break;
                            }

                            Computer.ram.setContent(compiled, AssemblyCompiler.compileLine(fileLine));
                            compiled++;
                        }

                        terminal.outln("Line " + count + " compiled!");
                    } catch (Exception ex) {
                        // Pular os comentários
                        if (!ex.getMessage().isEmpty())
                            continue;

                        terminal.outError("Line " + count + " not compiled!");
                    }

                    count++;
                }
            } catch (IOException ex) {
                terminal.outError("File import failed!");
            }
        }
    }
}
