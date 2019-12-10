package Module;

import Emulator.Computer;
import Model.Register;

/*
 * @author MrCapybara
 */
public class Instruction extends Register {
    
    @Override
    public void setOutput(boolean output) {
        this.output = output;
        
        if (isOutput())
            Computer.bus.setLsb(getValue());
        else
            Computer.bus.clear();
    }
    
    @Override
    public void risingEdge() {
        // Carrega os valores do barramento para este registrador.
        if (isInput())
            setValue(Computer.bus.getValue());

        // Carrega os valores deste registrador para o barramento.
        if (isOutput()) {
            // Envia para o barramento o lsb
            Computer.bus.setLsb(getValue());

            // Envia para o controle o msb
        }
    }
}
