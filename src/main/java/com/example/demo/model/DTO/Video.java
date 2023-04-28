package com.example.demo.model.DTO;

import com.example.demo.util.VideoUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Video extends File {

	public static class VideoPool {

		private static VideoPool videoPool;

		public static VideoPool getInstance() {
			if (videoPool == null) {
				videoPool = new VideoPool();
			}
			return videoPool;
		}

		private Map<Attributes.Hash, Video> pool = new HashMap<>();

		private VideoPool() {
		}

		protected boolean addThenUpdatePreview(Video video) {
			if (video == null) {
				return false;
			}

			Attributes attributes = video.getAttributes();

			if (attributes == null || attributes.hash == null) {
				return false;
			}

			video.preview = pool.get(attributes.hashPreview);
			pool.put(attributes.hash, video);
			return true;
		}

	}

	@Data
	@SuperBuilder
	@NoArgsConstructor
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "hash")
	public static class Attributes {

		@Data
		@SuperBuilder
		@NoArgsConstructor
		public static class Hash {
			private String hashVideo;
			private String hashAudio;
		}

		private Hash hash;

		private Hash hashPreview;

		private boolean isPreview = false;
	}

	// attribute để ghi vào file
//	private Attributes attributes;

	private Video preview;

	private void init() {
		getAttributes();
		VideoPool.getInstance().addThenUpdatePreview(this);
	}

	public boolean removeAttribute() {
//        this.attributes = null;
		return VideoUtils.removeAttributesFromFile(this);
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
		if (VideoUtils.setAttributesToFile(this, attributes)) {
//			this.attributes = attributes;
			return true;
		}
		return false;
	}

	public Attributes getAttributes() {
		try {
//			attributes = VideoUtils.getAttributesFromFile(this);
			return VideoUtils.getAttributesFromFile(this);
		} catch (Exception e) {
			e.printStackTrace();
			removeAttribute();
		}
		return null;
	}
}
