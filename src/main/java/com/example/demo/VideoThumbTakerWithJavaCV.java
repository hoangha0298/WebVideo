package com.example.demo;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Value;

import java.awt.image.BufferedImage;

public class VideoThumbTakerWithJavaCV {

    public final static String PATH_ROOT_VIDEO_TEST = "C:\\Users\\Admin\\Desktop\\video\\";
    @Value("${custom.source.root}")
    private static String PATH_ROOT_VIDEO;

    public static void main(String[] args) throws Exception {
        lengthTime();
    }

    public static void lengthTime() throws FrameGrabber.Exception {
        String nameVideo = "View_From_A_Blue_Moon_Trailer-1080p.mp4";
        FFmpegFrameGrabber g = new FFmpegFrameGrabber(PATH_ROOT_VIDEO_TEST + nameVideo);
        g.start();
        long second = (long) (g.getLengthInVideoFrames() / g.getFrameRate());
        System.out.println(second);
        System.out.println(second/60 + ":" + second%60);
        g.stop();
    }

    public static void videoToGif() throws FrameGrabber.Exception {
        long countTimeRun = System.currentTimeMillis();

//        String nameVideo = "mov_bbb.mp4";
        String nameVideo = "View_From_A_Blue_Moon_Trailer-1080p.mp4";
        FFmpegFrameGrabber g = new FFmpegFrameGrabber(PATH_ROOT_VIDEO + nameVideo);
        g.start();
        int totalFrame = g.getLengthInVideoFrames();
        int frameDistanceRecord = totalFrame / 100;
        float totalTimeGif = 10; // 10 giây

        Java2DFrameConverter converter = new Java2DFrameConverter();
        AnimatedGifEncoder animatedGifEncoder = new AnimatedGifEncoder();
        animatedGifEncoder.setRepeat(0);
        animatedGifEncoder.setFrameRate(totalFrame / totalTimeGif);
        animatedGifEncoder.start("C:\\Users\\Admin\\Desktop\\mysnapshot\\video-animated-" + System.currentTimeMillis() + ".gif");

        for (int i = frameDistanceRecord; i < totalFrame; i += frameDistanceRecord) {
            g.setFrameNumber(i);
            Frame frame = g.grabKeyFrame();
            System.out.println("i = " + i);
            System.out.println(Math.round(((float) i / totalFrame) * 100) + "%");
            BufferedImage bufferedImage = converter.getBufferedImage(frame);
//            ImageIO.write(bufferedImage, "png", new File("C:\\Users\\Admin\\Desktop\\mysnapshot\\video-frame-" + System.currentTimeMillis() + ".png"));
            animatedGifEncoder.addFrame(bufferedImage);
        }

        g.stop();
        animatedGifEncoder.finish();

        countTimeRun = System.currentTimeMillis() - countTimeRun;
        countTimeRun = countTimeRun / 1000;
        System.out.println(countTimeRun / 60 + " phút, " + countTimeRun % 60 + " giây");
    }

}