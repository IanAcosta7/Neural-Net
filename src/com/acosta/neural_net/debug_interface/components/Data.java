package com.acosta.neural_net.debug_interface.components;

import com.acosta.neural_net.perceptron.Perceptron;

import java.awt.*;
import java.text.DecimalFormat;

public final class Data extends Component {
    // ATTRIBUTES
    private int xPointer;
    private int yPointer;
    private int separation;
    private Perceptron perceptron;

    // CONSTRUCTOR
    public Data(int x, int y, int size, Graphics g) {
        super(x, y, 0, 0, g);

        size *= 10;
        this.xPointer = x + size;
        this.yPointer = y + size;
        this.separation = size;

        this.perceptron = null;
    }


    // SETTERS
    public void setPerceptron(Perceptron per) {
        this.perceptron = per;
        if (per.getTInputs() != null) {
            super.setWidth(separation * 7);
            super.setHeight(separation * per.getTInputs().length + separation);
        }
        draw();
    }


    // METHODS
    @Override
    public void draw() {
        DecimalFormat formatter = new DecimalFormat("0.00");

        if (perceptron.getTInputs() != null && perceptron.getWeights() != null && !Double.isNaN(perceptron.getOutput())) {
            for (int i = 0; i < perceptron.getTInputs().length; i++) {
                // Draw inputs
                g.drawString(formatter.format(perceptron.getTInputs()[i]), xPointer, yPointer + i * separation);

                // Draw weights
                g.drawString(formatter.format(perceptron.getWeights()[i]), xPointer + separation * 2, yPointer + i * separation);
            }

            // Draw output
            g.drawString(formatter.format(perceptron.getOutput()), xPointer + separation * 4, yPointer);
        }

        drawBorders();
    }
}
