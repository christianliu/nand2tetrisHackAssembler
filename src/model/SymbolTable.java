package model;

import java.util.Arrays;
import java.util.List;

public class SymbolTable {
    private List<String> labels;
    private List<Integer> linenums;

    public SymbolTable() {
        labels = Arrays.asList("R1");
        linenums = Arrays.asList(1);
    }


    // MODIFIES: this
    // EFFECTS: adds a label and corresponding reference lineNum to table
    public void add(String label, int lineNum) {

    }


}
