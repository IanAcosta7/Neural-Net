package com.company.neuralnet.components;

import com.company.neuralnet.Perceptron;

import java.awt.*;

public class Scheme extends Component {
    // ATTRIBUTES
    private int xPointer;
    private int yPointer;
    private int separation;
    private Graphics g;
    private Perceptron perceptron;
    private boolean state;


    // CONSTRUCTOR
    public Scheme (int x, int y, int size, Graphics graphics) {
        super(x, y, 0, 0);

        size *= 10;
        this.xPointer = x + size;
        this.yPointer = y + size;
        this.separation = size;
        this.g = graphics;
        this.state = false;
    }


    //SETTERS
    public void setPerceptron (Perceptron per) {
        this.perceptron = per;
        if (per.getCurrentInputs() != null) {
            super.setWidth(separation * 9);
            super.setHeight(separation * per.getCurrentInputs().length);
        }
        drawScheme();
    }

    public void setState(boolean state) {
        this.state = state;
        drawScheme();
    }


    // METHODS
    public void drawScheme () {
        if (perceptron.getCurrentInputs() != null && perceptron.getWeights() != null && !Double.isNaN(perceptron.getCurrentOutput())) {
            for (int i = 0; i < perceptron.getCurrentInputs().length; i++) {
                // Draw Inputs
                if (perceptron.getCurrentInputs()[i] > 0.5)
                    g.setColor(Color.GREEN);
                else
                    g.setColor(Color.BLACK);
                g.drawLine(xPointer, yPointer + (i * separation), xPointer + separation * 4, (yPointer + super.getHeight() / 2) - separation / 2);
            }
            // Draw Output
            if (perceptron.getCurrentOutput() > 0.5)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.BLACK);
            g.drawLine(xPointer + separation * 4, (yPointer + super.getHeight() / 2) - separation / 2, xPointer + separation * 8, (yPointer + super.getHeight() / 2) - separation / 2);

            // Draw perceptron
            if (state)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.RED);
            g.fillOval((xPointer + separation * 4) - (separation * 2) / 2, ((yPointer + super.getHeight() / 4) - separation / 4) - (super.getHeight() / 4 - separation / 4), separation * 2, separation * 2);
            g.setColor(Color.BLACK);
        }
    }
}
