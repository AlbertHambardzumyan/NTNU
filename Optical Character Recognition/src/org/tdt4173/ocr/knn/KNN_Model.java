package org.tdt4173.ocr.knn;


import org.encog.Encog;
import org.tdt4173.ocr.config.Config;
import org.tdt4173.ocr.loader.Loader_ReadAll;
import org.tdt4173.ocr.loader.Loader_ReadAll_FeatureExtract;

import java.util.Arrays;

public class KNN_Model {
    private boolean featureExtraction;

    public KNN_Model(boolean featureExtraction){
        this.featureExtraction = featureExtraction;
    }

    public void start(){
        int TOTAL_TRAIN_SIZE = 0, TOTAL_TEST_SIZE = 0;
        for (int aDATA_SIZE : Config.DATA_SIZE) {
            int count = aDATA_SIZE / 2;
            TOTAL_TRAIN_SIZE +=count;
            TOTAL_TEST_SIZE += aDATA_SIZE-count;
        }
        System.out.println("Specified Total Train Size: "+TOTAL_TRAIN_SIZE);
        System.out.println("Specified Total Test Size: "+TOTAL_TEST_SIZE);



        double TRAIN_INPUT[][] = new double[TOTAL_TRAIN_SIZE][];
        double TRAIN_IDEAL[][] = new double[TOTAL_TRAIN_SIZE][];

        double TEST_INPUT[][] = new double[TOTAL_TEST_SIZE][];
        double TEST_IDEAL[][] = new double[TOTAL_TEST_SIZE][];
        if (!featureExtraction)
            Loader_ReadAll.loadAllData(TRAIN_INPUT, TRAIN_IDEAL, TEST_INPUT, TEST_IDEAL);
        else
            Loader_ReadAll_FeatureExtract.loadAllData(TRAIN_INPUT, TRAIN_IDEAL, TEST_INPUT, TEST_IDEAL);


        // test the Knn
        System.out.println("\nTesting ...");
        int logIndex = 1, predictions = 0;
        for (int k = 0; k < TEST_INPUT.length; k++) {
            double value = 0;
            double min  = -1;
            int index = -1;
            for(int i = 0; i < TRAIN_INPUT.length; i++){
                for(int j = 0; j < TRAIN_INPUT[i].length; j++){
                    value += Math.pow(TRAIN_INPUT[i][j] - TEST_INPUT[k][j], 2);
                }
                value = Math.sqrt(value);
                if (min < 0) {
                    min = value;
                    index = i;
                }
                else if (min > value){
                    min = value;
                    index = i;
                }
            }
            System.out.println(logIndex++ + ".\tactual: " + Arrays.toString(TRAIN_IDEAL[index]) + ", ideal: " + Arrays.toString(TEST_IDEAL[k]));
            if (TRAIN_IDEAL[index][0] == TEST_IDEAL[k][0])predictions++;
        }
        System.out.println("\nCorrect predictions: " + predictions + " out of "+TOTAL_TEST_SIZE);
        System.out.println("Accuracy: "+ (double)predictions/TOTAL_TEST_SIZE*100 +" percent");
        Encog.getInstance().shutdown();
    }
}
