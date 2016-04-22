package org.tdt4173.ocr.nn;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.tdt4173.ocr.config.Config;
import org.tdt4173.ocr.loader.Loader_ReadAll_NN;

import java.util.Arrays;


public class NN_Model {
    private int hiddenNeurons;
    private int layersNumber;

    public NN_Model(int hiddenNeurons, int layersNumber) {
        this.hiddenNeurons = hiddenNeurons;
        this.layersNumber = layersNumber;
    }

    public void start() {
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
        Loader_ReadAll_NN.loadAllData(TRAIN_INPUT, TRAIN_IDEAL, TEST_INPUT, TEST_IDEAL);


        System.out.println("\nTraining ...");
        // create a neural network
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, false, Config.INPUT_NEURONS));
        for (int i = 0; i < layersNumber; i++) {
            network.addLayer(new BasicLayer(new ActivationSigmoid(), true, hiddenNeurons));
        }
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, Config.OUTPUT_NEURONS));
        network.getStructure().finalizeStructure();
        network.reset();
        // create training data
        MLDataSet trainingSet = new BasicMLDataSet(TRAIN_INPUT, TRAIN_IDEAL);
        // train the neural network
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
        int epoch = 1;
        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while (train.getError() > Config.ERROR_THRESHOLD && epoch < Config.EPOCH_THRESHOLD);
        train.finishTraining();
        System.out.println("Done ...\n");


        // testing
        System.out.println("Testing ...");
        int logIndex = 1, predictions = 0;
        double[] outputVector;
        for (int i = 0; i < TEST_INPUT.length; i++) {
            outputVector = new double[26];
            network.compute(TEST_INPUT[i], outputVector);
            char result = vectorToChar(outputVector);
            System.out.println(logIndex++ + ".\tactual: " + result + ", ideal: " + Arrays.toString(TEST_IDEAL[i]));

            if (result == Config.ALPHABET[(int) TEST_IDEAL[i][0]]) predictions++;
        }

        System.out.println("\nCorrect predictions: " + predictions + " out of " + TEST_INPUT.length);
        System.out.println("Accuracy: " + (double) predictions / TEST_INPUT.length * 100 + " percent");
        Encog.getInstance().shutdown();
    }


    /**
     * helpers *
     */
    private static char vectorToChar(double[] vector) {
        int maxIndex = maxIndex(vector);
        return Config.ALPHABET[maxIndex];
    }

    private static int maxIndex(double[] vector) {
        int maxIndex = -1;
        double max = -1;

        for (int i = 0; i < vector.length; ++i) {
            if (max < vector[i]) {
                maxIndex = i;
                max = vector[i];
            }
        }

        return maxIndex;
    }
}