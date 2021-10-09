package com.example.demo.Model;

import com.example.demo.Repository.VideoRepository;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;

@Getter
public class FolderDTO {

    private final static String[] TYPES_OF_VIDEO = {"mp4"};

    private static VideoRepository videoRepository = new VideoRepository();

    private ArrayList<VideoDTO> videos;
    private ArrayList<FolderDTO> subFolders;
    private String pathRelative;

    private FolderDTO() {
    }

    public FolderDTO setVideos(ArrayList<VideoDTO> videos) {
        this.videos = videos;
        return this;
    }

    public FolderDTO setSubFolders(ArrayList<FolderDTO> folders) {
        this.subFolders = folders;
        return this;
    }

    public FolderDTO setPathRelative(String pathRelative) {
        this.pathRelative = pathRelative;
        return this;
    }

    public static FolderDTO getFolderRoot() {
        File root = new File(VideoRepository.PATH_ROOT_VIDEO);
        return getFolderAndAllContent(root);
    }

    private static String getPathRelative(String pathAbsolute) {
        return pathAbsolute.replace(VideoRepository.PATH_ROOT_VIDEO, "");
    }

    private static ArrayList<VideoDTO> getVideoFromFolder(File folder, String[] types) {
        ArrayList<VideoDTO> videos = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (!file.isFile()) {
                continue;
            }
            for (String typeCheck : types) {
                // nếu là video thì thêm vào list
                if (file.getName().toLowerCase().endsWith("." + typeCheck.toLowerCase())) {
                    VideoDTO videoDTO = new VideoDTO()
                            .setPathRelative(getPathRelative(file.getPath()))
                            .setType(typeCheck.toLowerCase())
                            .setLength(file.length());
                    videos.add(videoDTO);
                    break;
                }
            }

        }
        return videos;
    }

    // trả về folder chứa video và tất cả folder con cháu trong đó
    private static FolderDTO getFolderAndAllContent(File folder) {
        ArrayList<VideoDTO> videos = getVideoFromFolder(folder, TYPES_OF_VIDEO);
        ArrayList<FolderDTO> subFolders = new ArrayList<>();
        // set tham số kiểu tham chiếu nên thao tác ở list ngoài tác động tới object folderDTO
        FolderDTO folderDTO = new FolderDTO()
                .setPathRelative(getPathRelative(folder.getPath()))
                .setSubFolders(subFolders)
                .setVideos(videos);

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                FolderDTO subFolder = getFolderAndAllContent(file);
                subFolders.add(subFolder);
            }
        }
        return folderDTO;
    }

}
