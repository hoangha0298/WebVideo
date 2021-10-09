package com.example.demo.Repository;

import com.example.demo.Model.VideoRange;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class VideoRepository {

    public final static String PATH_ROOT_VIDEO = "C:\\Users\\Admin\\Desktop\\video\\";

    public InputStream getInputStreamVideo(VideoRange videoRange) throws IOException {
        String path = PATH_ROOT_VIDEO + videoRange.getName();
        File videoFile = new File(path);
        FileInputStream videoInputStream = new FileInputStream(videoFile);
        byte[] arrayVideo = new byte[(int) videoRange.lengthRange()];

        videoInputStream.skip(videoRange.getRangeBegin());
        videoInputStream.read(arrayVideo);

        return new ByteArrayInputStream(arrayVideo);
    }

    // trả về độ dài là byte
    public Long getLengthVideo(String name) {
        String path = PATH_ROOT_VIDEO + name;
        File video = new File(path);
        return video.length();
    }

}
