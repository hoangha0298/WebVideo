package com.example.demo.model.DTO;

import com.example.demo.util.FileUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.File;
import java.io.Serializable;
import java.net.URI;

@Getter
@Setter
public class Video extends File {

	public static final String NAME_FIELD_SAVE_INFO_CUSTOM = "user:infoVideoCustom";


	@Data
	@SuperBuilder
	@NoArgsConstructor
	public static class Attributes implements Serializable {

		private static final long serialVersionUID = 0L;

		private String hashVideo;

		private String hashAudio;

		private String path;

		private boolean isPreview = false;

		// child
		private Attributes videoPreview;

		// parent
		private Attributes videoSource;

		@Override
		public String toString() {
			return toString(1);
		}

		public String toString(int level) {
			String videoPreviewStr = null;
			if (level == 1 && videoPreview != null) {
				videoPreviewStr = videoPreview.toString(level+1);
			}

			String videoSourceStr = null;
			if (level == 1 && videoSource != null) {
				videoSourceStr = videoSource.toString(level+1);
			}

			return  "Attributes{" +
					"hashVideo='" + hashVideo + '\'' +
					", hashAudio='" + hashAudio + '\'' +
					", path='" + path + '\'' +
					", isPreview=" + isPreview +
					", videoPreview=" + videoPreviewStr +
					", videoSource=" + videoSourceStr +
					'}';
		}
	}

	// attribute để ghi vào file
	private Attributes attributes;

	private void init() {
		attributes = getAtributesFromFile(this);
	}

	private void validateInit() {
		if (!exists()) {
			throw new RuntimeException(super.getPath() + " - file không tồn tại!");
		}
		if (isDirectory()) {
			throw new RuntimeException(super.getPath() + " - là đường dẫn không phải file!");
		}
	}

	public Video(File file) {
		super(file.getAbsolutePath());
		validateInit();
		init();
	}

	public Video(String pathname) {
		super(pathname);
		validateInit();
		init();
	}

	public Video(String parent, String child) {
		super(parent, child);
		validateInit();
		init();
	}

	public Video(File parent, String child) {
		super(parent, child);
		validateInit();
		init();
	}

	public Video(URI uri) {
		super(uri);
		validateInit();
		init();
	}

	public boolean setAttributes(Attributes attributes) {
		if (setAtributesToFile(this, attributes)) {
			this.attributes = attributes;
			return true;
		}
		return false;
	}

	public Video.Attributes getAtributesFromFile(Video video) {
		return FileUtils.getAttribute(video, NAME_FIELD_SAVE_INFO_CUSTOM);
	}

	public boolean setAtributesToFile(Video video, Video.Attributes attributes) {
		return FileUtils.setAttribute(video, NAME_FIELD_SAVE_INFO_CUSTOM, attributes);
	}
}
