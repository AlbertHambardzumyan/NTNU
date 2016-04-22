package org.tdt4173.ocr.featureExtraction;

public class VerticalCelledProjection implements FEMethod {
    private int[][] pixelMatrix;
    private double[] featureVector;


    @Override
    public void setPixelMatrix(int[][] pixelMatrix) {
        this.pixelMatrix = transpose(pixelMatrix);
        int length = pixelMatrix.length * pixelMatrix[0].length;
        featureVector = new double[length];
    }

    @Override
    public void compute() {
        // We transposed the matrix so we can use the same algorithm
        HorizontalCelledProjection h = new HorizontalCelledProjection();
        h.setPixelMatrix(pixelMatrix);
        h.compute();
        featureVector = h.getFeatureVector();
    }

    @Override
    public double[] getFeatureVector() {
        return featureVector;
    }

    private int[][] transpose(int[][] matrix) {
        int x = matrix.length;
        int y = matrix[0].length;
        int[][] transposeMat = new int[x][y];
        
        for(int i = 0; i < matrix.length; ++i) {
            for(int j = 0; j < matrix[0].length; ++j) {
                transposeMat[j][i] = matrix[i][j];
            }
        }
        return transposeMat;
    }

    @Override
    public String getName(){
        return this.getClass().getSimpleName();
    }
}
