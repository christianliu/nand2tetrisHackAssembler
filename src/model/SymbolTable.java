package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SymbolTable {
    private List<String> labels;
    private List<Integer> lineNums;
    private int nextAvail;
    static private int screen = 16384;
    static private int kbd = 24576;

    public SymbolTable() {
        labels = new ArrayList<>(Arrays.asList("R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10",
                "R11", "R12", "R13", "R14", "R15", "SCREEN", "KBD", "SP", "LCL", "ARG", "THIS",
                "THAT"));
        lineNums = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                screen, kbd, 0, 1, 2, 3, 4));
        nextAvail = 16;
    }

    // getters
    public int getNextAvail() { return nextAvail; }

    // MODIFIES: this
    // EFFECTS: increments placeholder of next available space in memory
    public void incrNextAvail() { nextAvail++; }

    // MODIFIES: this
    // EFFECTS: adds a label and corresponding reference lineNum to table
    public void add(String label, int lineNum) {
        labels.add(label);
        lineNums.add(lineNum);
    }

    // EFFECTS: true if labels contains label
    public boolean contains(String label) {
        return labels.contains(label);
    }

    // EFFECTS: returns value associated with given label
    public int getVal(String label) {
        return lineNums.get(labels.indexOf(label));
    }
}
