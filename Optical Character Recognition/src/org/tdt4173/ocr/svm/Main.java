package org.tdt4173.ocr.svm;

/**
 * Testing SVM Model
 */
public class Main {
    public static void main(String[] args){

        /** Loads all data, normalize, trains and tests **/
        new SVM_Model(false, 33).start();   // 72.10%

        /** Loads all data, extracts features, trains and tests **/
        //new SVM_Model(true, 33).start();  // 38.67%

        /** Loads all data, specifies kernel, trains and tests **/
        //new SVM_Model(false, 33, SVM_Model.KernelType.RadialBasisFunction).start(); Default one
        //new SVM_Model(false, 33, SVM_Model.KernelType.Sigmoid).start();   // 5.69%
        //new SVM_Model(false, 33, SVM_Model.KernelType.Precomputed).start();   // 5.95%
        //new SVM_Model(false, 33, SVM_Model.KernelType.Poly).start();  // 70.41%
        //new SVM_Model(false, 33, SVM_Model.KernelType.Linear).start();  // 18.15%
    }
}
