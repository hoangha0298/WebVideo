package com.example.demo.Model;

import com.example.demo.Repository.VideoRepository;
import lombok.Data;

@Data
public class VideoRange {

    private static VideoRepository videoRepository;
    static {
        videoRepository = new VideoRepository();
    }

    private String name;
    private Long rangeBegin;
    private Long rangeEnd;
    private Long lengthTotalVideo;

    private VideoRange(String name, Long rangeBegin, Long rangeEnd, Long lengthTotalVideo) {
        this.name = name;
        this.rangeBegin = rangeBegin;
        this.rangeEnd = rangeEnd;
        this.lengthTotalVideo = lengthTotalVideo;
    }

    public long lengthRange() {
        return rangeEnd - rangeBegin + 1;
    }

    // tính toán rangeBegin, rangeEnd, độ dài toàn video
    public static VideoRange getInstance(String name, String headerRange) {
        Long rangeBegin = 0l;
        Long rangeEnd = videoRepository.getLengthVideo(name) - 1;

        if (headerRange != null) {
            String[] ranges = headerRange.replace("bytes=", "").split("-");
            rangeBegin = Long.valueOf(ranges[0]);
            if (headerRange.length() == 2) {
                rangeEnd = Long.valueOf(ranges[1]);
            }
        }

        return new VideoRange(name, rangeBegin, rangeEnd, videoRepository.getLengthVideo(name));
    }

}
