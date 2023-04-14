package com.example.demo.converter;

import com.example.demo.model.DTO.Video;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class VideoConverterByCommandLineImpl extends VideoConverter {

	@Override
	public Video buildVideoPreview(Video video, File outputPreview) {

		String inputFilePath = video.getAbsolutePath();
		String outputFilePath;
		if (outputPreview != null) {
			outputFilePath = outputPreview.getPath();
		} else {
			outputFilePath = inputFilePath.substring(0, inputFilePath.lastIndexOf(".")) + "_preview.mp4";
			outputPreview = new File(outputFilePath);
		}

		int resolution = getMaxResolution(video);
		double duration = getDuration(video);
		int begin = 0;

		// Giảm độ phân giải video nếu cần
		boolean isHighQuantity = resolution > 480;

		// Bỏ qua 30 giây đầu tiên nếu video có độ dài lớn hơn 5 phút
		if (duration > 300) {
			begin = 30;
		}

		// Cắt video và nối lại
		StringBuilder trims = new StringBuilder();
		StringBuilder concat = new StringBuilder();
		double cutDuration = 0.005 * duration;
		double skipDuration = 0.05 * duration;
		int segment = 1;
		while (begin <= duration) {
			// Cắt video
			int end = (int) (begin + cutDuration);
			String trim = buildTrimCommand(begin, end, isHighQuantity, segment);
			trims.append(trim);
			trims.append(";");

			concat.append("[v" + segment + "]");

			begin = (int) (begin + cutDuration + skipDuration);
			segment++;
		}
		concat.append("concat=n=" + (segment - 1) + ":v=1:a=0[v]");

		String filterComplex = String.valueOf(trims) + concat;

		String command = String.format("ffmpeg -threads %s -i \"%s\" -filter_complex \"%s\" -map \"[v]\" -y %s", 4, inputFilePath, filterComplex, outputFilePath);
		command = command.replaceAll(" +", " ");

		// Thực thi lệnh
		try {
			System.out.println(command);

			ProcessBuilder pb = new ProcessBuilder(command.split(" "));
			pb.redirectErrorStream(true);
			Process p = pb.start();

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			System.out.println("Here is the standard output of the command:\n");
			String s;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			System.out.println("\nHere is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}

			p.waitFor();

			resetInfoCustomForVideoSourceAndPreview(video, new Video(outputPreview));

			return new Video(outputFilePath);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	// trả về số lượng file set thành công thuộc tính. Nhỏ nhất là 0 lớn nhất là 2
	private int resetInfoCustomForVideoSourceAndPreview(Video source, Video preview) {
		Video.Attributes sourceAttributes = createMd5(source);
		sourceAttributes.setPreview(false);
		sourceAttributes.setPath(source.getAbsolutePath());

		Video.Attributes previewAttributes = createMd5(preview);
		previewAttributes.setPreview(true);
		previewAttributes.setPath(preview.getAbsolutePath());

		sourceAttributes.setVideoPreview(previewAttributes);
		previewAttributes.setVideoSource(sourceAttributes);

		int numberOfSuccess = 0;
		numberOfSuccess += source.setAttributes(sourceAttributes) ? 1 : 0;
		numberOfSuccess += preview.setAttributes(previewAttributes) ? 1 : 0;

		return numberOfSuccess;
	}

	private String buildTrimCommand(int secondBegin, int secondEnd, boolean isHighQuantity, int segment) {
		StringBuilder trim = new StringBuilder();
		trim.append(String.format("[0:v]trim=%s:%s,setpts=0.2*PTS,", secondBegin, secondEnd));
		if (isHighQuantity) {
			trim.append("scale=-2:480,");
		}
		trim.append(String.format("setpts=PTS-STARTPTS[v%s]", segment));

		return trim.toString();
	}

	@Override
	public int getMaxResolution(Video video) {
		String command = String.format("ffprobe -v error -select_streams v:0 -show_entries stream=width,height -of csv=s=x:p=0 \"%s\"", video.getAbsolutePath());
		String result = processExecuteReturnString(command);

		if (result == null) {
			return 0;
		}

		Optional<Integer> maxResolution = Stream.of(result.trim().split("x")).map(Integer::valueOf).max(Comparator.naturalOrder());
		return maxResolution.orElse(0);
	}

	@Override
	public double getDuration(Video video) {
		String command = String.format("ffprobe -v error -show_entries format=duration -of default=noprint_wrappers=1:nokey=1 \"%s\"", video.getAbsolutePath());
		Double result = processExecuteReturnDouble(command);
		return result == null ? 0 : result;
	}

	@Override
	public Video.Attributes createMd5(Video video) {
		String command = String.format("ffmpeg -i \"%s\" -v error -map 0 -c copy -f streamhash -hash md5 -", video.getAbsolutePath());
		String result = processExecuteReturnString(command);
		if (result == null) {
			return null;
		}

		Video.Attributes attributes = new Video.Attributes();
		String[] md5Arr = result.split("\n");
		if (md5Arr.length == 1) {
			attributes.setHashVideo(md5Arr[0].replace("0,v,MD5=", ""));
		} else if (md5Arr.length == 2) {
			attributes.setHashVideo(md5Arr[0].replace("0,v,MD5=", ""));
			attributes.setHashAudio(md5Arr[0].replace("1,a,MD5=", ""));
		} else {
			return null;
		}

		return attributes;
	}

	public Double processExecuteReturnDouble(String command) {
		return Double.parseDouble(processExecuteReturnString(command).trim());
	}

	public Integer processExecuteReturnInteger(String command) {
		return Integer.parseInt(processExecuteReturnString(command).trim());
	}

	public String processExecuteReturnString(String command) {
		try {
			System.out.println("Process execute: " + command);
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			byte[] bytes = new byte[process.getInputStream().available()];
			process.getInputStream().read(bytes);
			String result = new  String(bytes);
			System.out.println("Process execute result: " + result);
			return result;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
