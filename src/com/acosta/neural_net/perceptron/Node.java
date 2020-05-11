package com.acosta.neural_net.perceptron;

import java.util.ArrayList;

public class Node extends Perceptron {

    // ATTRIBUTES
    private final String NAME;
    private final ArrayList<String> INPUT_NODES;
    private final ArrayList<String> OUTPUT_NODES;
    private double partialError;


    // CONSTRUCTOR
    public Node(String NAME, double lr) {
        super(lr);
        this.NAME = NAME;
        this.INPUT_NODES = new ArrayList<>();
        this.OUTPUT_NODES = new ArrayList<>();
    }

    public Node(String NAME, double lr, INode iNode) {
        super(lr);
        this.NAME = NAME;
        this.INPUT_NODES = new ArrayList<>();
        this.OUTPUT_NODES = new ArrayList<>();
    }


    // GETTERS
    public String getNAME() {
        return NAME;
    }

    public ArrayList<String> getINPUT_NODES() {
        return INPUT_NODES;
    }

    public ArrayList<String> getOUTPUT_NODES() {
        return OUTPUT_NODES;
    }

    public double getPartialError() {
        return partialError;
    }


    // SETTERS
    public void setInputNode(String name) {
        this.INPUT_NODES.add(name);
    }

    public void setOutputNode(String name) {
        this.OUTPUT_NODES.add(name);
    }

    public void setPartialError(double partialError) {
        this.partialError = partialError;
    }


    // METHODS
    public void fromPerceptron (Perceptron perceptron) {
        this.tInputs = perceptron.getTInputs();
        this.tOutput = perceptron.getTOutput();
        this.output = perceptron.getOutput();
        this.weights = perceptron.getWeights();
        this.biasWeight = perceptron.getBiasWeight();
        this.lr = perceptron.getLr();
    }


    // OVERRIDES
    @Override
    public void adjustWeights(double error) {
        for (int c = 0; c < tInputs.length; c++) {
            double realAdjustment = lr * tInputs[c] * error * sigmoidDerivative(output);
            weights[c] += realAdjustment;
        }
    }

    @Override
    public void adjustBiasWeights(double error) {
        biasWeight += lr * error * sigmoidDerivative(output);
    }
}
