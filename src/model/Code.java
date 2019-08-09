package model;

public class Code {
    private boolean isC; // is a- or c-instruction
    private String aAddr;
    private String cDest;
    private String cComp;
    private String cJump;

    private int lineNum; // instruction line number excluding whitespace and labels
    private String binary; // converted binary code of size 16

    // constructor for a-instruction
    public Code(int lineNum, String aAddr) {
        isC = false;
        this.lineNum = lineNum;
        this.aAddr = aAddr;
        binary = convertA();
    }
    // constructor for c-instruction
    public Code(int lineNum, String cDest, String cComp, String cJump) {
        isC = true;
        this.lineNum = lineNum;
        this.cDest = cDest;
        this.cComp = cComp;
        this.cJump = cJump;
        binary = convertC();
    }

    // getters
    public boolean getIsC() { return isC; }
    public int getLineNum() { return lineNum; }
    public String getBinary() { return binary; }

    private String convertA() {
        int n = Integer.parseInt(aAddr);
        return "0" + intToBinary15(n);
    }

    private String convertC() {
        return "111" + convertCC() + convertCD() + convertCJ();
    }

    private String convertCC() {
        String binary = "";
        boolean isM = cComp.contains("M");
        String cCompGeneric = cComp.replace('M', 'A');
        switch (cCompGeneric) {
            case "0":
                binary = "101010";
                break;
            case "1":
                binary = "111111";
                break;
            case "-1":
                binary = "111010";
                break;
            case "D":
                binary = "001100";
                break;
            case "A":
                binary = "110000";
                break;
            case "!D":
                binary = "001101";
                break;
            case "!A":
                binary = "110001";
                break;
            case "-D":
                binary = "001111";
                break;
            case "-A":
                binary = "110011";
                break;
            case "D+1":
                binary = "011111";
                break;
            case "A+1":
                binary = "110111";
                break;
            case "D-1":
                binary = "001110";
                break;
            case "A-1":
                binary = "110010";
                break;
            case "D+A":
                binary = "000010";
                break;
            case "D-A":
                binary = "010011";
                break;
            case "A-D":
                binary = "000111";
                break;
            case "D&A":
                binary = "000000";
                break;
            case "D|A":
                binary = "010101";
                break;
        }
        return (isM ? "1" : "0") + binary;
    }

    private String convertCD() {
        return (cDest.contains("A") ? "1" : "0") +
                (cDest.contains("D") ? "1" : "0") +
                (cDest.contains("M") ? "1" : "0");
    }

    private String convertCJ() {
        String binary = "";
        switch (cJump) {
            case "":
                binary = "000";
                break;
            case "JGT":
                binary = "001";
                break;
            case "JEQ":
                binary = "010";
                break;
            case "JGE":
                binary = "011";
                break;
            case "JLT":
                binary = "100";
                break;
            case "JNE":
                binary = "101";
                break;
            case "JLE":
                binary = "110";
                break;
            case "JMP":
                binary = "111";
                break;
        }
        return binary;
    }

    // EFFECTS: returns (n mod 2^15=32768) in binary notation
    private String intToBinary15(int n) {
        String binary = "";
        for (int i = 0; i < 15; i++) {
            binary += (n % (int) Math.pow(2, 15 - i)) / (int) Math.pow(2, 14 - i);
        }
        return binary;
    }
}
