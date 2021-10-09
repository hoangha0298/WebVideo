package com.example.demo.Service;

import com.example.demo.Model.VideoRange;
import com.example.demo.Repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public InputStream getInputStreamVideo(VideoRange videoRange) throws IOException {
        return videoRepository.getInputStreamVideo(videoRange);
    }

    public VideoRange getInformationVideoByNameAndRange(String name, String range) {
        return VideoRange.getInstance(name, range);
    }

}
