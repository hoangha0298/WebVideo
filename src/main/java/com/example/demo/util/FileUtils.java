package com.example.demo.util;

import com.example.demo.model.response.FileResponse;
import com.example.demo.model.response.FolderResponse;
import com.example.demo.model.response.VideoResponse;
import org.springframework.util.SerializationUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class FileUtils {

	private final static Set<String> TYPES_VIDEO_SUPPORT = CollectionUtils.asSet("mp4");

	public static InputStream getInputStream(String path, Long begin, Long length) throws IOException {
		File file = new File(path);
		return getInputStream(file, begin, length);
	}

	public static InputStream getInputStream(File file, Long begin, Long length) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] array = new byte[length.intValue()];
		fileInputStream.skip(begin);
		fileInputStream.read(array);

		return new ByteArrayInputStream(array);
	}

	public static File getFile(String path) {
		return new File(path);
	}

	private static String getPathRelative(File root, File absolute) {
		return root.toURI().relativize(absolute.toURI()).getPath();
	}

	public static Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename)
				.filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	// trả về tất cả video trong thư mục có type khớp type truyền vào
	private static List<FileResponse> getFileFromFolder(File folder) {
		List<FileResponse> files = new ArrayList<>();

		for (File file : Objects.requireNonNull(folder.listFiles())) {
			if (!file.isFile()) {
				continue;
			}

			String typeInName = getExtensionByStringHandling(file.getName()).get();

			if (TYPES_VIDEO_SUPPORT.contains(typeInName)) {
				Integer lengthSecond = VideoUtils.getLengthTimeVideo(file);
				VideoResponse videoResponse = VideoResponse.builder()
						.pathAbsolute(file.toURI())
						.type(typeInName.toLowerCase())
						.length(file.length())
						.lengthSecond(lengthSecond)
						.build();

				files.add(videoResponse);
			} else {
				FileResponse fileResponse = FileResponse.builder()
						.pathAbsolute(file.toURI())
						.build();

				files.add(fileResponse);
			}

		}
		return files;
	}

	// trả về tree folder chứa  tất cả folder/file con cháu trong đó
	public static FolderResponse convertToResponse(File folder) {
		List<FileResponse> files = getFileFromFolder(folder);
		ArrayList<FolderResponse> subFolders = new ArrayList<>();

		// subFolders là tham số tham chiếu nên thao sẽ tác động tới object folderResponse
		FolderResponse folderResponse = FolderResponse.builder()
				.pathAbsolute(folder.toURI())
				.subFolders(subFolders)
				.files(files)
				.build();

		for (File file : Objects.requireNonNull(folder.listFiles())) {
			if (file.isDirectory()) {
				FolderResponse subFolder = convertToResponse(file);
				subFolders.add(subFolder);
			}
		}

		return folderResponse;
	}

	public static <T> boolean setAttribute(File file, String attributeName, T attributeValue) {
		try {
			Files.setAttribute(file.toPath(), attributeName, SerializationUtils.serialize(attributeValue));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static <T> T getAttribute (File file, String attributeName) {
		try {
			byte[] bytes = (byte[]) Files.getAttribute(file.toPath(), attributeName);
			if (bytes != null && bytes.length != 0) {
				return (T) SerializationUtils.deserialize(bytes);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
