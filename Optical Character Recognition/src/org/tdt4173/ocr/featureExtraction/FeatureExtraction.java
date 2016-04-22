package org.tdt4173.ocr.featureExtraction;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FeatureExtraction {
    private int featureVectorLength;
    private final List<FEMethod> methods;

    public FeatureExtraction() {
        methods = new LinkedList<FEMethod>();
    }

    public void addMethods(FEMethod[] feMethod) {
        methods.addAll(Arrays.asList(feMethod));
    }

    public void setPixelMatrix(int[][] pixelMatrix) {
        featureVectorLength = 0;

        for (FEMethod m : methods) {
            m.setPixelMatrix(pixelMatrix);
            featureVectorLength += m.getFeatureVector().length;
        }
    }

    public void compute() {
        for (FEMethod m : methods) {
            m.compute();
        }
    }

    public double[] getFeatureVector() {
        double[] primitiveFeatureVector = new double[featureVectorLength];
        int index = 0;

        for (FEMethod m : methods) {
            for (double d : m.getFeatureVector()) {
                primitiveFeatureVector[index++] = d;
            }
            //System.out.println(m.getName() + " : " + m.getFeatureVector().length);
            //System.out.println( Arrays.toString(m.getFeatureVector()) + "\n");
        }
        //System.out.println("Total : " + featureVectorLength);
        return primitiveFeatureVector;
    }
}
