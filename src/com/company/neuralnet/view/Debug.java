package com.company.neuralnet.view;

import com.company.neuralnet.IPerceptron;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Debug extends Frame implements IPerceptron {

    // Debugging Attributes
    private int microsDelay;

    private double[] inputs;
    private double output;
    private double[] weights;
    private int iterations;
    private int currentIterations;

    public Debug (int microsDelay) {
        super("Debug");

        this.microsDelay = microsDelay;

        inputs = null;
        //output = Double.NaN;
        weights = null;
        iterations = -1;
        currentIterations = -1;

        frame.getContentPane().add(this);
        frame.pack();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        // Here we can draw things
        DecimalFormat formatter = new DecimalFormat("0.00");

        if (inputs != null) {
            for (int i = 0; i < inputs.length; i++) {
                g.drawString(formatter.format(inputs[i]), 20, 20 * i + 20);
            }
        }

        if (!Double.isNaN(output)) {
            g.drawString(formatter.format(output), 100, 20);
        }

        if (weights != null) {
            for (int i = 0; i < weights.length; i++) {
                g.drawString(formatter.format(weights[i]), 60, 20 * i + 20);
            }
        }

        if (iterations >= 0 && currentIterations >= 0) {
            g.drawString(currentIterations + " / " + iterations, 160, 20);
        }
    }

    @Override
    public void showInputs (double[] currentI) {
        this.inputs = currentI;
        wait(frame::repaint);
    }

    @Override
    public void showOutput (double currentO) {
        this.output = currentO;
        wait(frame::repaint);
    }

    @Override
    public void showWeights (double[] w) {
        this.weights = w;
        wait(frame::repaint);
    }

    @Override
    public void showIterations(int currentI) {
        currentIterations = currentI;
        wait(frame::repaint);
    }

    @Override
    public void setIterations(int i) {
        iterations = i;
        frame.repaint();
    }

    @Override
    public void wait(Runnable callback) {
        try {
            callback.run();
            TimeUnit.MICROSECONDS.sleep(microsDelay);
        } catch (Exception e){
            Thread.currentThread().interrupt();
        }
    }
}
