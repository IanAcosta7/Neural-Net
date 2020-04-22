package com.company.neuralnet.views;

import com.company.neuralnet.IPerceptron;
import com.company.neuralnet.Perceptron;
import com.company.neuralnet.components.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class Debug extends Frame implements IPerceptron {

    // DEBUGGING ATTRIBUTES
    private int nanosDelay;

    // PERCEPTRON ATTRIBUTES
    private Perceptron per;
    private boolean state;
    private int[] perData;


    // CONSTRUCTOR
    public Debug (int nanosDelay) {
        super("Debug");

        this.nanosDelay = nanosDelay;
        per = null;
        state = false;
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


        if (per.getCurrentInputs() != null && per.getWeights() != null && !Double.isNaN(per.getCurrentOutput())) {
            int previousHeight = 0;
            int inputAmount = per.getCurrentInputs().length;

            //for (Perceptron perceptron : per) { Used when we have an array
            for (int d = 0; d < 1; d++) {
                // DATA COMPONENTS
                Data data = new Data(0, previousHeight, inputAmount, 20);

                // Draw inputs
                for (int i = 0; i < data.getInputStrings().size(); i++) {
                    g.drawString(formatter.format(per.getCurrentInputs()[i]), data.getInputStrings().get(i).get("x"), data.getInputStrings().get(i).get("y"));
                }

                // Draw weights
                for (int i = 0; i < data.getWeightStrings().size(); i++) {
                    g.drawString(formatter.format(per.getWeights()[i]), data.getWeightStrings().get(i).get("x"), data.getWeightStrings().get(i).get("y"));
                }

                // Draw output
                g.drawString(formatter.format(per.getCurrentOutput()), data.getOutputString().get("x"), data.getOutputString().get("y"));

                // DATA SCHEMA
                Scheme scheme = new Scheme(data.getWidth(), 0, inputAmount, 20);

                // Draw inputs
                for (int i = 0; i < inputAmount; i++) {
                    g.drawLine(scheme.getInputPoints().get(i).get("x1"), scheme.getInputPoints().get(i).get("y1"), scheme.getInputPoints().get(i).get("x2"), scheme.getInputPoints().get(i).get("y2"));
                }

                // Draw Output
                g.drawLine(scheme.getOutputsPoint().get("x1"), scheme.getOutputsPoint().get("y1"), scheme.getOutputsPoint().get("x2"), scheme.getOutputsPoint().get("y2"));

                // Draw perceptron
                if (state)
                    g.setColor(Color.RED);
                else
                    g.setColor(Color.GREEN);
                g.fillOval(scheme.getPerceptronOvals().get("x"), scheme.getPerceptronOvals().get("y"), scheme.getPerceptronOvals().get("width"), scheme.getPerceptronOvals().get("height"));
                g.setColor(Color.BLACK);
            }
        }

        // ITERATIONS COMPONENT
        /*if (per.getIterations() >= 0 && per.getCurrentIteration() >= 0) {
            g.drawString(per.getCurrentIteration() + " / " + per.getIterations(), 160, 20);
        }*/

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

    @Override
    public void setState (Perceptron p, boolean state) {
        this.state = state;
        wait(frame::repaint);
    }
}
