package com.company.neuralnet.view;

import com.company.neuralnet.IPerceptron;
import com.company.neuralnet.Perceptron;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class Debug extends Frame implements IPerceptron {

    private double[] inputs;
    private double output;
    private double[] weights;

    public Debug () {
        super("Debug");

        inputs = null;
        //output = Double.NaN;
        weights = null;

        frame.getContentPane().add(this);
        frame.pack();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void draw () {
        /*for (int i = 0; i < net.getIterations(); i++) {
            net.practice();
            bars = net.getWeights();
            frame.repaint();
        }*/
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


        /*for (int i = 0; i < net.getInputs().length; i++) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect( i * 50 + 20, frame.getHeight() - 160, 30, 100);

            if (bars[i] < 0)
                g.setColor(new Color(-(int)bars[i]*25, -(int)bars[i]*25, 0));
            else
                g.setColor(new Color((int)bars[i]*25, 0, 0));
            System.out.println(bars[i]);
            g.fillRect(i * 50 + 20, frame.getHeight() - 110, 30, (int)-bars[i]);
        }*/

    }

    @Override
    public void showInputs (double[] currentI) {
        this.inputs = currentI;
        frame.repaint();
    }

    @Override
    public void showOutput (double currentO) {
        this.output = currentO;
        frame.repaint();
    }

    @Override
    public void showWeights (double[] w) {
        this.weights = w;
        frame.repaint();
    }
}
