package com.example.demo.Service;

import com.example.demo.util.FileUtils;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class VideoService {

	@Getter
	@Value("${custom.source.root}")
	private String pathRootVideo;

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

	public byte[] getImageFromVideo(String pathRelative) {
		File video = new File(pathRootVideo, pathRelative);

//        return VideoUtils.getImageFromVideo(video);
		return null;
	}

	public VideoRange getVideoRange(String pathRelative, String headerRange) throws IOException {
		Long rangeBegin = 0L;
		Long lengthFile = FileUtils.getFile(pathRootVideo + pathRelative).length() - 1;
		Long rangeEnd = lengthFile;
		Long maxSizeArrays = 1024L * 1024 * 10;

		if (headerRange != null) {
			String[] ranges = headerRange.replace("bytes=", "").split("-");
			rangeBegin = Long.valueOf(ranges[0]);
			if (headerRange.length() == 2) {
				rangeEnd = Long.valueOf(ranges[1]);
			}
		}

		VideoRange videoRange = new VideoRange(
				pathRelative,
				rangeBegin,
				rangeEnd,
				FileUtils.getFile(pathRootVideo + pathRelative).length(),
				null
		);
		InputStream videoStream = FileUtils.getInputStream(
				pathRootVideo + pathRelative,
				rangeBegin,
				videoRange.lengthRange() <= maxSizeArrays ? videoRange.lengthRange() : 0
		);

		videoRange.setRangeStream(videoStream);
		return videoRange;
	}

}
