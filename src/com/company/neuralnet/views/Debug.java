package com.company.neuralnet.views;

import com.company.neuralnet.IPerceptron;
import com.company.neuralnet.Perceptron;
import com.company.neuralnet.components.Data;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class Debug extends Frame implements IPerceptron {

    // DEBUGGING ATTRIBUTES
    private int nanosDelay;

    // PERCEPTRON ATTRIBUTES
    private Perceptron per;
    private int[] perData;


    // CONSTRUCTOR
    public Debug (int nanosDelay) {
        super("Debug");

        this.nanosDelay = nanosDelay;
        per = null;
        perData = null;

        frame.getContentPane().add(this);
        frame.pack();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    // METHODS
    private void drawData (Graphics g) {
        DecimalFormat formatter = new DecimalFormat("0.00");

        // DATA COMPONENTS
        if (per.getCurrentInputs() != null && per.getWeights() != null && !Double.isNaN(per.getCurrentOutput())) {
            Data strings = new Data(0, 0, per.getCurrentInputs().length, 20, 1);

            // DRAW INPUTS
            for (int i = 0; i < per.getCurrentInputs().length; i++) {
                g.drawString(formatter.format(per.getCurrentInputs()[i]), 20, 20 * i + 20);
            }

            // DRAW WEIGHTS
            for (int i = 0; i < per.getWeights().length; i++) {
                g.drawString(formatter.format(per.getWeights()[i]), 60, 20 * i + 20);
            }

            // DRAW OUTPUT
            g.drawString(formatter.format(per.getCurrentOutput()), 100, 20);
        }

        // ITERATIONS COMPONENT
        if (per.getIterations() >= 0 && per.getCurrentIteration() >= 0) {
            g.drawString(per.getCurrentIteration() + " / " + per.getIterations(), 160, 20);
        }
    }

    public void drawNet (Graphics g) {
        if (per != null) {
            if (per.getCurrentInputs() != null) {
                for (int i = 0; i < per.getCurrentInputs().length; i++) {
                    //g.drawOval();
                }
            }
        }
    }


    // OVERWRITTEN METHODS
    @Override
    public void paintComponent(Graphics g) {
        // Here we can draw things
        drawData(g);
        drawNet(g);
    }

    @Override
    public void updatePerceptron (Perceptron p, boolean addDelay) {
        per = p;

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
