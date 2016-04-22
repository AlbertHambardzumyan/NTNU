package org.tdt4173.ocr.config;

public class Config {
    // Image Settings
    public static final int THRESHOLD = 100;
    public static final int IMAGE_HEIGHT = 20;
    public static final int IMAGE_WIDTH = 20;
    public static final int SCALE = 255;
    public static final int SIZE = IMAGE_HEIGHT * IMAGE_WIDTH;

    // Draw
    public static final int DRAW_LENGTH = 20 * 20;
    public static final char[] ALPHABET = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    //Demo img
    public static final int DEMO_HEIGHT = 140;
    public static final int DEMO_WIDTH = 200;
    public static final int LETTER_HEIGHT = 20;
    public static final int LETTER_WIDTH = 20;
    public static final double PROB_THRESHOLD = 0.99;

    // Neural Network Settings
    public static final int INPUT_NEURONS = IMAGE_HEIGHT * IMAGE_WIDTH;
    public static final int OUTPUT_NEURONS = 26;
    public static final double ERROR_THRESHOLD = 0.027;
    public static final double EPOCH_THRESHOLD = 500;


    // Loader Read All
    public static final int[] DATA_SIZE = {716, 153, 278, 237, 673, 116, 179, 245, 429, 110, 126, 273, 192, 498, 530, 196, 89, 515, 466, 423, 133, 117, 103, 115, 110, 90};
}
