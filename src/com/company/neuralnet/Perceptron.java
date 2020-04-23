package com.company.neuralnet;

import java.util.Random;

public final class Perceptron {

    // ATTRIBUTES
    private int iterations;
    private double[][] inputs;
    private double[] outputs;
    private double[] weights;
    private double biasWeight;

    // DEBUGGING OPTIONAL ATTRIBUTES
    private int currentIteration;
    private double[] currentInputs;
    private double currentOutput;
    private IPerceptron iPer;


    public Perceptron (int iterations) {
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
    }

    public Perceptron (int iterations, IPerceptron iPer) {
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
    }


    // GETTERS
    public int getIterations () { return iterations; }

    public double[][] getInputs () { return inputs; }

    public double[] getOutputs () { return outputs; }

    public double[] getWeights () { return weights; }

    public double getBiasWeight() {
        return biasWeight;
    }

    public double[] getCurrentInputs () { return currentInputs; }

    public int getCurrentIteration () {
        return currentIteration;
    }

    public double getCurrentOutput () {
        return currentOutput;
    }


    // SETTERS
    public void setInputs (double[][] inputs) {
        this.inputs = inputs;
        setRandomWeights();
        setRandomBiasWeights();
    }

    public void setOutputs (double[] outputs) {
        this.outputs = outputs;
    }

    public void setWeights (double[] weights) {
        this.weights = weights;
    }

    public void setBiasWeight(double biasWeight) {
        this.biasWeight = biasWeight;
    }

    public void setIterations (int iterations) {
        this.iterations = iterations;
    }


    // METHODS
    public void train () {
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
    }

    public void practice() {
        double[] final_outputs  = think(inputs);

        correctPerceptron(final_outputs);
    }

    public double think (double[] inputs) {
        double output = 0;

        currentInputs = inputs;
        if (iPer != null) {
            iPer.updatePerceptron(this, true);
            iPer.setState(this, true);
        }

        for (int i = 0; i < inputs.length; i++) {
            output += inputs[i] * weights[i];
        }

        output += 1 * biasWeight;
        output = sigmoid(output); // sigmoid(x1 * w1 + x2 * w2 + x3 * w3 + 1 * biasWeight)

        currentOutput = output;
        if (iPer != null) {
            iPer.updatePerceptron(this, true);
            iPer.setState(this, false);
        }

        return output;
    }

    public double[] think (double[][] inputs) {
        double[] outputs = new double[inputs.length];

        if (weights != null) {
            for (int f = 0; f < inputs.length; f++) {

                // Send Data to interface
                currentInputs = inputs[f];
                if (iPer != null) {
                    iPer.setState(this, true);
                    iPer.updatePerceptron(this, true);
                }

                for (int c = 0; c < inputs[0].length; c++) {
                    outputs[f] += inputs[f][c] * weights[c];
                }
                outputs[f] += 1 * biasWeight;
                outputs[f] = sigmoid(outputs[f]); // sigmoid(x1 * w1 + x2 * w2 + x3 * w3 + 1 * biasWeight)

                // Send Data to interface
                currentOutput = outputs[f];
                if (iPer != null) {
                    iPer.updatePerceptron(this, true);
                    iPer.setState(this, false);
                }
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

    private void correctPerceptron (double[] lastOutputs) {
        double[] adjustments = new double[lastOutputs.length];

        for (int i = 0; i < lastOutputs.length; i++){
            double error = outputs[i] - lastOutputs[i];

            adjustments[i] = error * sigmoidDerivative(lastOutputs[i]);
        }

        adjustWeights(adjustments);
        adjustBiasWeights(adjustments);
    }

    private void adjustWeights(double[] adjustments) {
        for (int c = 0; c < inputs[0].length; c++) {
            for (int f = 0; f < inputs.length; f++) {
                weights[c] += inputs[f][c] * adjustments[f];
            }

            if (iPer != null)
                iPer.updatePerceptron(this, false);
        }
    }

    private void adjustBiasWeights(double[] adjustments) {
        for (int f = 0; f < adjustments.length; f++) {
            biasWeight += 1 * adjustments[f];
        }
    }

    private void setRandomWeights () {
        // setRandomWeights generates n random numbers for the weights
        Random rand = new Random();
        double[] randomWeights = new double[inputs[0].length];

        for (int i = 0; i < randomWeights.length; i++) {
            randomWeights[i] = (2 * rand.nextDouble()) - 1;
        }

        if (iPer != null)
            iPer.updatePerceptron(this, false);

        setWeights(randomWeights);
    }

    private void setRandomBiasWeights () {
        Random rand = new Random();
        double randomWeight = (2 * rand.nextDouble()) - 1;

        setBiasWeight(randomWeight);
    }
}
