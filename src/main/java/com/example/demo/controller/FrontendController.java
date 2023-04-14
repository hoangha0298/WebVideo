package com.example.demo.controller;

import com.example.demo.Service.FolderService;
import com.example.demo.model.response.FolderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FrontendController {

    @Autowired
    private FolderService folderService;

    @RequestMapping("/video")
    public String video() {
        return "video";
    }

    @RequestMapping("/videos")
    public String videos(HttpServletRequest request) {
        FolderResponse folder = folderService.getTreeFolderVideo("");
        request.setAttribute("folderDTO", folder);
        return "videos";
    }

    @RequestMapping("/demo_plyr")
    public String demoPlyr() {
        return "demo_plyr";
    }

}
