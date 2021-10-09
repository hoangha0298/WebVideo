package com.example.demo;

import java.io.File;

public class Test {

    public static void main(String[] args) {
        String url = "C:\\Users\\Admin\\Desktop\\video";
        File root = new File(url);
        for (File file : root.listFiles()) {
            System.out.println("=======================");
            System.out.println(file.getName());
            System.out.println("Absolute " + file.isAbsolute());
            System.out.println("Directory " + file.isDirectory());
            System.out.println("File " + file.isFile());
        }
    }

}
