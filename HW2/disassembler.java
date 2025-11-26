import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Disassembler {

    public static void main(String[] args) {
        int opcode11;
        int opcode10;
        int opcode6;
        int opcode8;
        int rm;
        int rn;
        int rd; // rd doubles as rt
        int shamt;
        int alu_i;
        int dt_a;
        int op;
        int br_a;
        int cond_br_a;
        int mov_i;

        int binary;
        // binary = 0b11001101110011000011011000100101;

        List<String> result = new ArrayList<String>();
        int count = 0;

        try (Scanner scanner = new Scanner(new File("test.txt"))) {

            while (scanner.hasNext()) {
                count++;

                String binaryString = scanner.next();
                binary = Integer.parseUnsignedInt(binaryString, 2);
                // System.out.println(Integer.toBinaryString(binary));

                opcode11 = binary >>> 21;
                opcode10 = binary >>> 22;
                opcode6 = binary >>> 26;
                opcode8 = binary >>> 24;
                rm = (binary >>> 16) & 0x1F;
                rn = (binary >>> 5) & 0x1F;
                rd = binary & 0x1F;
                shamt = (binary >>> 10) & 0x3F;
                alu_i = (binary >>> 10) & 0xFFF;
                dt_a = (binary >>> 12) & 0x1FF;
                op = (binary >>> 10) & 0x3;
                br_a = binary & 0x3FFFFFF;
                cond_br_a = (binary >>> 5) & 0x7FFFF;
                mov_i = (binary >>> 5) & 0xFFFF;
                // System.out.println(Integer.toBinaryString(rm));

                if(opcode11 == 0b10001011000){ 
                    result.add(String.format("ADD X%d, X%d, X%d", rd, rn, rm) );
                }

                
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        
        for(String s : result)
        {
            System.out.println(s);
        }

    }

}
