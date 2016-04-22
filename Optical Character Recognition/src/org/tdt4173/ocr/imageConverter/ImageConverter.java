package org.tdt4173.ocr.imageConverter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * provides static methods for converting image to array.
 * Created by Alik on 4/4/2016.
 */
public class ImageConverter {

    // Image to byte array.  Top to bottom. Left to right.
    // Different ways - the same result.
    public static byte[] toByteArray(BufferedImage image) {
        //byte[] pixels = (byte[])image.getRaster().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }

    public static int[] toIntArray1D(BufferedImage image) {
        int[] pixels = new int[image.getHeight() * image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                pixels[j + i * image.getWidth()] = image.getData().getSample(i, j, 0);
            }
        }
        return pixels;
    }

    public static int[][] toInt2DArray(BufferedImage image) {
        int[][] pixels = new int[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                pixels[i][j] = image.getData().getSample(i, j, 0);
                //System.out.println(pixels[i][j]);
            }
        }
        return pixels;
    }

    public static double[][] toDouble2DArray(BufferedImage image) {
        double[][] pixels = new double[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                pixels[i][j] = image.getData().getSample(i, j, 0);
                //System.out.println(pixels[i][j]);
            }
        }
        return pixels;
    }

    public static double[][] toDouble2DArrayDemo(BufferedImage image) {
        double[][] pixels = new double[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                pixels[i][j] = (double) image.getData().getSample(j, i, 0) / 255;
                //System.out.println(pixels[i][j]);
            }
        }
        return pixels;
    }

    // Image cell to int.
    public static int toByte(BufferedImage image, int col, int row) {
        return image.getData().getSample(col, row, 0); //col, row color channel
    }
}
