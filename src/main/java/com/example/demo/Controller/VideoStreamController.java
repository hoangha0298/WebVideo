package com.example.demo.Controller;

import com.example.demo.Model.DTO.FolderDTO;
import com.example.demo.Model.DTO.ResponseDTO;
import com.example.demo.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController()
@RequestMapping("/video_service")
public class VideoStreamController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/video")
    public ResponseEntity<InputStreamResource> getVideo(
            String pathRelative,
            @RequestHeader(value = "range", required = false) String range
    ) throws IOException {
        VideoService.VideoRange videoRange = videoService.getVideoRange(pathRelative, range);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "video/mp4");
        headers.set("connection", "keep-alive");
        headers.set("content-length", String.valueOf(videoRange.lengthRange()));

        if (range != null) {
            headers.set("content-range", String.format("bytes %s-%s/%s", videoRange.getRangeBegin(), videoRange.getRangeEnd(), videoRange.getLengthTotalVideo()));
            return new ResponseEntity<>(new InputStreamResource(videoRange.getRangeStream()), headers, HttpStatus.PARTIAL_CONTENT);
        } else {
            return new ResponseEntity<>(new InputStreamResource(videoRange.getRangeStream()), headers, HttpStatus.OK);
        }
    }

    @GetMapping("/image")
    public ResponseEntity<InputStreamResource> getImage(
            String pathRelative
    ) throws Exception {
//        File image = videoService.getImage(pathRelative);
        byte[] image = videoService.getImageFromVideo(pathRelative);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "image/gif");
        headers.set("accept-ranges", "bytes");
        headers.set("connection", "keep-alive");
        headers.set("content-length", String.valueOf(image.length));

        return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(image)), headers, HttpStatus.OK);
    }

    @GetMapping("/tree_video")
    public ResponseDTO<FolderDTO> getTreeFolderVideo() {
        return new ResponseDTO<>().setData(videoService.getTreeFolderVideo()).setMessage("Danh s??ch th?? m???c con ch??u (bao g???m video) trong th?? m???c g???c");
    }

}
