package com.example.demo.Controller;

import com.example.demo.Model.DTO.FolderDTO;
import com.example.demo.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControllerTestApi {

    @Autowired
    private VideoService videoService;

    @RequestMapping("/video")
    public String video() {
        return "video";
    }

    @RequestMapping("/videos")
    public String videos(HttpServletRequest request) {
        FolderDTO folderDTO = videoService.getTreeFolderVideo();
        request.setAttribute("folderDTO", folderDTO);
        return "videos";
    }

}
