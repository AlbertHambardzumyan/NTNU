package org.tdt4173.ocr.gui.canvas;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class ImageCanvas extends Canvas implements MouseListener, MouseMotionListener {
    Vector input;
    Vector output;
    int currentSample;
    int currentDigit;

    boolean erase;
    final static int WIDTH = 20;
    final static int HEIGHT = 20;

    public int[][] getInputSpace() {
        return inputSpace;
    }

    int inputSpace[][];

    public ImageCanvas() {
        int i, j;
        input = new Vector();
        output = new Vector();
        erase = false;
        inputSpace = new int[WIDTH][HEIGHT];
        for (i = 0; i < WIDTH; i++) {
            for (j = 0; j < HEIGHT; j++) {
                inputSpace[i][j] = 0;
            }
        }
        currentSample = 0;
        currentDigit = 0;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void paint(Graphics g) {
        int i, j;
        Rectangle r = getBounds();
        g.setColor(Color.white);
        g.fillRect(0, 0, r.width, r.height);
        g.setColor(Color.black); // frame
        g.drawRect(r.width / 8, 10, 3 * r.width / 4 + 1, r.height - 40 + 1);
        g.drawString("  Draw Char", r.width / 4, r.height - 10);
        for (i = 0; i < WIDTH; i++) {
            for (j = 0; j < HEIGHT; j++) {
                if (inputSpace[i][j] != 0) {
                    drawPoint(i, j, 1);
                }

            }
        }

    }

    public void drawPoint(int x, int y, int value) {
        Graphics g = getGraphics();
        Rectangle r = getBounds();
        int X = 1 + r.width / 8 + x * r.width / 20;
        int Y = 11 + y * (r.height - 40) / 20;
        int W = r.width / 20;
        int H = (r.height - 40) / 20;
        if (value == 1) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.white);
        }
        g.fillRect(X, Y, W, H);
    }

    public void clear() {
        int i, j;
        for (i = 0; i < WIDTH; i++) {
            for (j = 0; j < HEIGHT; j++) {
                inputSpace[i][j] = 0;
                drawPoint(i, j, 0);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        Rectangle r = getBounds();
        int x = e.getX();
        int y = e.getY();
        int X = (x - r.width / 8) * 20 / r.width;
        int Y = (y - 10) * 20 / (r.height - 40);
        if ((X >= 0) && (X < 15) && (Y >= 0) && (Y < 20)) {
            if (inputSpace[X][Y] == 0) {
                inputSpace[X][Y] = 1;
                erase = false;
            } else {
                inputSpace[X][Y] = 0;
                erase = true;
            }
            drawPoint(X, Y, inputSpace[X][Y]);
        }
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Rectangle r = getBounds();
        int X = (x - r.width / 8) * 20 / r.width;
        int Y = (y - 10) * 20 / (r.height - 40);
        if ((X >= 0) && (X < 15) && (Y >= 0) && (Y < 20)) {
            if (erase) {
                inputSpace[X][Y] = 0;
            } else {
                inputSpace[X][Y] = 1;
            }
            drawPoint(X, Y, inputSpace[X][Y]);
        }

    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }
}
