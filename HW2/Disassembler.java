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


                //Reference sheet cause I cant remember a thing
                /*https://booksite.elsevier.com/9780128017333/content/Green%20Card.pdf */

               // Most R types: OP Xd, Xn, Xm
                switch(opcode11)
                {
                    //Add
                    case 0b10001011000:
                        result.add(String.format("ADD X%d, X%d, X%d", rd, rn, rm) );
                    break;

                    case 0b11001011000:
                        //sub
                        result.add(String.format("SUB X%d, X%d, X%d", rd, rn, rm));
                    break;

                    case 0b11101011000:
                        //subs
                        result.add(String.format("SUBS X%d, X%d, X%d", rd, rn, rm));
                    break;

                    case 0b10001010000:
                        // And
                        result.add(String.format("AND X%d, X%d, X%d", rd, rn, rm));
                    break;

                    case 0b10101010000:
                        //Or
                        result.add(String.format("ORR X%d, X%d, X%d", rd, rn, rm));
                    break;

                    case 0b11001010000:
                        //EOr
                        result.add(String.format("EOR X%d, X%d, X%d", rd, rn, rm));
                    break;

                    case 0b10011011000:
                        //Mul
                        result.add(String.format("MUL X%d, X%d, X%d", rd, rn, rm));
                    break;

                    case 0b11010011011:
                        //LSL
                        result.add(String.format("LSL X%d, X%d, #%d", rd, rn, shamt));
                    break;

                    case 0b11010011010:
                        //LSR
                        result.add(String.format("LSR X%d, X%d, #%d", rd, rn, shamt));
                    break;

                    case 0b11111111101:
                        //PRNT
                        result.add(String.format("PRNT X%d", rd));
                    break;

                    case 0b11111111100:
                        //PRNL
                        result.add("PRNL");
                    break;

                    case 0b11111111110:
                        //DUMP
                        result.add(String.format("DUMP"));
                    break;

                    case 0b11111111111:
                        //HALT
                        result.add(String.format("HALT"));
                    break;

                    case 0b11010110000:
                        //BR
                        result.add(String.format("BR X%d", rn));
                    break;

                    case 0b11111000010: {
                        //LDUR
                        int offsetLD = signExtend(dt_a, 9);
                        result.add(String.format("LDUR X%d, [X%d, #%d]", rd, rn, offsetLD));
                    break;
                    }

                    case 0b11111000000: {
                        //STUR
                        int offsetST = signExtend(dt_a, 9);
                        result.add(String.format("STUR X%d, [X%d, #%d]", rd, rn, offsetST));
                    break;
                    }
                }
                
                switch(opcode10)
                {
                    case 0b1001000100: {
                        //ADDI
                        int imm12 = signExtend(alu_i, 12);
                        result.add(String.format("ADDI X%d, X%d, #%d", rd, rn, imm12));
                    break; 
                }

                    case 0b1001001000:{
                        //ANDI
                        result.add(String.format("ANDI X%d, X%d, #%d", rd, rn, alu_i));
                    break;
                    }


                    case 0b1011001000: {
                        //ORRI
                        result.add(String.format("ORRI X%d, X%d, #%d", rd, rn, alu_i));
                    break;
                    }

                    case 0b1101001000: {
                        //EORI
                        result.add(String.format("EORI X%d, X%d, #%d", rd, rn, alu_i));
                    break;
                    }

                    case 0b1101000100: {
                        //SUBI
                        int imm12 = signExtend(alu_i, 12);
                        result.add(String.format("SUBI X%d, X%d, #%d", rd, rn, imm12));
                    break;
                    }

                    case 0b1111000100: {
                        //SUBIS
                        int imm12 = signExtend(alu_i, 12);
                        result.add(String.format("SUBIS X%d, X%d, #%d", rd, rn, imm12));
                    break;
                    }
                }

                switch (opcode8) {
                    case 0b10110100: {
                        //CBZ
                        int offset = signExtend(cond_br_a, 19);
                        result.add(String.format("CBZ X%d, #%d", rd, offset));
                    break;
                    }

                    case 0b10110101: {
                        //CBNZ
                        int offset = signExtend(cond_br_a, 19);
                        result.add(String.format("CBNZ X%d, #%d", rd, offset));
                    break;
                    }

                    case 0b01010100: {
                        //B.cond
                        int offset = signExtend(cond_br_a, 19);
                            String[] condNames = {
                                "EQ","NE","HS","LO","MI","PL","VS","VC",
                                "HI","LS","GE","LT","GT","LE"
                            };
                        String cond = condNames[rd]; //rd is the index of the sign
                        result.add(String.format("B.%s #%d", cond, offset));
                    break;
                    }
                }

                switch (opcode6) {
                    case 0b000101: {
                        //Branch    
                        int offset = signExtend(br_a, 26);
                        result.add(String.format("B #%d", offset));
                    break;
                    }

                    case 0b100101: {
                        //BL
                        int offset = signExtend(br_a, 26);
                        result.add(String.format("BL #%d", offset));
                    break;
                    }
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
    
    private static int signExtend(int value, int bits) 
    {
        int shift = 32 - bits;
        return (value << shift) >> shift;
    }


}
