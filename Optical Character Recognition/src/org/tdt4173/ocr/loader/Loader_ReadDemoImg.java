package org.tdt4173.ocr.loader;


import org.tdt4173.ocr.imageConverter.ImageConverter;
import org.tdt4173.ocr.imageManipulation.ImageManipulation;

import java.awt.image.BufferedImage;


public class Loader_ReadDemoImg {

    public static void loadDemo(double[][] TEST_INPUT, double[][] TEST_IDEAL){
        System.out.println("\nLoading Demo Image ...");

        //Load
        BufferedImage image = ImageManipulation.loadImage("././demoImg/demo.png");
        //Image to byte array + normalization
        double[][] pixelDouble2D = ImageConverter.toDouble2DArrayDemo(image);
        System.out.println("Image Height: " + pixelDouble2D.length);
        System.out.println("Image Width: " + pixelDouble2D[0].length);

        int TEST_COUNTER = 0;
        for(int i = 0; i < 140-20; i++) {
            for (int j = 0; j < 200-20; j++) {
                double[] dummyArray = new double[20*20];
                for (int n = 0; n < 20; n++){
                    for (int m = 0; m < 20; m++){
                        dummyArray[n*20 + m] = pixelDouble2D[i+n][j+m];
                    }
                }

                double[] dummy = {j}; // labeling
                TEST_INPUT[TEST_COUNTER] = dummyArray;
                TEST_IDEAL[TEST_COUNTER] = dummy;
                TEST_COUNTER++;
            }
        }
        System.out.println("Loaded Test Data Size: "+TEST_COUNTER+"\nDone ...\n");
    }

}
