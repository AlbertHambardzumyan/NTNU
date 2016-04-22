package org.tdt4173.ocr.gui.facade;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.svm.SVM;
import org.encog.ml.svm.training.SVMTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.tdt4173.ocr.config.Config;

import java.util.Arrays;

public class OCR_Facade {
    public static void learnSvm(double[][] TRAIN_INPUT, double[][] TRAIN_IDEAL, SVM svm ){
        System.out.println("\nTraining ...");
        // create training data
        MLDataSet trainingSet = new BasicMLDataSet(TRAIN_INPUT, TRAIN_IDEAL);
        // train the SVM
        final SVMTrain train = new SVMTrain(svm, trainingSet);
        train.iteration();
        train.finishTraining();
        System.out.println("Done ...\n");
    }

    public static void testSVM( double[][] TEST_INPUT, double[][] TEST_IDEAL, SVM svm) {
        // testing svm
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

    public static void learnTestKnn(double[][] TEST_INPUT, double[][] TEST_IDEAL, double[][] TRAIN_INPUT, double[][] TRAIN_IDEAL){
        System.out.println("\nTesting ...");
        int logIndex = 1, predictions = 0;

        for (int k = 0; k < TEST_INPUT.length; k++) {
            double value = 0;
            double min = -1;
            int index = -1;
            for (int i = 0; i < TRAIN_INPUT.length; i++) {
                for (int j = 0; j < TRAIN_INPUT[i].length; j++) {
                    value += Math.pow(TRAIN_INPUT[i][j] - TEST_INPUT[k][j], 2);
                }
                value = Math.sqrt(value);

                if (min < 0) {
                    min = value;
                    index = i;
                } else if (min > value) {
                    min = value;
                    index = i;
                }
            }
            System.out.println(logIndex++ + ".\tactual: " + Arrays.toString(TRAIN_IDEAL[index]) + ", ideal: " + Arrays.toString(TEST_IDEAL[k]));
            if (TRAIN_IDEAL[index][0] == TEST_IDEAL[k][0]) predictions++;
        }
        System.out.println("\nCorrect predictions: " + predictions + " out of " + TEST_INPUT.length);
        System.out.println("Accuracy: " + (double) predictions / TEST_INPUT.length * 100 + " percent");
        Encog.getInstance().shutdown();
    }

    public static void learnNN(double[][] TRAIN_INPUT, double[][] TRAIN_IDEAL, BasicNetwork network, int layersNumber, int hiddenNeurons ){
        System.out.println("\nTraining ...");
        network.addLayer(new BasicLayer(null, false, Config.INPUT_NEURONS));
        for (int i = 0; i < layersNumber; i++){
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
        } while(train.getError() > Config.ERROR_THRESHOLD && epoch < Config.EPOCH_THRESHOLD);
        train.finishTraining();
        System.out.println("Done ...\n");
    }

    public static void testNN( double[][] TEST_INPUT, double[][] TEST_IDEAL, BasicNetwork network) {
        // testing NN
        System.out.println("Testing ...");
        int logIndex = 1, predictions = 0;
        double[] outputVector;
        for(int i = 0; i < TEST_INPUT.length; i++ ) {
            outputVector = new double[26];
            network.compute(TEST_INPUT[i], outputVector);
            char result = vectorToChar(outputVector);
            System.out.println(logIndex++ +".\tactual: " + result + ", ideal: " + Arrays.toString(TEST_IDEAL[i]));

            if (result == Config.ALPHABET[(int)TEST_IDEAL[i][0]]) predictions++;
        }

        System.out.println("\nCorrect predictions: " + predictions + " out of " + TEST_INPUT.length);
        System.out.println("Accuracy: " + (double) predictions / TEST_INPUT.length * 100 + " percent");
        Encog.getInstance().shutdown();
    }

    public static void testDraw(int[][] array, SVM svm){
        System.out.println("\nTesting the Drawn Image ...");
        double result[] = new double[Config.DRAW_LENGTH];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                result[i * 20 + j] = array[j][i];
            }
        }

        double[][] TEST_INPUT_DRAW = new double[1][];
        TEST_INPUT_DRAW[0] = result;
        double[][] TEST_IDEAL_DRAW = new double[1][];
        TEST_IDEAL_DRAW[0] = new double[1];
        MLDataSet set = new BasicMLDataSet(TEST_INPUT_DRAW, TEST_IDEAL_DRAW);
        for (MLDataPair pair : set) {
            final MLData output = svm.compute(pair.getInput());
            System.out.println("System think its: '" + Config.ALPHABET[(int) output.getData(0)] + "'\n");
        }
        Encog.getInstance().shutdown();
    }

    public static void testDemo(double[][] TEST_INPUT_DEMO, double[][] TEST_IDEAL_DEMO, BasicNetwork network){
        System.out.println("\nTesting Demo Image...");

        int logIndex = 1, checked = 1;
        double[] outputVector;
        for(int i = 0; i < TEST_INPUT_DEMO.length; i++ ) {
            outputVector = new double[26];
            network.compute(TEST_INPUT_DEMO[i], outputVector);
            int resultIndex = findCharIndex(outputVector);
            if(resultIndex > -1){
                char result = Config.ALPHABET[resultIndex];
                System.out.println(logIndex++ +".\tSystem found: " + result);
            }
            checked++;
        }
        System.out.println("Checked " + --checked + " out of " + TEST_INPUT_DEMO.length);
        System.out.println("Done ...");
        Encog.getInstance().shutdown();
    }



    /** helpers */

    /** helpers for NN test**/
    private static char vectorToChar(double[] vector) {
        int maxIndex = maxIndex(vector);
        return Config.ALPHABET[maxIndex];
    }

    private static int maxIndex(double[] vector) {
        int maxIndex = -1;
        double max = -1;

        for(int i = 0; i < vector.length; ++i) {
            if(max < vector[i]) {
                maxIndex = i;
                max = vector[i];
            }
        }

        return maxIndex;
    }

    /** helpers for NN Demo**/
    private static int findCharIndex(double[] vector) {
        int maxIndex = -1;
        double max = -1;

        for(int i = 0; i < vector.length; ++i) {
            if(max < vector[i] && vector[i] > Config.PROB_THRESHOLD) {
                maxIndex = i;
                max = vector[i];
            }
        }

        return maxIndex;
    }
}
