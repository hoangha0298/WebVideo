package com.example.demo.Model;

import com.example.demo.Service.FileService;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;

@Data
public class VideoRange {

    private static FileService fileService;
    static {
        fileService = new FileService();
    }

    private String pathRelative;
    private Long rangeBegin;
    private Long rangeEnd;
    private Long lengthTotalVideo;
    private InputStream rangeStream;

    private VideoRange(String pathRelative, Long rangeBegin, Long rangeEnd, Long lengthTotalVideo, InputStream rangeStream) {
        this.pathRelative = pathRelative;
        this.rangeBegin = rangeBegin;
        this.rangeEnd = rangeEnd;
        this.lengthTotalVideo = lengthTotalVideo;
        this.rangeStream = rangeStream;
    }

    public long lengthRange() {
        return rangeEnd - rangeBegin + 1;
    }

    public static VideoRange getInstance(String pathRelative, String headerRange) throws IOException {
        Long rangeBegin = 0l;
        Long rangeEnd = fileService.getFile(pathRelative).length() - 1;

        if (headerRange != null) {
            String[] ranges = headerRange.replace("bytes=", "").split("-");
            rangeBegin = Long.valueOf(ranges[0]);
            if (headerRange.length() == 2) {
                rangeEnd = Long.valueOf(ranges[1]);
            }
        }

        VideoRange videoRange = new VideoRange(pathRelative, rangeBegin, rangeEnd, fileService.getFile(pathRelative).length(), null);
        InputStream videoStream = fileService.getInputStream(pathRelative, rangeBegin, videoRange.lengthRange());
        videoRange.setRangeStream(videoStream);
        return videoRange;
    }

}
