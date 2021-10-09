package com.example.demo;

import com.example.demo.Model.FolderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Test {

    public static void main(String[] args) throws JsonProcessingException {
//        String url = "C:\\Users\\Admin\\Desktop\\video\\";
//        File root = new File(url);
//        for (File file : root.listFiles()) {
//            System.out.println("=======================");
//            System.out.println(file.getName());
//            System.out.println("Absolute " + file.isAbsolute());
//            System.out.println("Directory " + file.isDirectory());
//            System.out.println("File " + file.isFile());
//        }
        FolderDTO root = FolderDTO.getFolderRoot();
        System.out.println(root);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(root);
        System.out.println(json);
    }

}
