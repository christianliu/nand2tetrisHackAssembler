package main;

import model.Parser;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File f1 = new File("/Users/christianliu/Dropbox/OSSU Files/nand2tetris/projects/06/add/Add.asm");
        Parser p1 = new Parser(f1);
        p1.writeBinarytoFile("../add/Add.hack");

        File f2 = new File("/Users/christianliu/Dropbox/OSSU Files/nand2tetris/projects/06/max/MaxL.asm");
        Parser p2 = new Parser(f2);
        p2.writeBinarytoFile("../max/MaxL.hack");

        File f3 = new File("/Users/christianliu/Dropbox/OSSU Files/nand2tetris/projects/06/rect/RectL.asm");
        Parser p3 = new Parser(f3);
        p3.writeBinarytoFile("../rect/RectL.hack");

        File f4 = new File("/Users/christianliu/Dropbox/OSSU Files/nand2tetris/projects/06/pong/PongL.asm");
        Parser p4 = new Parser(f4);
        p4.writeBinarytoFile("../pong/PongL.hack");


//        Code c = new Code(398, "AD", "D+1", "JMP");
//        System.out.println(c.getBinary());
    }




}
