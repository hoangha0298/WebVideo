package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerTestApi {

    @RequestMapping("/html_video")
    public String htmlVideo() {
        return "video";
    }

}
