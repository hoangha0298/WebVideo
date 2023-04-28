package com.example.demo.util;

import com.example.demo.model.DTO.Video;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bytedeco.javacpp.avutil;
import org.bytedeco.javacv.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class VideoUtils {

	public static final String NAME_FIELD_SAVE_INFO_CUSTOM = "user:infoVideoCustom";

	public final static Set<String> TYPES_VIDEO_SUPPORT = CollectionUtils.asSet("mp4");

	public static Video.Attributes getAttributesFromFile(Video video) throws IOException {
		return FileUtils.getAttribute(video, NAME_FIELD_SAVE_INFO_CUSTOM, new TypeReference<Video.Attributes>() {});
	}

	public static boolean removeAttributesFromFile(Video video) {
		return setAttributesToFile(video, null);
	}

	public static boolean setAttributesToFile(Video video, Video.Attributes attributes) {
		return FileUtils.setAttribute(video, NAME_FIELD_SAVE_INFO_CUSTOM, attributes);
	}

	public static final Java2DFrameConverter java2DFrameConverter = new Java2DFrameConverter();

	// chỉ lấy khung hình ở giữa video làm ảnh đại diện
	public static BufferedImage getImageFromVideo(File file) {

		FFmpegFrameGrabber video = new FFmpegFrameGrabber(file);
		BufferedImage bufferedImage = null;

		try {
			video.start();

			int totalFrame = video.getLengthInVideoFrames();
			int frameGet = totalFrame / 2;

			video.setFrameNumber(frameGet);
			Frame frame = video.grabKeyFrame();
			bufferedImage = java2DFrameConverter.convert(frame);
			video.stop();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}

	// trả về thời gian video tính bằng giây
	public static int getLengthTimeVideo(Video video) {
		int second = 0;
		try {
			FFmpegFrameGrabber g = new FFmpegFrameGrabber(video);
			g.start();
			second = (int) (g.getLengthInVideoFrames() / g.getFrameRate());
			g.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return second;
	}

	public static double getLengthTimeVideo(int numberFrame, double frameRate) {
		return numberFrame / frameRate;
	}

	// cắt video
	public static void createVideoPreview(File videoInput, File output) {
		Integer coreNumber = new Double(Math.ceil(Runtime.getRuntime().availableProcessors() / 4.0)).intValue();
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoInput);
		int totalFrame = grabber.getFrameNumber();
		grabber.setVideoOption("threads", coreNumber.toString());

		try {
			grabber.start();

			FrameRecorder recorder = new FFmpegFrameRecorder(output, grabber.getImageWidth(), grabber.getImageHeight());
			recorder.setFormat(grabber.getFormat());
			recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
			recorder.setFrameRate(grabber.getFrameRate());
			recorder.setVideoBitrate(grabber.getVideoBitrate());
			recorder.setVideoCodec(grabber.getVideoCodec());
			recorder.setVideoOption("preset", "ultrafast");
			recorder.setVideoOption("threads", coreNumber.toString());
			recorder.setVideoCodecName("libx264");

			recorder.start();

			int frameHanded = 0;
			long startTime = System.currentTimeMillis();

			FFmpegFrameFilter filter = new FFmpegFrameFilter(
					"scale=400x300",
					grabber.getImageWidth(),
					grabber.getImageHeight()
			);
			filter.setPixelFormat(grabber.getPixelFormat());
			filter.setFrameRate(grabber.getFrameRate() / 10);
			filter.start();

			int currentFrame = -1;
			Frame frame2;
			while ((frame2 = grabber.grab()) != null) {
				currentFrame++;

				if (currentFrame < 100) {
					continue;
				}

				if (currentFrame % 20 != 0) {
					continue;
				}

				filter.push(frame2);
			}

			Frame frame3;
			while ((frame3 = filter.pull()) != null) {
				frameHanded++;
				recorder.record(frame3);
			}


			filter.stop();
			filter.release();
			recorder.stop();
			grabber.stop();

			float secs = (System.currentTimeMillis() - startTime) / 1000.0f;
			System.out.println("processing " + frameHanded + " frames took " + (Math.round(secs * 1000) / 1000.0f) + " (" + (Math.round(frameHanded / secs * 1000) / 1000.0f) + " fps)");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
