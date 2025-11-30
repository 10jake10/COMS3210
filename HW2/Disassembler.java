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

                /*
                //Eleven Bit opcodes
                if(opcode11 == 0b10001011000){ 
                    //Add
                    result.add(String.format("ADD X%d, X%d, X%d", rd, rn, rm) );
                }
                    */


                switch(opcode11)
                {
                    //Add
                    case 0b10001011000:
                        result.add(String.format("ADD X%d, X%d, X%d", rd, rn, rm) );
                    break;

                    case 0b11001011000:
                        //sub
                    break;

                    case 0b11101011000:
                        //subs
                    break;

                    case 0b10001010000:
                        // And
                    break;

                    case 0b10101010000:
                        //Or
                    break;

                    case 0b11001010000:
                        //EOr
                    break;

                    case 0b10011011000:
                        //Mul
                    break;

                    case 0b11010011011:
                        //LSL
                    break;

                    case 0b11010011010:
                        //LSR
                    break;

                    case 0b11111111101:
                        //PRNT
                    break;

                    case 0b11111111100:
                        //PRNL
                    break;

                    case 0b11111111110:
                        //DUMP
                    break;

                    case 0b11111111111:
                        //HALT
                    break;

                    case 0b11010110000:
                        //BR
                    break;

                    case 0b11111000010:
                        //LDUR
                    break;

                    case 0b11111000000:
                        //STUR
                    break;
                }
                
                switch(opcode10)
                {
                    case 0b1001000100:
                        //ADDI
                    break;

                    case 0b1001001000:
                        //ANDI
                    break;

                    case 0b1011001000:
                        //ORRI
                    break;

                    case 0b1101001000:
                        //EORI
                    break;

                    case 0b1101000100:
                        //SUBI
                    break;

                    case 0b1111000100:
                        //SUBIS
                    break;
                }

                switch (opcode8) {
                    case 0b10110100:
                        //CBZ    
                    break;

                    case 0b10110101:
                        //CBNZ
                    break;

                    case 0b01010100:
                        //B.cond
                    break;
                }

                switch (opcode6) {
                    case 0b000101:
                        //Branch    
                    break;

                    case 0b100101:
                        //BL
                    break;
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
