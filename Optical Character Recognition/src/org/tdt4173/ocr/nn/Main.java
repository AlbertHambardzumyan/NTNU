package org.tdt4173.ocr.nn;

/**
 * Testing NN Model
 */
public class Main {
    public static void main(String[] args){

        /** Loads all data, normalize, trains and tests **/
        new NN_Model(200, 1).start();   // 42.77%

        //new NN_Model(400, 1).start();   // 39.18%

        //new NN_Model(100, 1).start();   // 38.98%

        //new NN_Model(100, 2).start();   // stopped at Epoch #400

        //new NN_Model(1000, 1).start();   // 37.15%

        //new NN_Model(300, 1).start();   // 39.37%
    }
}
