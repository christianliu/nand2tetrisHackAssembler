package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
    private File file;
    private SymbolTable symbolTable;
    private List<String> symbolicWithLabels;
    private List<Code> symbolicParsed;

    public Parser(File file) {
        this.file = file;
        makeSymbolicWithLabels();
        makeSymbolicParsed();
    }

    // getters
    public List<Code> getSymbolicParsed() {
        return symbolicParsed;
    }

    // MODIFIES: this
    // EFFECTS: sets symbolicParsed to be a list of Code
    private void makeSymbolicParsed() {
        symbolicParsed = new ArrayList<>();
        int lineNum = 0;
        Code c;

        for (String line : symbolicWithLabels) {
            if (lineIsLabel(line)) {
                symbolTable.add(line.replaceFirst("(", "").replaceFirst(")", ""), lineNum);
            } else if (lineIsA(line)) {
                c = new Code(lineNum, line.substring(1));
                symbolicParsed.add(c);
                lineNum++;
            }
            else {
                String[] parsed = parseC(line);
                c = new Code(lineNum, parsed[0], parsed[1], parsed[2]);
                symbolicParsed.add(c);
                lineNum++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets symbolicWithLabels to be a list of Strings of symbolic code
    private void makeSymbolicWithLabels() {
        symbolicWithLabels = new ArrayList<>();
        Scanner input;
        String line = "";

        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                line = input.nextLine();
                line = processLine(line);
                if (!line.isEmpty()) { symbolicWithLabels.add(line); }
            }
            input.close();

        } catch (FileNotFoundException e) { e.printStackTrace(); }
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
        s = s.trim(); // remove any whitespace before command
        s = s.split("//")[0]; // remove any line comment
        s = s.replaceAll("\"|\'", ""); // remove quotations
        return s;
    }

    // EFFECTS: writes binary line by line to new file of given filename
    public void writeBinarytoFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Code c : symbolicParsed) {
            writer.write(c.getBinary());
            writer.newLine();
        }
        writer.close();
    }
}
