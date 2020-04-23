package com.company.neuralnet.components;

import com.company.neuralnet.Perceptron;

import java.awt.*;

public class Iteration extends Component{

    private int xPointer;
    private int yPointer;
    private Perceptron perceptron;
    private int separation;
    private Graphics g;

    public Iteration (int x, int y, int size, Graphics graphics) {
        super(x, y, 0, 0);
        this.separation = size * 10;
        this.g = graphics;
        this.xPointer = x + separation;
        this.yPointer = y + separation;
    }

    public void setPerceptron (Perceptron per) {
        perceptron = per;
        drawIterations();
    }

    private void drawIterations () {
        if (perceptron.getCurrentIteration() > 0) {
            g.drawString(perceptron.getCurrentIteration() + " / " + perceptron.getIterations(), xPointer, yPointer);
        }
    }
}
