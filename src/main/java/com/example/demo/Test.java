package com.example.demo;

import com.example.demo.converter.VideoConverterByCommandLineImpl;
import com.example.demo.model.DTO.Video;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.VideoUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class Test {

    public static void main(String[] args) throws IOException {
//        getPng();

//        createGif();

//        showAllTypeImageSupport();

//        subVideo();

//        createVideoPreview2();

        viewData();
    }

    public static BufferedImage getPng() throws IOException {
        String path = "C:\\Users\\Hoang\\Desktop\\video\\a";

        File video = new File(path, "cuu-non-120739.mp4");

        File thumbnail = new File(path, "thumbnail.png");
        FileOutputStream thumbnailOutputStream = new FileOutputStream(thumbnail);

        BufferedImage bufferedImage = VideoUtils.getImageFromVideo(video);

        thumbnailOutputStream.write(ImageUtils.toByteArray(bufferedImage, ImageUtils.png));

        return bufferedImage;
    }

    public static void createVideoPreview2() {
        String path = "C:\\Users\\Hoang\\Desktop\\video\\a b c ne";

        Video video = new Video(path, "Switzerland.mp4");

        new VideoConverterByCommandLineImpl().buildVideoPreview(video, null);
    }

    public static void viewData() {
        String path = "C:\\Users\\Hoang\\Desktop\\video\\a b c ne";

        Video video = new Video(path, "Switzerland.mp4");

        System.out.println(video.getAttributes());
    }

    public static void createGif() throws IOException {
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("gif").next();

        ImageWriteParam gifWriteParam = jpgWriter.getDefaultWriteParam();

        String path = "C:\\Users\\Hoang\\Desktop\\video\\a";
        File thumbnail = new File(path, "thumbnail.gif");
        FileOutputStream baos = new FileOutputStream(thumbnail);

        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        jpgWriter.setOutput(ios);

        BufferedImage bufferedImage = getPng();
        jpgWriter.write(null, new IIOImage(bufferedImage, null, null), gifWriteParam);

        baos.flush();
    }

    public static void showAllTypeImageSupport() {
        IIORegistry registry = IIORegistry.getDefaultInstance();
        Iterator<ImageWriterSpi> serviceProviders = registry.getServiceProviders(ImageWriterSpi.class, false);
        while (serviceProviders.hasNext()) {
            ImageWriterSpi next = serviceProviders.next();
            System.out.printf("description: %-27s   format names: %s%n",
                    next.getDescription(Locale.ENGLISH),
                    Arrays.toString(next.getFormatNames())
            );
        }
    }

}
