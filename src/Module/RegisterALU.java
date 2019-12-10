package Module;

import Emulator.Computer;
import Model.Register;

/*
 * @author MrCapybara
 */
public class RegisterALU extends Register {

    // Os demais registradores atualizam a ALU
    @Override
    public void setValue(byte value) {
        super.setValue(value);
        Computer.alu.refresh();
    }

    @Override
    public void risingEdge() {
        // Carrega os valores do barramento para este registrador.
        if (isInput())
            setValue(Computer.bus.getValue());

        // Carrega os valores deste registrador para o barramento.
        if (isOutput())
            Computer.bus.setValue(getValue());
    }
}
