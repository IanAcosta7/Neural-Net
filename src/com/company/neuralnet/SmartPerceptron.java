package com.company.neuralnet;

import java.util.ArrayList;

public class SmartPerceptron extends Perceptron {

    // ATTRIBUTES
    private String name;
    private ArrayList<String> inputNodes;
    private ArrayList<String> outputNodes;
    private double partialError;


    // CONSTRUCTOR
    public SmartPerceptron(String name, double lr) {
        super(lr);
        this.name = name;
        this.inputNodes = new ArrayList<>();
        this.outputNodes = new ArrayList<>();
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public ArrayList<String> getInputNodes () {
        return inputNodes;
    }

    public ArrayList<String> getOutputNodes () {
        return outputNodes;
    }

    public double getPartialError() {
        return partialError;
    }

    // SETTERS
    public void setInputNode (String name) {
        this.inputNodes.add(name);
    }

    public void setOutputNode (String name) {
        this.outputNodes.add(name);
    }

    public void setPartialError(double partialError) {
        this.partialError = partialError;
    }

    // METHODS



    // OVERRIDES
    @Override
    public void adjustWeights(double error) {
        for (int c = 0; c < tInputs.length; c++) {
            double realAdjustment = lr * tInputs[c] * error * sigmoidDerivative(output);
            weights[c] -= realAdjustment;
        }
    }

    @Override
    public void adjustBiasWeights(double error) {
        biasWeight -= lr * error * sigmoidDerivative(output);
    }
}
