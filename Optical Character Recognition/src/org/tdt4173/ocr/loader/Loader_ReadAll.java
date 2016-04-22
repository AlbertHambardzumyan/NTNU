package org.tdt4173.ocr.loader;


import org.tdt4173.ocr.config.Config;
import org.tdt4173.ocr.imageConverter.ImageConverter;
import org.tdt4173.ocr.imageManipulation.ImageManipulation;
import org.tdt4173.ocr.normalization.Normalization;

import java.awt.image.BufferedImage;


public class Loader_ReadAll {

    public static void loadAllData(double[][] TRAIN_INPUT, double[][] TRAIN_IDEAL, double[][] TEST_INPUT, double[][] TEST_IDEAL){
        System.out.println("\nLoading Data ...");
        System.out.println("Load & Normalize ...");
        int TRAIN_COUNTER = 0, TEST_COUNTER = 0;
        for(int j = 0; j < 26; j++) {
            for (int i = 0; i < Config.DATA_SIZE[j]; i++) {
                //Load
                BufferedImage image = ImageManipulation.loadImage("././input/" + (char) (j + 97) + "/" + (char) (j + 97) + "_" + i + ".jpg");
                //Image to byte array
                byte[] pixelsByte = ImageConverter.toByteArray(image);
                //Normalization
                double[] pixelDouble1D = Normalization.normalize(pixelsByte);

                double[] dummy = {j}; // labeling
                if (i < Config.DATA_SIZE[j] / 2){
                    TRAIN_INPUT[TRAIN_COUNTER] = pixelDouble1D;
                    TRAIN_IDEAL[TRAIN_COUNTER] = dummy;
                    TRAIN_COUNTER++;
                }
                else{
                    TEST_INPUT[TEST_COUNTER] = pixelDouble1D;
                    TEST_IDEAL[TEST_COUNTER] = dummy;
                    TEST_COUNTER++;
                }
            }
            System.out.print((char) (j + 97) + "... ");
        }
        System.out.println("\nLoaded Train Data Size: "+TRAIN_COUNTER+"\nLoaded Test Data Size: "+TEST_COUNTER+"\nDone ...\n");
    }
}
