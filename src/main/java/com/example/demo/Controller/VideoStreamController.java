package com.example.demo.Controller;

import com.example.demo.Model.VideoRange;
import com.example.demo.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController()
@RequestMapping("/stream_video")
public class VideoStreamController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/video/{name}")
    public ResponseEntity<InputStreamResource> getVideo(
            @PathVariable("name") String nameVideo,
            @RequestHeader(value = "range", required = false) String range
    ) throws IOException {
        VideoRange videoRange = videoService.getInformationVideoByNameAndRange(nameVideo, range);
        InputStream videoResult = videoService.getInputStreamVideo(videoRange);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "video/mp4");
        headers.set("connection", "keep-alive");
        headers.set("content-length", String.valueOf(videoRange.lengthRange()));

        if (range != null) {
            headers.set("content-range", String.format("bytes %s-%s/%s", videoRange.getRangeBegin(), videoRange.getRangeEnd(), videoRange.getLengthTotalVideo()));
            return new ResponseEntity<>(new InputStreamResource(videoResult), headers, HttpStatus.PARTIAL_CONTENT);
        } else {
            return new ResponseEntity<>(new InputStreamResource(videoResult), headers, HttpStatus.OK);
        }
    }

}
