package com.company.neuralnet.views;

import com.company.neuralnet.IPerceptron;
import com.company.neuralnet.Perceptron;
import com.company.neuralnet.components.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Debug extends Frame implements IPerceptron {

    // DEBUGGING ATTRIBUTES
    private int nanosDelay;

    // PERCEPTRON ATTRIBUTES
    private ArrayList<Perceptron> perceptrons;
    private ArrayList<Boolean> states;


    // CONSTRUCTOR
    public Debug (int nanosDelay) {
        super("Debug");

        this.nanosDelay = nanosDelay;
        perceptrons = new ArrayList<>();
        states = new ArrayList<>();

        frame.getContentPane().add(this);
        frame.pack();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    // METHODS
    private void draw (Graphics g) {
        int previousHeight = 0;

        for (Perceptron perceptron : perceptrons) {
            // DRAW A DATA COMPONENT
            Data data = new Data(0, previousHeight, 2, g);
            data.setPerceptron(perceptron);
            previousHeight = data.getHeight();

            // DRAW A SCHEME COMPONENT
            Scheme scheme = new Scheme(data.getWidth(), 0, 2, g);
            scheme.setPerceptron(perceptron);
            scheme.setState(states.get(perceptrons.indexOf(perceptron)));
        }

        // ITERATIONS COMPONENT
        /*if (per.getIterations() >= 0 && per.getCurrentIteration() >= 0) {
            g.drawString(per.getCurrentIteration() + " / " + per.getIterations(), 160, 20);
        }*/
    }


    // OVERWRITTEN METHODS
    @Override
    public void paintComponent(Graphics g) {
        // Here we can draw things
        draw(g);
    }

    @Override
    public void updatePerceptron (Perceptron p, boolean addDelay) {
        if (perceptrons.contains(p))
            perceptrons.set(perceptrons.indexOf(p), p);
        else {
            perceptrons.add(p);
            states.add(false);
        }

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
        this.states.set(perceptrons.indexOf(p), state);
        wait(frame::repaint);
    }
}
