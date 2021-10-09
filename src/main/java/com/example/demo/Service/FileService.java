package com.example.demo.Service;

import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class FileService {

    public final static String PATH_ROOT_VIDEO = "C:\\Users\\Admin\\Desktop\\video\\";

    public InputStream getInputStream(String pathRelative, Long begin, Long length) throws IOException {
        String path = PATH_ROOT_VIDEO + pathRelative;
        File videoFile = new File(path);
        FileInputStream videoInputStream = new FileInputStream(videoFile);
        byte[] arrayVideo = new byte[(int) length.longValue()];

        videoInputStream.skip(begin);
        videoInputStream.read(arrayVideo);

        return new ByteArrayInputStream(arrayVideo);
    }

    public File getFile(String pathRelative) {
        String path = PATH_ROOT_VIDEO + pathRelative;
        return new File(path);
    }

}
