package com.example.demo.Model.DTO;

import com.example.demo.Service.VideoService;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;

@Getter
public class FolderDTO {

    private final static String[] TYPES_OF_VIDEO = {"mp4"};
    private static VideoService videoService = new VideoService();
    private static File folderRoot = null;

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

    // trả về tree folder k chứa folder truyền vào
    public static FolderDTO getFolderRoot(File folderRoot2) {
        folderRoot = new File(folderRoot2.getPath());
        return getFolderAndAllContent(folderRoot);
    }

    private static String getPathRelative(String pathAbsolute) {
        String pathRelative = pathAbsolute.replace(folderRoot.getPath(), "");
        return pathRelative.length() > 2 ? pathRelative.substring(1) : null;
    }

    // trả về tất cả video trong thư mục có type khớp type truyền vào
    private static ArrayList<VideoDTO> getVideoFromFolder(File folder, String[] types) {
        ArrayList<VideoDTO> videos = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (!file.isFile()) {
                continue;
            }
            for (String typeCheck : types) {
                // nếu là video thì thêm vào list
                if (file.getName().toLowerCase().endsWith("." + typeCheck.toLowerCase())) {
                    Long lengthSecond = videoService.getLengthTimeVideo(file);
                    VideoDTO videoDTO = new VideoDTO()
                            .setPathRelative(getPathRelative(file.getPath()))
                            .setType(typeCheck.toLowerCase())
                            .setLength(file.length())
                            .setLengthSecond(lengthSecond);
                    videos.add(videoDTO);
                    break;
                }
            }

        }
        return videos;
    }

    // trả về tree folder chứa video và tất cả folder con cháu trong đó (k tính folder truyền vào)
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
