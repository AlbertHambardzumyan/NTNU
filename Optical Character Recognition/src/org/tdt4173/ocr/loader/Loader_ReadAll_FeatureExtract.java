package org.tdt4173.ocr.loader;


import org.tdt4173.ocr.config.Config;
import org.tdt4173.ocr.featureExtraction.*;
import org.tdt4173.ocr.imageConverter.ImageConverter;
import org.tdt4173.ocr.imageManipulation.ImageManipulation;

import java.awt.image.BufferedImage;


public class Loader_ReadAll_FeatureExtract {

    public static void loadAllData(double[][] TRAIN_INPUT, double[][] TRAIN_IDEAL, double[][] TEST_INPUT, double[][] TEST_IDEAL){
        System.out.println("\nLoading Data ...");
        System.out.println("Load & Feature Extract ...");
        int TRAIN_COUNTER = 0, TEST_COUNTER = 0;
        for(int j = 0; j < 26; j++) {
            for (int i = 0; i < Config.DATA_SIZE[j]; i++) {
                //Load
                BufferedImage image = ImageManipulation.loadImage("././input/" + (char) (j + 97) + "/" + (char) (j + 97) + "_" + i + ".jpg");
                //Image to 2D int array
               int[][] pixelsInt2D = ImageConverter.toInt2DArray(image);

                //Feature Vector
                FEMethod[] feMethods = {
                        new HorizontalCelledProjection(),
                        new VerticalCelledProjection()
                    };
                FeatureExtraction featureExtraction = new FeatureExtraction();
                featureExtraction.addMethods(feMethods);
                featureExtraction.setPixelMatrix(pixelsInt2D);
                featureExtraction.compute();

                double[] pixelDouble1D = featureExtraction.getFeatureVector();

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
