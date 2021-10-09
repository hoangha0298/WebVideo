package com.example.demo.Service;

import com.example.demo.Model.DTO.FolderDTO;
import com.example.demo.Model.VideoRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class VideoService {

    public VideoRange getVideoRange(String pathRelative, String headerRange) throws IOException {
        return VideoRange.getInstance(pathRelative, headerRange);
    }

    public FolderDTO getTreeFolderVideo() {
        File folderRootVideo = new File(FileService.PATH_ROOT_VIDEO);
        return FolderDTO.getFolderRoot(folderRootVideo);
    }

}
