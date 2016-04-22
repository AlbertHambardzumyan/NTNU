package org.tdt4173.ocr.knn;


/**
 * Testing KNN Model
 */
public class Main {
    public static void main(String[] args){

        /** Loads all data, normalize, trains and tests **/
        //new KNN_Model(false).start();   // 65.84%

        /** Loads all data, extracts features, trains and tests **/
        new KNN_Model(true).start();    //  66.34%
    }
}
