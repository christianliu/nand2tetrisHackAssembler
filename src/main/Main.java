package main;

import model.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String folder = "/Users/christianliu/Dropbox/OSSU Files/nand2tetris/projects/06/";
        List<String> fileNames = Arrays.asList("add/Add", "max/MaxL", "rect/RectL", "pong/PongL",
                "max/Max", "rect/Rect", "pong/Pong");

        File f;
        Parser p;
        for (String name : fileNames) {
            f = new File(folder + name + ".asm");
            p = new Parser(f);
            p.writeBinarytoFile("../" + name + ".hack");
        }

    }

}
