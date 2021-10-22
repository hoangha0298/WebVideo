package com.example.demo.Service;

import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class FileService {

    public InputStream getInputStream(String path, Long begin, Long length) throws IOException {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] array = new byte[(int) length.longValue()];
        fileInputStream.skip(begin);
        fileInputStream.read(array);

        return new ByteArrayInputStream(array);
    }

    public File getFile(String path) {
        return new File(path);
    }

}
