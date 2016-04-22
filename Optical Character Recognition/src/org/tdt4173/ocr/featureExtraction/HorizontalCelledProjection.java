package org.tdt4173.ocr.featureExtraction;


import org.tdt4173.ocr.config.Config;

public class HorizontalCelledProjection implements FEMethod {

    private int[][] pixelMatrix;
    private double[] featureVector;


    @Override
    public void setPixelMatrix(int[][] pixelMatrix) {
        this.pixelMatrix = pixelMatrix;
        int length = pixelMatrix.length * pixelMatrix[0].length;
        featureVector = new double[length];
    }

    @Override
    public void compute() {
        int m = pixelMatrix.length;
        int n = pixelMatrix[0].length;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (pixelMatrix[i][j] > Config.THRESHOLD) {
                    int index = (i + m * j);

                    try {
                        featureVector[index] = 1.0;
                    } catch (Exception e) {
                        System.out.println(i + " " + j);
                    }
                }
            }
        }
    }

    @Override
    public double[] getFeatureVector() {
        return featureVector;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
