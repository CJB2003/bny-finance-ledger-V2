package com.pluralsight;

public class TextFormatter {
    //Made them static and final so I can pass them through multiple methods
    //Formats text bold
    private static final String bold = "\u001B[1m";
    //Resets formatting
    private static final String reset = "\u001B[0m";

    public static String bold(String text) {
        return bold + text + reset;
    }
}
