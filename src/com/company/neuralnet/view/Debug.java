package com.company.neuralnet.view;

import com.company.neuralnet.IPerceptron;
import com.company.neuralnet.Perceptron;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Debug extends Frame implements IPerceptron {

    // Debugging Attributes
    private int nanosDelay;

    private Perceptron perceptron;

    /*
    private double[] inputs;
    private double output;
    private double[] weights;
    private int iterations;
    private int currentIterations;*/

    public Debug (int nanosDelay) {
        super("Debug");

        this.nanosDelay = nanosDelay;

        /*
        inputs = null;
        //output = Double.NaN;
        weights = null;
        iterations = -1;
        currentIterations = -1;
         */

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

        if (perceptron.getCurrentInputs() != null) {
            for (int i = 0; i < perceptron.getCurrentInputs().length; i++) {
                g.drawString(formatter.format(perceptron.getCurrentInputs()[i]), 20, 20 * i + 20);
            }
        }

        if (!Double.isNaN(perceptron.getCurrentOutput())) {
            g.drawString(formatter.format(perceptron.getCurrentOutput()), 100, 20);
        }

        if (perceptron.getWeights() != null) {
            for (int i = 0; i < perceptron.getWeights().length; i++) {
                g.drawString(formatter.format(perceptron.getWeights()[i]), 60, 20 * i + 20);
            }
        }

        if (perceptron.getIterations() >= 0 && perceptron.getCurrentIteration() >= 0) {
            g.drawString(perceptron.getCurrentIteration() + " / " + perceptron.getIterations(), 160, 20);
        }
    }

    @Override
    public void updatePerceptron (Perceptron p, boolean addDelay) {
        perceptron = p;
        if (addDelay)
            wait(frame::repaint);
    }

    @Override
    public void wait(Runnable callback) {
        try {
            //TimeUnit.NANOSECONDS.wait(nanosDelay);
            TimeUnit.MILLISECONDS.sleep(nanosDelay);
            callback.run();
        } catch (Exception e){
            //Thread.currentThread().notify()
            Thread.currentThread().interrupt();
        }

        //final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //executorService.scheduleAtFixedRate(callback, 0, nanosDelay, TimeUnit.NANOSECONDS);
    }
}
