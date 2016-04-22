package org.tdt4173.ocr.svm;

import org.encog.Encog;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.svm.SVM;
import org.encog.ml.svm.SVMType;
import org.encog.ml.svm.training.SVMTrain;
import org.tdt4173.ocr.config.Config;
import org.tdt4173.ocr.loader.Loader_ReadAll;
import org.tdt4173.ocr.loader.Loader_ReadAll_FeatureExtract;


public class SVM_Model {
    private boolean featureExtraction;
    private SVM svm;
    public enum KernelType{ RadialBasisFunction, Sigmoid, Precomputed, Poly, Linear }

    public SVM_Model(boolean featureExtraction, int theInputCount){
        // create a SVM for classification, change false to true for regression.
        // For regression it will use an epsilon support vector. Both types will use an RBF kernel.
        this.featureExtraction = featureExtraction;
        svm = new org.encog.ml.svm.SVM(theInputCount, false);
    }
    public SVM_Model(boolean featureExtraction, int theInputCount, KernelType kernelType){
        this.featureExtraction = featureExtraction;
        switch (kernelType){
            case Poly:
                System.out.println("Specified Kernel Type: " + kernelType);
                svm = new SVM(theInputCount, SVMType.SupportVectorClassification, org.encog.ml.svm.KernelType.Poly);
                break;
            case Precomputed:
                System.out.println("Specified Kernel Type: " + kernelType);
                svm = new SVM(theInputCount, SVMType.SupportVectorClassification, org.encog.ml.svm.KernelType.Precomputed);
                break;
            case Linear:
                System.out.println("Specified Kernel Type: " + kernelType);
                svm = new SVM(theInputCount, SVMType.SupportVectorClassification, org.encog.ml.svm.KernelType.Linear);
                break;
            case Sigmoid:
                System.out.println("Specified Kernel Type: " + kernelType);
                svm = new SVM(theInputCount, SVMType.SupportVectorClassification, org.encog.ml.svm.KernelType.Sigmoid);
                break;
            case RadialBasisFunction:
                System.out.println("Specified Kernel Type: " + kernelType);
                svm = new SVM(theInputCount, SVMType.SupportVectorClassification, org.encog.ml.svm.KernelType.RadialBasisFunction);
                break;
        }
    }

    public void start(){
        int TOTAL_TRAIN_SIZE = 0, TOTAL_TEST_SIZE = 0;
        for (int aDATA_SIZE : Config.DATA_SIZE) {
            int count = aDATA_SIZE / 2;
            TOTAL_TRAIN_SIZE += count;
            TOTAL_TEST_SIZE += aDATA_SIZE - count;
        }
        System.out.println("Specified Total Train Size: " + TOTAL_TRAIN_SIZE);
        System.out.println("Specified Total Test Size: " + TOTAL_TEST_SIZE);


        double TRAIN_INPUT[][] = new double[TOTAL_TRAIN_SIZE][];
        double TRAIN_IDEAL[][] = new double[TOTAL_TRAIN_SIZE][];

        double TEST_INPUT[][] = new double[TOTAL_TEST_SIZE][];
        double TEST_IDEAL[][] = new double[TOTAL_TEST_SIZE][];
        if (!featureExtraction)
            Loader_ReadAll.loadAllData(TRAIN_INPUT, TRAIN_IDEAL, TEST_INPUT, TEST_IDEAL);
        else
            Loader_ReadAll_FeatureExtract.loadAllData(TRAIN_INPUT, TRAIN_IDEAL, TEST_INPUT, TEST_IDEAL);


        System.out.println("\nTraining ...");
        // create training data
        MLDataSet trainingSet = new BasicMLDataSet(TRAIN_INPUT, TRAIN_IDEAL);
        // train the SVM
        final SVMTrain train = new SVMTrain(svm, trainingSet);
        train.iteration();
        train.finishTraining();
        System.out.println("Done ...\n");


        // testing
        System.out.println("\nTesting ...");
        int logIndex = 1, predictions = 0;
        MLDataSet set = new BasicMLDataSet(TEST_INPUT, TEST_IDEAL);
        for (MLDataPair pair : set) {
            final MLData output = svm.compute(pair.getInput());
            System.out.println(logIndex++ + ".\tactual: " + Config.ALPHABET[(int) output.getData(0)] + "\tideal: " + Config.ALPHABET[(int) pair.getIdeal().getData(0)]);
            if (output.getData(0) == pair.getIdeal().getData(0)) predictions++;
        }
        System.out.println("\nCorrect predictions: " + predictions + " out of " + TEST_INPUT.length);
        System.out.println("Accuracy: " + (double) predictions / TEST_INPUT.length * 100 + " percent");
        Encog.getInstance().shutdown();
    }
}