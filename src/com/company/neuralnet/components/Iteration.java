package com.company.neuralnet.components;

import com.company.neuralnet.Perceptron;

import java.awt.*;

public class Iteration extends Component{

    // ATTRIBUTES
    private int xPointer;
    private int yPointer;
    private int separation;
    private Graphics g;
    private Perceptron perceptron;

    // CONSTRUCTOR
    public Iteration (int x, int y, int size, Graphics graphics) {
        super(x, y, 0, 0);
        this.separation = size * 10;
        this.g = graphics;
        this.xPointer = x + separation;
        this.yPointer = y + separation;
    }


    // SETTERS
    //TODO: NEED TO IMPLEMENT THIS
    /*
    public void setPerceptron (Perceptron per) {
        perceptron = per;
        drawIterations();
    }

    // METHODS
    private void drawIterations () {
        if (perceptron.getCurrentIteration() > 0) {
            g.drawString(perceptron.getCurrentIteration() + " / " + perceptron.getIterations(), xPointer, yPointer);
        }
    }
    */
}
