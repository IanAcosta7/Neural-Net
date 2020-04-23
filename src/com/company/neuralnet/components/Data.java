package com.company.neuralnet.components;

import com.company.neuralnet.Perceptron;

import java.awt.*;
import java.text.DecimalFormat;

public final class Data extends Component {
    // ATTRIBUTES
    private int xPointer;
    private int yPointer;
    private int separation;
    private Graphics g;
    private Perceptron perceptron;

    // CONSTRUCTOR
    public Data(int x, int y, int size, Graphics graphics) {
        super(x, y, 0, 0);

        size *= 10;
        this.xPointer = x + size;
        this.yPointer = y + size;
        this.separation = size;
        this.g = graphics;
    }


    // SETTERS
    public void setPerceptron(Perceptron per) {
        this.perceptron = per;
        if (per.getCurrentInputs() != null) {
            super.setWidth(separation * 7);
            super.setHeight(separation * per.getCurrentInputs().length);
        }
        drawData();
    }


    // METHODS
    private void drawData () {
        DecimalFormat formatter = new DecimalFormat("0.00");

        if (perceptron.getCurrentInputs() != null && perceptron.getWeights() != null && !Double.isNaN(perceptron.getCurrentOutput())) {
            for (int i = 0; i < perceptron.getCurrentInputs().length; i++) {
                // Draw inputs
                g.drawString(formatter.format(perceptron.getCurrentInputs()[i]), xPointer, yPointer + i * separation);

                // Draw weights
                g.drawString(formatter.format(perceptron.getWeights()[i]), xPointer + separation * 2, yPointer + i * separation);
            }

            // Draw output
            g.drawString(formatter.format(perceptron.getCurrentOutput()), xPointer + separation * 4, yPointer);
        }
    }
}
