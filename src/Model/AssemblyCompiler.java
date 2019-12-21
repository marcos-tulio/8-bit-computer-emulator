package Model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MrCapybara
 */
public class AssemblyCompiler {

    /*
        .---------------------------------------. 
        |          Default operations           |
        |---------------------------------------| 
        | NOP | 0000 |  0Xh  | P. Counter++     |
        | LDA | 0001 | (0Xh) | ACC = (XXh)      |
        | LDI | 0010 |  0Xh  | ACC = 0Xh        |  
        | STA | 0011 | (0Xh) | (XXh) = ACC      |     
        | ADD | 0100 | (0Xh) | ACC += (XXh)     |    
        | SUB | 0101 | (0Xh) | ACC -= (XXh)     |  
        | AND | 0110 | (0Xh) | ACC &= (XXh)     |  
        | ORL | 0111 | (0Xh) | ACC |= (XXh)     |   
        | XOR | 1000 | (0Xh) | ACC ^= (XXh)     |  
        | NOT | 1001 |  0Xh  | ACC = /(ACC)     |     
        | JMP | 1010 |  0Xh  | P. counter = XXh |   
        | --- | 1011 |  0Xh  | ---------------- |
        | --- | 1100 |  0Xh  | ---------------- |
        | --- | 1101 |  0Xh  | ---------------- |
        | OUT | 1110 |  0Xh  | OUT = ACC        |
        | HLT | 1111 |  0Xh  | Des. o clock     |
        *---------------------------------------*
     */
    private static final Map<String, Object> OP_CODE = new HashMap() {
        {
            /*   Adicione aqui os opcodes para a compilações (em lowercase).  
                Add here your custom opcodes to compilation (in lower case). */

            put("nop", 0b0000);
            put("lda", 0b0001);
            put("ldi", 0b0010);
            put("sta", 0b0011);
            put("add", 0b0100);
            put("sub", 0b0101);
            put("and", 0b0110);
            put("orl", 0b0111);
            put("xor", 0b1000);
            put("not", 0b1001);
            put("jmp", 0b1010);
            put("out", 0b1110);
            put("hlt", 0b1111);
        }
    };

    public static byte compileLine(String line) throws Exception {
        line = line.toLowerCase();
        line = line.replace("\t", " ");
        String ins = "", value = "0000";

        if (line.startsWith(" "))
            line = line.substring(1);

        // Retirar os comentários
        if (line.contains(";"))
            line = line.substring(0, line.indexOf(";")) + "\n";

        // Identificar a instrução
        if (line.contains(" "))
            ins = line.substring(0, line.indexOf(" "));
        else if (line.contains("\n"))
            ins = line.substring(0, line.indexOf("\n"));
        else
            ins = line;

        // Nenhuma instrução identificada
        if (ins.isEmpty())
            throw new Exception("only comment line");

        // Identificar o valor
        if (line.contains("0x"))
            value = line.substring(ins.length()).replace("0x", "");

        else if (line.contains("h") && !line.contains("hlt"))
            value = line.substring(ins.length(), line.indexOf("h"));

        value = value.replace("\n", "").replace(" ", "");

        return (byte) ((getInstruction(ins) << 4) | (Integer.valueOf(value, 16) & 0x0F));
    }

    public static byte getInstruction(String ins) throws Exception {
        if (ins.equalsIgnoreCase(".org"))
            return 0b0000;
        else
            return Byte.parseByte(OP_CODE.get(ins).toString());
    }
}
