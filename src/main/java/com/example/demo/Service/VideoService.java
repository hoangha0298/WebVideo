package com.example.demo.Service;

import com.example.demo.Model.DTO.FolderDTO;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import lombok.Data;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class VideoService {

//    public final static String PATH_ROOT_VIDEO = "C:\\Users\\Admin\\Desktop\\video\\";
    public final static String PATH_ROOT_VIDEO = "C:\\D\\New folder\\";
    public final static String PATH_ROOT_IMAGE = "C:\\Users\\Admin\\Desktop\\video\\";

    @Autowired
    private FileService fileService;

    @Data
    public static class VideoRange {

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
    }

    public VideoRange getVideoRange(String pathRelative, String headerRange) throws IOException {
        Long rangeBegin = 0l;
        Long rangeEnd = fileService.getFile(PATH_ROOT_VIDEO + pathRelative).length() - 1;

        if (headerRange != null) {
            String[] ranges = headerRange.replace("bytes=", "").split("-");
            rangeBegin = Long.valueOf(ranges[0]);
            if (headerRange.length() == 2) {
                rangeEnd = Long.valueOf(ranges[1]);
            }
        }

        VideoRange videoRange = new VideoRange(pathRelative, rangeBegin, rangeEnd, fileService.getFile(PATH_ROOT_VIDEO + pathRelative).length(), null);
        InputStream videoStream = fileService.getInputStream(PATH_ROOT_VIDEO + pathRelative, rangeBegin, videoRange.lengthRange());
        videoRange.setRangeStream(videoStream);
        return videoRange;
    }

    public File getImage(String pathRelative) throws IOException {
        File image = fileService.getFile(PATH_ROOT_IMAGE + "neon.PNG");
        return image;
    }

    public FolderDTO getTreeFolderVideo() {
        File folderRootVideo = new File(PATH_ROOT_VIDEO);
        return FolderDTO.getFolderRoot(folderRootVideo);
    }

    // chỉ lấy khung hình ở giữa video làm ảnh đại diện
    public byte[] getImageFromVideo(String pathRelativeVideo) throws Exception {

        FFmpegFrameGrabber g = new FFmpegFrameGrabber(PATH_ROOT_VIDEO + pathRelativeVideo);
        g.start();
        int totalFrame = g.getLengthInVideoFrames();
        int frameGet = totalFrame / 2;
        Java2DFrameConverter converter = new Java2DFrameConverter();

        g.setFrameNumber(frameGet);
        Frame frame = g.grabKeyFrame();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", ba);
        g.stop();

        return ba.toByteArray();
    }

}
