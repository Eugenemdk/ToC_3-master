package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

    Coder c=new Coder();

        String fileName = "C:\\Users\\Eugene\\IdeaProjects\\ToC_3-master\\text.txt";
        String contents = c.readUsingScanner(fileName);
        System.out.println(contents);

        c.launch(contents);
        System.out.println();

        System.out.println("Count Enthropy before encoding for 3 sets ");
      c.countEnthropy(contents,"ref");
      c.countEnthropy(contents,"inc");
      c.countEnthropy(contents,"som");
        System.out.println("Count Enthropy before encoding for 2 sets");
        c.countEnthropy(contents,"do");
        c.countEnthropy(contents,"ho");
        c.countEnthropy(contents,"wh");
    }
}