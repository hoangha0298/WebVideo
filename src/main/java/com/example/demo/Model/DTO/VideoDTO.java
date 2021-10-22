package com.example.demo.Model.DTO;

import lombok.Getter;

@Getter
public class VideoDTO {

    private String pathRelative;
    // độ dài đơn vị byte
    private Long length;
    private Long lengthSecond;
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

    public VideoDTO setLengthSecond(Long lengthSecond) {
        this.lengthSecond = lengthSecond;
        return this;
    }
}
