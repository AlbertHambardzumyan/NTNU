package org.tdt4173.ocr.featureExtraction;

public interface FEMethod {

    public void setPixelMatrix(int[][] pixelMatrix);

    public void compute();

    public double[] getFeatureVector();

    public String getName();

}
