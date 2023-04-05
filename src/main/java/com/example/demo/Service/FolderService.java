package com.example.demo.Service;

import com.example.demo.ResponseType;
import com.example.demo.model.response.FolderResponse;
import com.example.demo.util.FileUtils;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FolderService {

	@Getter
	@Value("${custom.source.root}")
	private String pathRootVideo;

	public FolderResponse getTreeFolderVideo(String pathRelative) {
		pathRelative = Strings.isBlank(pathRelative) ? "" : pathRelative;

		File folderRequest = new File(pathRootVideo, pathRelative);
		if (!folderRequest.exists()) {
			throw new RuntimeException(ResponseType.UNKNOWN_SOURCE.toString());
		}

		return FileUtils.convertToResponse(folderRequest);
	}

}
