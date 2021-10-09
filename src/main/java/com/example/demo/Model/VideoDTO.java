package com.example.demo.Model;

import lombok.Getter;

@Getter
public class VideoDTO {

    private String pathRelative;
    // độ dài đơn vị byte
    private Long length;
    private String type;
    private String pathPreviewRelative;
    private String pathImageRelative;

    public VideoDTO() {
    }

    public VideoDTO setPathRelative(String pathRelative) {
        this.pathRelative = pathRelative;
        return this;
    }

    public VideoDTO setLength(Long length) {
        this.length = length;
        return this;
    }

    public VideoDTO setType(String type) {
        this.type = type;
        return this;
    }

    public VideoDTO setPathPreviewRelative(String pathPreviewRelative) {
        this.pathPreviewRelative = pathPreviewRelative;
        return this;
    }

    public VideoDTO setPathImageRelative(String pathImageRelative) {
        this.pathImageRelative = pathImageRelative;
        return this;
    }
}
