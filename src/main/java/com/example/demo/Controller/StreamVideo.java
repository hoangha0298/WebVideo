package com.example.demo.Controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController()
@RequestMapping("/stream_video")
public class StreamVideo {

    @GetMapping("/video/{name}")
    public ResponseEntity<InputStreamResource> getVideo(
            @PathVariable("name") String nameVideo,
            @RequestHeader(value = "Range", required = false) String range) throws IOException {
        String url = "C:\\Users\\Admin\\Desktop\\View_From_A_Blue_Moon_Trailer-1080p.mp4";
        File video = new File(url);
        FileInputStream videoStream = new FileInputStream(video);

        // vị trí byte bắt đầu và kết thúc gửi tới client
        long rangeBegin = 0;
        long rangeEnd = video.length() - 1;

        if (range != null) {
            String[] ranges = range.replace("bytes=", "").split("-");
            rangeBegin = Long.valueOf(ranges[0]);
            if (ranges.length == 2) {
                rangeEnd = Long.valueOf(ranges[1]);
            }
        }

        byte[] videoArray = new byte[(int) (rangeEnd - rangeBegin + 1)];
        videoStream.skip(rangeBegin);
        videoStream.read(videoArray);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "video/mp4");
        headers.set("connection", "keep-alive");
        headers.set("content-length", String.valueOf(videoArray.length));

        if (range == null){
            return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(videoArray)), headers, HttpStatus.OK);
        } else {
            headers.set("content-range", String.format("bytes %s-%s/%s", rangeBegin, rangeEnd, video.length()));
            return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(videoArray)), headers, HttpStatus.PARTIAL_CONTENT);
        }

    }

}
