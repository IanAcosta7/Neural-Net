package com.company.neuralnet;

import java.util.Random;

public class NetPerceptron {

    public NetPerceptron () {
    }

    // METHODS
    public static double sigmoid (double x) {
        // this is the sigmoid function "1 / (1 + exp(-x))", it normalizes the value received between 1 and -1
        return 1 / (1 + Math.exp(-x));
    }

    public static double sigmoidDerivative (double x) {
        // Sigmoid derivative controls the learning rate
        return x * (1 - x);
    }


    //TODO: WILL NEED TO RETURN AN ARRAY OF OUTPUTS




    // METHODS FROM COMMON PERCEPTRON
    /*public Perceptron (int iterations) {
        this.iterations = iterations;
        // DEFAULT VALUES;
        this.inputs = null;
        this.outputs = null;
        this.weights = null;
        this.biasWeight = 0;
        this.currentIteration = -1;
        this.currentInputs = null;
        this.currentOutput = -1;

        this.iPer = null;
    }*/

    /*public Perceptron (int iterations, IPerceptron iPer) {
        this.iterations = iterations;
        // DEFAULT VALUES
        this.inputs = null;
        this.outputs = null;
        this.weights = null;
        this.biasWeight = 0;
        this.currentIteration = -1;
        this.currentInputs = null;
        this.currentOutput = -1;

        this.iPer = iPer;
        this.iPer.updatePerceptron(this, true);
    }*/

    /*public void setIterations (int iterations) {
        this.iterations = iterations;
    }*/

    /*public void train () {
        if (inputs == null) throw new NullPointerException("inputs equals null");
        if (outputs == null) throw new NullPointerException("outputs equals null");

        for (int i = 0; i < iterations; i++) {
            if (iPer != null) {
                currentIteration = i + 1;
                iPer.wait(this::practice);
                iPer.updatePerceptron(this, false);
            }
            else {
                practice();
            }
        }
    }*/

    /*public void practice() {
        double[] final_outputs  = think(inputs);

        correctPerceptron(final_outputs);
    }*/

    /*private void correctPerceptron (double[] lastOutputs) {
        double[] adjustments = new double[lastOutputs.length];

        for (int i = 0; i < lastOutputs.length; i++){
            double error = outputs[i] - lastOutputs[i];

            adjustments[i] = error * sigmoidDerivative(lastOutputs[i]);
        }

        adjustWeights(adjustments);
        adjustBiasWeights(adjustments);
    }*/

    /*public void adjustWeights(double[] adjustments) {
        for (int c = 0; c < inputs[0].length; c++) {
            for (int f = 0; f < inputs.length; f++) {
                double realAdjustment = inputs[f][c] * adjustments[f];
                weights[c] += realAdjustment;
            }

            if (iPer != null)
                iPer.updatePerceptron(this, false);
        }
    }*/
}
