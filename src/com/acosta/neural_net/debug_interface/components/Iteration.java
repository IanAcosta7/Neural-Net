package com.acosta.neural_net.debug_interface.components;

import com.acosta.neural_net.perceptron.Perceptron;

import java.awt.*;

public class Iteration extends Component{

    // ATTRIBUTES
    private int xPointer;
    private int yPointer;
    private int separation;
    private Perceptron perceptron;

    // CONSTRUCTOR
    public Iteration (int x, int y, int size, Graphics g) {
        super(x, y, 0, 0, g);
        this.separation = size * 10;

        this.xPointer = x + separation;
        this.yPointer = y + separation;
    }

    @Override
    public void draw() {

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
