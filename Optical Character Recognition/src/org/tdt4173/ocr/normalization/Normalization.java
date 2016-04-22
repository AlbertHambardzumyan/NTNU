package org.tdt4173.ocr.normalization;

import org.tdt4173.ocr.config.Config;


public class Normalization {

    public static double[] normalize(byte[] pixelsByte) {
        // Right now all we need is scaling
        return scale(pixelsByte);
    }
    
    private static double[] scale(byte[] pixelsByte) {
        double[] pixelsFloat = new double[Config.IMAGE_HEIGHT * Config.IMAGE_WIDTH];
        for (int i = 0; i < pixelsByte.length; i++) {
            pixelsFloat[i] = (double)Byte.toUnsignedInt(pixelsByte[i]) / Config.SCALE;
        }

        return pixelsFloat;
    }
    public static double[] normalize1DDouble(double[] pixels1DDouble) {
        // Right now all we need is scaling
        return scale(pixels1DDouble);
    }
    public static double[][] normalize2D(double[][] pixelsByte) {
        // Right now all we need is scaling
        return scale(pixelsByte);
    }

    private static double[][] scale(double[][] pixelsByte) {
        double[][] pixelsFloat = new double[Config.IMAGE_HEIGHT][Config.IMAGE_WIDTH];
        for (int i = 0; i < pixelsByte.length; i++) {
            for (int j = 0; j < pixelsByte[i].length; j++)
                pixelsFloat[i][j] = (pixelsByte[i][j] / Config.SCALE);
        }

        return pixelsFloat;
    }
    private static double[] scale(double[] pixels1DDouble) {
        for (int i = 0; i < pixels1DDouble.length; i++) {
            pixels1DDouble[i] = (pixels1DDouble[i] / Config.SCALE);
        }

        return pixels1DDouble;
    }
}
