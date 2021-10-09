package com.example.demo.Repository;

import com.example.demo.Model.Video;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class VideoRepository {

    public final static String pathRootVideo = "C:\\Users\\Admin\\Desktop\\video\\";

    public InputStream getInputStreamVideo(Video video) throws IOException {
        String path = pathRootVideo + video.getName();
        File videoFile = new File(path);
        FileInputStream videoInputStream = new FileInputStream(videoFile);
        byte[] arrayVideo = new byte[(int) video.lengthRange()];

        videoInputStream.skip(video.getRangeBegin());
        videoInputStream.read(arrayVideo);

        return new ByteArrayInputStream(arrayVideo);
    }

    // trả về độ dài là byte
    public Long getLengthVideo(String name) {
        String path = pathRootVideo + name;
        File video = new File(path);
        return video.length();
    }

}
