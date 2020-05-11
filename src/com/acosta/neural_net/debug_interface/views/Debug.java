package com.acosta.neural_net.debug_interface.views;

import com.acosta.neural_net.NeuralNet;
import com.acosta.neural_net.INeuralNet;
import com.acosta.neural_net.perceptron.Node;
import com.acosta.neural_net.debug_interface.components.*;
import com.acosta.neural_net.perceptron.Perceptron;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Debug extends Frame implements INeuralNet {

    // DEBUGGING ATTRIBUTES
    private int nanosDelay;

    // PERCEPTRON ATTRIBUTES
    //private ArrayList<Perceptron> perceptrons;
    //private ArrayList<Boolean> states;
    private NeuralNet net;


    // CONSTRUCTOR
    public Debug (int nanosDelay) {
        super("Debug");

        this.nanosDelay = nanosDelay;
        this.net = null;
        //perceptrons = new ArrayList<>();
        //states = new ArrayList<>();

        frame.getContentPane().add(this);
        frame.pack();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    // METHODS
    private void draw (Graphics g) {
        int previousDataHeight = 0;
        int previousDataWidth = 0;

        int previousSchemeHeight = 5000;
        int previousSchemeWidth = 0;

        if (net != null) {
            for (int i = 0; i < net.getLAYERS().size(); i++) {
                for (int j = 0; j < net.getLAYERS().get(i).size(); j++) {
                    Node node = net.getLAYERS().get(i).get(j);

                    // DRAW A DATA COMPONENT
                    Data data = new Data(i * previousDataWidth, j * previousDataHeight, 2, g);
                    data.setPerceptron(node);
                    previousDataHeight = data.getHeight();
                    previousDataWidth = data.getWidth();

                    // DRAW A SCHEME COMPONENT
                    Scheme scheme = new Scheme((i + 1) * previousSchemeHeight, (j + 1) * previousSchemeWidth, 2, g);
                    scheme.setPerceptron(node);
                    //scheme.setState(states.get(perceptrons.indexOf(perceptron)));
                    scheme.setState(true);
                    previousSchemeHeight = scheme.getHeight();
                    previousSchemeWidth = scheme.getWidth();

                    // DRAW AN ITERATION COMPONENT
                    // TODO: ITERATION COMPONENT
                    /*
                    Iteration iteration = new Iteration(scheme.getX() + scheme.getWidth() / 3, scheme.getY(), 1, g);
                    iteration.setPerceptron(perceptron);
                     */
                }
            }
        }
    }


    // OVERWRITTEN METHODS
    @Override
    public void paintComponent(Graphics g) {
        // Here we can draw things
        draw(g);
    }

    @Override
    public void updatePerceptron(Perceptron p, boolean addDelay) {
        for (ArrayList<Node> layer : net.getLAYERS()) {
            for (int i = 0; i < layer.size(); i++) {
                if (layer.get(i).equals(p))
                    layer.get(i).fromPerceptron(p);
            }
        }

        if (addDelay)
            wait(frame::repaint);
    }

    @Override
    public void updateNode (Node n, boolean addDelay) {
        if (net.getNode(n.getNAME()) != null)
            net.setNode(n.getNAME(), n);

        if (addDelay)
            wait(frame::repaint);
    }

    @Override
    public void setState(Node n, boolean state) {
        //this.states.set(perceptrons.indexOf(p), state);
        //wait(frame::repaint);
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
    public void updateNeuralNet(NeuralNet nn, boolean addDelay) {
        this.net = nn;

        if (addDelay)
            wait(frame::repaint);
    }
}
