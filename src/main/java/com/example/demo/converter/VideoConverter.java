package com.example.demo.converter;

import com.example.demo.model.DTO.Video;

import java.io.File;

public abstract class VideoConverter {

	public abstract Video buildVideoPreview(Video origin, File outputPreview);

	public abstract int getMaxResolution(Video video);

	public abstract double getDuration(Video video);

	public abstract Video.Attributes createMd5(Video video);

}