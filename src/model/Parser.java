package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
    private File file;
    private SymbolTable symbolTable;
    private List<String> inputNoWhitespace;
    private List<String> firstPass; // inputNoWhitespace with no labels
    private List<Code> secondPass; // code

    public Parser(File file) {
        this.file = file;
        symbolTable = new SymbolTable();
        makeInputNoWhitespace();
        doFirstPass(); // get labels
        doSecondPass(); // converts firstPass Strings to Code
    }

    // getters
    public List<Code> getSecondPass() {
        return secondPass;
    }

    // ASSUMES: in-line comments marked by "//" string
    // MODIFIES: this
    // EFFECTS: sets inputNoWhitespace to be input with comments and whitespace removed
    private void makeInputNoWhitespace() {
        inputNoWhitespace = new ArrayList<>();
        String line = "";

        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                line = input.nextLine();
                line = processLine(line);
                if (!line.isEmpty()) { inputNoWhitespace.add(line); }
            }
            input.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }

    // ASSUMES: labels of format (NAME)
    // MODIFIES: this
    // EFFECTS: adds labels to symbolTable with corresponding lineNum
    //          sets firstPass to inputNoWhitespace with no labels
    private void doFirstPass() {
        firstPass = new ArrayList<>();
        int lineNum = 0;

        for (String line : inputNoWhitespace) {
            if (lineIsLabel(line)) {
                symbolTable.add(line.replaceFirst("\\(", "").replaceFirst("\\)", ""), lineNum);
            }
            else {
                firstPass.add(line);
                lineNum++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets secondPass to be a list of Code
    private void doSecondPass() {
        secondPass = new ArrayList<>();
        int lineNum = 0;
        Code c;

        for (String line : firstPass) {
            if (lineIsA(line)) {
                c = new Code(lineNum, line.substring(1), symbolTable);
                secondPass.add(c);
                lineNum++;
            }
            else {
                String[] parsed = parseC(line);
                c = new Code(lineNum, parsed[0], parsed[1], parsed[2]);
                secondPass.add(c);
                lineNum++;
            }
        }
    }


    // EFFECTS: returns true if the first character of a string is '@',
    //          signifying the line is an a-instruction
    private boolean lineIsA(String line) { return line.charAt(0) == '@'; }
    // EFFECTS: returns true if the first character of a string is '@',
    //          signifying the line is an a-instruction
    private boolean lineIsLabel(String line) { return line.charAt(0) == '('; }

    // EFFECTS: returns String array of D, C, J commands from c-instruction.
    //          if instruction does not have one part, returns ""
    private String[] parseC(String line) {
        String[] parsed = new String[3];

        if (line.contains(";")) {
            String[] xj = line.split(";");
            parsed[2] = xj[1].trim();
            line = xj[0];
        } else {
            parsed[2] = "";
        }

        if (line.contains("=")) {
            String[] dc = line.split("=");
            parsed[0] = dc[0].trim();
            parsed[1] = dc[1].trim();
        } else {
            parsed[0] = "";
            parsed[1] = line;
        }

        return parsed;
    }

    private String processLine(String s) {
        s = s.trim(); // remove any whitespace and after command
        s = s.split("//")[0]; // remove any line comment
        s = s.replaceAll("\"|\'", ""); // remove quotations
        return s;
    }

    // EFFECTS: writes binary line by line to new file of given filename
    public void writeBinarytoFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Code c : secondPass) {
            writer.write(c.getBinary());
            writer.newLine();
        }
        writer.close();
    }
}
