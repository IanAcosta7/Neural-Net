package com.company.neuralnet;

import java.util.Random;

public final class Perceptron {

    // ATTRIBUTES
    private int iterations;
    private double[][] inputs;
    private double[] outputs;
    private double[] weights;


    public Perceptron (int iterations) {
        this.iterations = iterations;
    }


    // GETTERS
    public int getIterations () { return iterations; }

    public double[][] getInputs () { return inputs; }

    public double[] getOutputs () { return outputs; }

    public double[] getWeights () { return weights; }


    // SETTERS
    public void setInputs (double[][] inputs) {
        this.inputs = inputs;
        setRandomWeights();
    }

    public void setOutputs (double[] outputs) {
        this.outputs = outputs;
    }

    public void setWeights (double[] weights) {
        this.weights = weights;
    }

    public void setIterations (int iterations) {
        this.iterations = iterations;
    }


    // METHODS
    public void train () {
        if (inputs == null) throw new NullPointerException("inputs equals null");
        if (outputs == null) throw new NullPointerException("outputs equals null");

        for (int i = 0; i < iterations; i++) {
            double[] final_outputs  = think(inputs);

            adjustWeight(final_outputs);
        }
    }

    public double think (double[] inputs) {
        double output = 0;

        for (int i = 0; i < inputs.length; i++) {
            output += inputs[i] * weights[i];
        }

        return sigmoid(output); // sigmoid(x1*w1+x2*w2+x3*w3)
    }

    public double[] think (double[][] inputs) {
        double[] outputs = new double[inputs.length];

        if (weights != null) {
            for (int f = 0; f < inputs.length; f++) {
                for (int c = 0; c < inputs[0].length; c++) {
                    outputs[f] += inputs[f][c] * weights[c];
                }
                outputs[f] = sigmoid(outputs[f]); // sigmoid(x1*w1+x2*w2+x3*w3)
            }
        }

        return outputs;
    }

    public double sigmoid (double x) {
        // this is the sigmoid function "1 / (1 + exp(-x))", it normalizes the value received between 1 and -1
        return 1 / (1 + Math.exp(-x));
    }

    public double sigmoidDerivative (double x) {
        // Sigmoid derivative controls the learning rate
        return x * (1 - x);
    }

    private void adjustWeight (double[] lastOutputs) {
        double[] adjustments = new double[lastOutputs.length];

        for (int i = 0; i < lastOutputs.length; i++){
            double error = outputs[i] - lastOutputs[i];

            adjustments[i] = error * sigmoidDerivative(lastOutputs[i]);
        }

        for (int c = 0; c < inputs[0].length; c++) {
            for (int f = 0; f < inputs.length; f++) {
                weights[c] += inputs[f][c] * adjustments[f];
            }
        }
    }

    private void setRandomWeights () {
        // setRandomWeights generates n random numbers for the weights
        Random rand = new Random();
        double[] randomWeights = new double[inputs[0].length];

        for (int i = 0; i < randomWeights.length; i++) {
            randomWeights[i] = (2 * rand.nextDouble()) - 1;
        }

        setWeights(randomWeights);
    }
}
