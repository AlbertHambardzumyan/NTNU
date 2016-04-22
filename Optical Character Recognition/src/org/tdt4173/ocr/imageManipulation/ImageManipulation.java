package org.tdt4173.ocr.imageManipulation;

import org.tdt4173.ocr.config.Config;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * provides static methods for manipulating images.
 */
public class ImageManipulation {

    // Load image
    public static BufferedImage loadImage(String filename) {
        final long startTime = System.nanoTime();
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filename));
            return image;
        } catch(IOException e) {
            System.out.println("There was an error while loading the file: " + e);
            //e.printStackTrace();
            return null;
        }
    }

    // Convert image to greyscale
    public static void imageGreyScale(BufferedImage image) {
        BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = img.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }

    // Save Image
    public static void saveImage(BufferedImage image, String fileType, String fileName) {
        try {
            ImageIO.write(image, fileType, new File(fileName)); //"jpg", "test.jpg"
        } catch(IOException e) {
            System.out.println("There was an error while saving the file: " + e);
        }
    }


    public static int countPixels(int[][] matrix) {
        return countPixels(matrix, 0, 0, matrix.length, matrix[0].length);
    }

    public static int countPixels(int[][] matrix, int x, int y, int width, int height) {
        int pixels = 0;

        for(int i = x; i < x+width; ++i) {
            for(int j = y; j < y+height; ++j) {
                if(matrix[i][j] > Config.THRESHOLD) { ++pixels; }
            }
        }

        return pixels;
    }
}
