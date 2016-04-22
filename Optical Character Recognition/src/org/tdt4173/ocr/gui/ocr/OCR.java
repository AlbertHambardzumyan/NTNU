package org.tdt4173.ocr.gui.ocr;


import org.encog.ml.svm.SVM;
import org.encog.neural.networks.BasicNetwork;
import org.tdt4173.ocr.config.Config;
import org.tdt4173.ocr.gui.canvas.ImageCanvas;
import org.tdt4173.ocr.gui.facade.OCR_Facade;
import org.tdt4173.ocr.loader.Loader_ReadAll;
import org.tdt4173.ocr.loader.Loader_ReadAll_FeatureExtract;
import org.tdt4173.ocr.loader.Loader_ReadAll_NN;
import org.tdt4173.ocr.loader.Loader_ReadDemoImg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class OCR {
    private JButton loadAndNormalize;
    private JButton loadAndFE;
    private JButton learnSvm;
    private JButton learnKnn;
    private JButton testLeft;

    JButton testMiddle;
    private ImageCanvas imageCanvas;

    private JButton loadAndNormalizeNN;
    private JButton learnNN;
    private JButton testNN;
    private JButton loadAndNormalizeDemo;
    private JButton testDemo;

    private JTextArea textArea;


    private double TRAIN_INPUT[][];
    private double TRAIN_IDEAL[][];
    private double TEST_INPUT[][];
    private double TEST_IDEAL[][];
    private SVM svm;
    // create a neural network
    private BasicNetwork network;

    double TEST_INPUT_DEMO[][];
    double TEST_IDEAL_DEMO[][];

    public OCR() {
        initGui();
    }

    private void initGui() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        /** TOP PANEL */
        JPanel top = new JPanel();
        top.setBackground(Color.cyan);
        top.setBorder(BorderFactory.createEtchedBorder());
        JLabel header = new JLabel("Optical Character Recognition");
        header.setFont(new Font("Serif", Font.ITALIC, 14));
        top.add("North", header);
        // add top panel
        mainPanel.add(top, BorderLayout.PAGE_START);


        /** LEFT PANEL */
        /**************************************************/
        JPanel left = new JPanel();
        left.setBackground(Color.getHSBColor(0, 216, 251));
        left.setBorder(BorderFactory.createEtchedBorder());
        left.setLayout(new BorderLayout());

        // first control block of left panel
        loadAndNormalize = new JButton("Load & Normalize");
        loadAndNormalize.setEnabled(true);
        loadAndNormalize.setMargin(new Insets(5, 5, 5, 5));
        loadAndNormalize.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener loadAndNormalizeListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAndNormalize.setEnabled(false);
                loadAndFE.setEnabled(false);
                loadAndNormalize();
                loadAndFE.setEnabled(true);
                learnSvm.setEnabled(true);
                learnKnn.setEnabled(true);
            }
        };
        loadAndNormalize.addActionListener(loadAndNormalizeListener);

        loadAndFE = new JButton("Load & Feature Extract");
        loadAndFE.setEnabled(true);
        loadAndFE.setMargin(new Insets(5, 5, 5, 5));
        loadAndFE.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener loadAndFEListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAndFE.setEnabled(false);
                loadAndNormalize.setEnabled(false);
                loadAndFE();
                loadAndNormalize.setEnabled(true);
                learnSvm.setEnabled(true);
                learnKnn.setEnabled(true);
            }
        };
        loadAndFE.addActionListener(loadAndFEListener);

        JPanel control = new JPanel();
        control.setLayout(new FlowLayout(FlowLayout.CENTER));
        control.setBackground(Color.getHSBColor(0, 216, 251));
        control.add(loadAndNormalize);
        control.add(loadAndFE);
        control.setBorder(BorderFactory.createEtchedBorder());
        left.add(control, BorderLayout.NORTH);

        // second control block of left panel
        learnSvm = new JButton("Learn SVM");
        learnSvm.setEnabled(false);
        learnSvm.setMargin(new Insets(5, 5, 5, 5));
        learnSvm.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener learnSvmListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                learnSvm.setEnabled(false);
                learnKnn.setEnabled(false);
                learnSvm();
                learnKnn.setEnabled(true);
                testLeft.setEnabled(true);
                testMiddle.setEnabled(true);
            }
        };
        learnSvm.addActionListener(learnSvmListener);

        learnKnn = new JButton("Learn KNN");
        learnKnn.setEnabled(false);
        learnKnn.setMargin(new Insets(5, 5, 5, 5));
        learnKnn.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener learnKnnListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                learnKnn.setEnabled(false);
                learnKnn();
                learnSvm.setEnabled(true);
            }
        };
        learnKnn.addActionListener(learnKnnListener);

        // second control block of left panel
        JPanel control1 = new JPanel();
        control1.setLayout(new FlowLayout(FlowLayout.CENTER));
        control1.setBackground(Color.getHSBColor(0, 216, 251));
        control1.add(learnSvm);
        control1.add(learnKnn);
        control1.setBorder(BorderFactory.createEtchedBorder());
        left.add(control1, BorderLayout.CENTER);

        testLeft = new JButton("Testing");
        testLeft.setEnabled(false);
        testLeft.setMargin(new Insets(5, 5, 5, 5));
        testLeft.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener testLeftListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testLeft.setEnabled(false);
                testLeft();
                testLeft.setEnabled(true);
            }
        };
        testLeft.addActionListener(testLeftListener);

        JPanel control2 = new JPanel();
        control2.setLayout(new FlowLayout(FlowLayout.CENTER));
        control2.setBackground(Color.getHSBColor(0, 216, 251));
        control2.add(testLeft);
        control2.setBorder(BorderFactory.createEtchedBorder());
        left.add(control2, BorderLayout.SOUTH);
        // add left panel
        mainPanel.add(left, BorderLayout.WEST);


        /** Middle PANEL */
        JPanel middle = new JPanel();
        middle.setBackground(Color.getHSBColor(0, 216, 251));
        middle.setBorder(BorderFactory.createEtchedBorder());
        middle.setLayout(new BorderLayout());

        testMiddle = new JButton("Testing");
        testMiddle.setEnabled(false);
        testMiddle.setMargin(new Insets(5, 5, 5, 5));
        testMiddle.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener testMiddleListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testDraw();
            }
        };
        testMiddle.addActionListener(testMiddleListener);

        JButton clearMiddle = new JButton("Clear");
        clearMiddle.setMargin(new Insets(5, 5, 5, 5));
        clearMiddle.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener clearMiddleListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imageCanvas.clear();
            }
        };
        clearMiddle.addActionListener(clearMiddleListener);

        JPanel control3 = new JPanel();
        control3.setBackground(Color.getHSBColor(0, 216, 251));
        control3.setLayout(new FlowLayout(FlowLayout.CENTER));
        control3.add(testMiddle);
        control3.add(clearMiddle);
        control3.setBorder(BorderFactory.createEtchedBorder());
        middle.add(control3, BorderLayout.NORTH);

        imageCanvas = new ImageCanvas();
        imageCanvas.setPreferredSize(new Dimension(150, 150)); //130 180

        JPanel canvasWrapper = new JPanel();
        canvasWrapper.setBackground(Color.getHSBColor(0, 216, 251));
        canvasWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        canvasWrapper.add(imageCanvas, FlowLayout.LEFT);
        control3.setBorder(BorderFactory.createEtchedBorder());
        middle.add(canvasWrapper, BorderLayout.CENTER);
        // add middle panel
        mainPanel.add(middle, BorderLayout.CENTER);


        /** RIGHT PANEL  */
        /**************************************************/
        JPanel right = new JPanel();
        right.setBackground(Color.getHSBColor(0, 216, 251));
        right.setBorder(BorderFactory.createEtchedBorder());
        right.setLayout(new BorderLayout());

        // right north
        loadAndNormalizeNN = new JButton("Load & Normalize For NN");
        loadAndNormalizeNN.setEnabled(true);
        loadAndNormalizeNN.setMargin(new Insets(5, 5, 5, 5));
        loadAndNormalizeNN.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener loadAndNormalizeNNListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAndNormalizeNN.setEnabled(false);
                loadAndNormalizeNN();
                learnNN.setEnabled(true);
            }
        };
        loadAndNormalizeNN.addActionListener(loadAndNormalizeNNListener);

        learnNN = new JButton("Learn NN");
        learnNN.setEnabled(false);
        learnNN.setMargin(new Insets(5, 5, 5, 5));
        learnNN.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener learnNnListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                learnNN.setEnabled(false);
                learnNN();
                testNN.setEnabled(true);
            }
        };
        learnNN.addActionListener(learnNnListener);

        testNN = new JButton("Test NN");
        testNN.setEnabled(false);
        testNN.setMargin(new Insets(5, 5, 5, 5));
        testNN.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener testNNListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testNN.setEnabled(false);
                testNN();
                loadAndNormalizeDemo.setEnabled(true);
            }
        };
        testNN.addActionListener(testNNListener);

        JPanel rightNorthControl = new JPanel();
        rightNorthControl.setBackground(Color.getHSBColor(0, 216, 251));
        rightNorthControl.setLayout(new FlowLayout(FlowLayout.CENTER));
        rightNorthControl.add(loadAndNormalizeNN);
        rightNorthControl.add(learnNN);
        rightNorthControl.add(testNN);
        rightNorthControl.setBorder(BorderFactory.createEtchedBorder());
        right.add(rightNorthControl, BorderLayout.NORTH);

        // right center
        ImageIcon image = new ImageIcon("././demoImg/demo.png");
        JLabel demoImage = new JLabel("", image, JLabel.CENTER);

        JPanel rightCenterControl = new JPanel();
        rightCenterControl.setBackground(Color.getHSBColor(0, 216, 251));
        rightCenterControl.setLayout(new FlowLayout(FlowLayout.CENTER));
        rightCenterControl.add(demoImage);
        rightCenterControl.setBorder(BorderFactory.createEtchedBorder());
        right.add(rightCenterControl, BorderLayout.CENTER);

        // right south
        loadAndNormalizeDemo = new JButton("Load Demo & Normalize");
        loadAndNormalizeDemo.setEnabled(false);
        loadAndNormalizeDemo.setMargin(new Insets(5, 5, 5, 5));
        loadAndNormalizeDemo.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener loadAndNormalizeDemoListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAndNormalizeDemo.setEnabled(false);
                loadDemoAndNormalize();
                testDemo.setEnabled(true);
            }
        };
        loadAndNormalizeDemo.addActionListener(loadAndNormalizeDemoListener);

        testDemo = new JButton("Test Demo");
        testDemo.setEnabled(false);
        testDemo.setMargin(new Insets(5, 5, 5, 5));
        testDemo.setFont(new Font("Serif", Font.ITALIC, 12));
        ActionListener testRightListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testDemo.setEnabled(false);
                testDemo();
            }
        };
        testDemo.addActionListener(testRightListener);

        JPanel rightSouthControl = new JPanel();
        rightSouthControl.setBackground(Color.getHSBColor(0, 216, 251));
        rightSouthControl.setLayout(new FlowLayout(FlowLayout.CENTER));
        rightSouthControl.add(loadAndNormalizeDemo);
        rightSouthControl.add(testDemo);
        rightSouthControl.setBorder(BorderFactory.createEtchedBorder());
        right.add(rightSouthControl, BorderLayout.SOUTH);

        // add Right panel
        mainPanel.add(right, BorderLayout.EAST);


        /** BOTTOM PANEL  */
        JPanel bottom = new JPanel();
        //bottom.setBackground(Color.cyan);
        bottom.setBorder(BorderFactory.createEtchedBorder());
        JLabel console = new JLabel("Console");
        console.setFont(new Font("Serif", Font.ITALIC, 14));
        bottom.add(console, BorderLayout.NORTH);

        textArea = new JTextArea(10, 80); //74 75
        textArea.setMargin(new Insets(5, 5, 5, 5));
        textArea.setFont(new Font("Serif", Font.ITALIC, 12));
        // Get the default system.out
        final PrintStream sysOut = System.out;
        // Replace System.out with our own PrintStream
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) throws
                    IOException {
                // write to the text area
                textArea.append(String.valueOf((char) b));
                // write to the default System.out
                sysOut.write(b);
            }
        }));
        bottom.add(new JScrollPane(textArea), BorderLayout.CENTER);
        // add middle panel
        mainPanel.add(bottom, BorderLayout.PAGE_END);

        /** Main Frame **/
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setResizable(false);
        mainFrame.setSize(750, 510); //510
        mainFrame.setLocation(250, 50);
        mainFrame.setVisible(true);
    }

    private void prepareLoad() {
        int TOTAL_TRAIN_SIZE = 0;
        int TOTAL_TEST_SIZE = 0;
        for (int aDATA_SIZE : Config.DATA_SIZE) {
            int count = aDATA_SIZE / 2;
            TOTAL_TRAIN_SIZE += count;
            TOTAL_TEST_SIZE += aDATA_SIZE - count;
        }
        System.out.println("Specified Total Train Size: " + TOTAL_TRAIN_SIZE);
        System.out.println("Specified Total Test Size: " + TOTAL_TEST_SIZE);


        TRAIN_INPUT = new double[TOTAL_TRAIN_SIZE][];
        TRAIN_IDEAL = new double[TOTAL_TRAIN_SIZE][];

        TEST_INPUT = new double[TOTAL_TEST_SIZE][];
        TEST_IDEAL = new double[TOTAL_TEST_SIZE][];
    }

    private void loadAndNormalize() {
        prepareLoad();
        Loader_ReadAll.loadAllData(TRAIN_INPUT, TRAIN_IDEAL, TEST_INPUT, TEST_IDEAL);
    }

    private void loadAndFE() {
        prepareLoad();
        Loader_ReadAll_FeatureExtract.loadAllData(TRAIN_INPUT, TRAIN_IDEAL, TEST_INPUT, TEST_IDEAL);
    }

    private void learnSvm() {
        // create a SVM for classification, change false to true for regression.
        // For regression it will use an epsilon support vector. Both types will use an RBF kernel.
        svm = new SVM(33, false);
        OCR_Facade.learnSvm(TRAIN_INPUT, TRAIN_IDEAL, svm);
    }

    private void learnKnn() {
        OCR_Facade.learnTestKnn(TEST_INPUT, TEST_IDEAL, TRAIN_INPUT, TRAIN_IDEAL);
    }

    private void testLeft() {
        OCR_Facade.testSVM(TEST_INPUT, TEST_IDEAL, svm);
    }

    private void testDraw() {
        OCR_Facade.testDraw(imageCanvas.getInputSpace(), svm);
    }


    private void loadAndNormalizeNN() {
        System.out.println("\nLoad & Normalize For Neural Network ...");
        prepareLoad();
        Loader_ReadAll_NN.loadAllData(TRAIN_INPUT, TRAIN_IDEAL, TEST_INPUT, TEST_IDEAL);
    }

    private void learnNN() {
        // create a neural network
        network = new BasicNetwork();
        OCR_Facade.learnNN(TRAIN_INPUT, TRAIN_IDEAL, network, 1, 200);
    }

    private void testNN() {
        OCR_Facade.testNN(TEST_INPUT, TEST_IDEAL, network);
    }

    private void loadDemoAndNormalize() {
        TEST_INPUT_DEMO = new double[(Config.DEMO_HEIGHT - Config.LETTER_HEIGHT) * (Config.DEMO_WIDTH - Config.LETTER_WIDTH)][];
        TEST_IDEAL_DEMO = new double[(Config.DEMO_HEIGHT - Config.LETTER_HEIGHT) * (Config.DEMO_WIDTH - Config.LETTER_WIDTH)][];
        Loader_ReadDemoImg.loadDemo(TEST_INPUT_DEMO, TEST_IDEAL_DEMO);
    }

    private void testDemo() {
        OCR_Facade.testDemo(TEST_INPUT_DEMO, TEST_IDEAL_DEMO, network);
    }
}
