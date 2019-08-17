package com;

import com.parser.Parse;
import com.print.Printer;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Parse parse = new Parse();
        Printer printer = new Printer(parse.getBooks());
        printer.exec();
    }
}