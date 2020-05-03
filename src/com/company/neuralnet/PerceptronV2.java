package com.company.neuralnet;

import java.util.Random;

public class Perceptron {

    // ATTRIBUTES
    private double[] tInputs;
    private double tOutput;
    private double output;
    private double[] weights;
    private double biasWeight;
    private double lr;

    // DEBUGGING OPTIONAL ATTRIBUTE
    private IPerceptron iPer;


    public Perceptron(int inputs, double lr) {
        this.tInputs = new double[inputs];
        this.tOutput = 0;
        this.output = Double.NaN;
        this.weights = new double[inputs];
        this.lr = lr;
        this.biasWeight = 0;
        this.iPer = null;
    }

    public Perceptron(int inputs, double lr, IPerceptron iPer) {
        this.tInputs = new double[inputs];
        this.tOutput = 0;
        this.output = Double.NaN;
        this.weights = new double[inputs];
        this.lr = lr;
        this.biasWeight = 0;
        this.iPer = iPer;
    }


    // GETTERS
    public double[] getTInputs() {
        return tInputs;
    }

    public double getTOutput() {
        return tOutput;
    }

    public double getOutput() {
        return output;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getBiasWeight() {
        return biasWeight;
    }

    public double getLr() {
        return lr;
    }


    // SETTERS
    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public void setBiasWeight(double biasWeight) {
        this.biasWeight = biasWeight;
    }

    public void setLr(double lr) {
        this.lr = lr;
    }

    public void setIPer(IPerceptron iPer) {
        this.iPer = iPer;
    }


    // METHODS
    public void practice (double[] inputs, double output) {
        tInputs = inputs;
        tOutput = output;

        think(tInputs);

        correctPerceptron();
    }

    public double think (double[] inputs) {
        if (iPer != null) {
            iPer.updatePerceptron(this, true);
            iPer.setState(this, true);
        }

        // ---------- PROCESS INPUTS ---------- //
        double z = 0;
        for (int i = 0; i < inputs.length; i++) {
            z += inputs[i] * weights[i];
        }
        z += biasWeight;

        output = sigmoid(z); // sigmoid(x1 * w1 + x2 * w2 + x3 * w3 + 1 * biasWeight)
        // ----------------------------------- //

        if (iPer != null) {
            iPer.updatePerceptron(this, true);
            iPer.setState(this, false);
        }

        return output;
    }

    private void correctPerceptron () {
        double error = tOutput - output;

        double adjustment = error * sigmoidDerivative(output);

        adjustWeights(adjustment);
        adjustBiasWeights(adjustment);
    }

    private void adjustWeights(double adjustment) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += tInputs[i] * adjustment;

            if (iPer != null)
                iPer.updatePerceptron(this, false);
        }
    }

    private void adjustBiasWeights(double adjustment) {
            biasWeight += adjustment;
    }

    private void setRandomWeights () {
        // setRandomWeights generates n random numbers for the weights
        Random rand = new Random();
        double[] randomWeights = new double[weights.length];

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


    // STATIC METHODS
    public static double sigmoid (double x) {
        // this is the sigmoid function "1 / (1 + exp(-x))", it normalizes the value received between 1 and -1
        return 1 / (1 + Math.exp(-x));
    }

    public static double sigmoidDerivative (double x) {
        return x * (1 - x);
    }
}

