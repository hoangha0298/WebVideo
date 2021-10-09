package com.example.demo.Service;

import com.example.demo.Model.Video;
import com.example.demo.Repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public InputStream getInputStreamVideo(Video video) throws IOException {
        return videoRepository.getInputStreamVideo(video);
    }

    public Video getInformationVideoByNameAndRange(String name, String range) {
        return Video.getInstance(name, range);
    }

}
