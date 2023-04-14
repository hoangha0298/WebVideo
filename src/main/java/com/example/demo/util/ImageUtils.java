package com.example.demo.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    public final static String png = "png";

    public final static String gif = "gif";

    public static BufferedImage asBufferedImage(byte[] imageByte) {
        BufferedImage result = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageByte);
            result = ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static BufferedImage asBufferedImage(InputStream imageInputStream) {
        BufferedImage result = null;
        try {
            result = ImageIO.read(imageInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] toByteArray(BufferedImage bufferedImage, String type) {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, type, byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArray.toByteArray();
    }

}
