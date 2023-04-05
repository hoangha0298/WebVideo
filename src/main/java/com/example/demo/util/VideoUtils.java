package com.example.demo.util;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class VideoUtils {

	// chỉ lấy khung hình ở giữa video làm ảnh đại diện
	public static byte[] getImageFromVideo(File video) {
		FFmpegFrameGrabber g = new FFmpegFrameGrabber(video);
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

		try {
			g.start();

			int totalFrame = g.getLengthInVideoFrames();
			int frameGet = totalFrame / 2;
			Java2DFrameConverter converter = new Java2DFrameConverter();

			g.setFrameNumber(frameGet);
			Frame frame = g.grabKeyFrame();
			BufferedImage bufferedImage = converter.getBufferedImage(frame);
			ImageIO.write(bufferedImage, "png", byteArray);
			g.stop();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return byteArray.toByteArray();
	}

	// trả về thời gian video tính bằng giây
	public static long getLengthTimeVideo(File video) {
		long second = 0;
		try {
			FFmpegFrameGrabber g = new FFmpegFrameGrabber(video);
			g.start();
			second = (long) (g.getLengthInVideoFrames() / g.getFrameRate());
			g.stop();
		} catch (Exception e) {
			System.out.println(e);
		}
		return second;
	}

}
