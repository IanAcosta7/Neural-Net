package com.acosta.neural_net.debug_interface.views;

import com.acosta.neural_net.NeuralNet;
import com.acosta.neural_net.debug_interface.INeuralNet;
import com.acosta.neural_net.perceptron.Node;
import com.acosta.neural_net.debug_interface.components.*;
import com.acosta.neural_net.perceptron.Perceptron;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Debug extends Frame implements INeuralNet {

    // DEBUGGING ATTRIBUTES
    private int nanosDelay;

    private NeuralNet net;


    // CONSTRUCTOR
    public Debug (int nanosDelay) {
        super("Debug");

        this.nanosDelay = nanosDelay;
        this.net = null;

        frame.setBackground(Color.BLACK);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    // METHODS
    private void draw (Graphics g) {

        if (net != null) {
            for (int i = 0; i < net.getLAYERS().size(); i++) {
                for (int j = 0; j < net.getLAYERS().get(i).size(); j++) {
                    g.setColor(Color.WHITE);

                    // DRAW A DATA COMPONENT
                    NetData netData = new NetData(0, 0, 4, g);
                    netData.setNeuralNet(net);
                    //netData.setDrawBorders(true);
                    netData.draw();

                    // DRAW A SCHEME COMPONENT
                    NetScheme netScheme = new NetScheme(netData.getWidth(), 0, 4, g);
                    netScheme.setNeuralNet(net);
                    //netScheme.setDrawBorders(true);
                    netScheme.draw();

                    // DRAW AN ITERATION COMPONENT
                    NetIteration netIteration = new NetIteration(netScheme.getX(), netScheme.getHeight() + 20, 4, g);
                    netIteration.setNeuralNet(net);
                    netIteration.draw();
                }
            }
        }
    }


    // SUPER METHODS
    @Override
    public void paintComponent(Graphics g) {
        // Here we can draw things
        draw(g);
    }

    // INTERFACE METHODS
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
    public void updateNeuralNet(NeuralNet nn, boolean addDelay) {
        this.net = nn;

        if (addDelay)
            wait(frame::repaint);
    }
}
