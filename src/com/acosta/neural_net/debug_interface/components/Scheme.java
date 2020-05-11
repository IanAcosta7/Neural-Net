package com.acosta.neural_net.debug_interface.components;

import com.acosta.neural_net.perceptron.Perceptron;

import java.awt.*;

public class Scheme extends Component {
    // ATTRIBUTES
    private int xPointer;
    private int yPointer;
    private int separation;
    private Perceptron perceptron;
    private boolean state;


    // CONSTRUCTOR
    public Scheme (int x, int y, int size, Graphics g) {
        super(x, y, 0, 0, g);

        size *= 10;
        this.xPointer = x + size;
        this.yPointer = y + size;
        this.separation = size;
        this.state = false;
    }


    //SETTERS
    public void setPerceptron (Perceptron per) {
        this.perceptron = per;
        if (per.getTInputs() != null) {
            super.setWidth(separation * 9);
            super.setHeight(separation * per.getTInputs().length);
        }
        draw();
    }

    public void setState(boolean state) {
        this.state = state;
        draw();
    }


    // METHODS
    @Override
    public void draw() {
        if (perceptron.getTInputs() != null && perceptron.getWeights() != null && !Double.isNaN(perceptron.getOutput())) {
            for (int i = 0; i < perceptron.getTInputs().length; i++) {
                // Draw Inputs
                if (perceptron.getTInputs()[i] > 0.5)
                    g.setColor(Color.GREEN);
                else
                    g.setColor(Color.BLACK);
                g.drawLine(xPointer, yPointer + (i * separation), xPointer + separation * 4, (yPointer + super.getHeight() / 2) - separation / 2);
            }
            // Draw Output
            if (perceptron.getOutput() > 0.5)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.BLACK);
            g.drawLine(xPointer + separation * 4, (yPointer + super.getHeight() / 2) - separation / 2, xPointer + separation * 8, (yPointer + super.getHeight() / 2) - separation / 2);

            // Draw perceptron
            if (state)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.RED);
            g.fillOval((xPointer + separation * 4) - (separation * 2) / 2, ((yPointer + super.getHeight() / 2) - separation / 2) - separation, separation * 2, separation * 2);
            g.setColor(Color.BLACK);
        }
    }
}
