package com.example.demo;

import com.example.demo.Service.FileService;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class VideoThumbTakerWithJavaCV {

    public static void main(String[] args) throws Exception {
//        String nameVideo = "mov_bbb.mp4";
        long time = System.currentTimeMillis();
        String nameVideo = "View_From_A_Blue_Moon_Trailer-1080p.mp4";
        FFmpegFrameGrabber g = new FFmpegFrameGrabber(FileService.PATH_ROOT_VIDEO + nameVideo);
        g.start();
        int totalFrame = g.getLengthInVideoFrames();
        int frameDistanceRecord = totalFrame / 100;

        Java2DFrameConverter converter = new Java2DFrameConverter();

        for (int i = frameDistanceRecord; i < totalFrame; i += frameDistanceRecord) {
            g.setFrameNumber(i);
            Frame frame = g.grabKeyFrame();
            System.out.println("i = " + i);
            BufferedImage bufferedImage = converter.getBufferedImage(frame);
            ImageIO.write(bufferedImage, "png", new File("C:\\Users\\Admin\\Desktop\\mysnapshot\\video-frame-" + System.currentTimeMillis() + ".png"));
        }

        g.stop();

        time = System.currentTimeMillis() - time;
        time = time / 1000;
        System.out.println(time / 60 + " phút, " + time % 60 + " giây");
    }

}
// 0 phút, 51 giây