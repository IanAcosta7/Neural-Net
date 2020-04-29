package com.company.neuralnet;

import java.util.ArrayList;

public class SmartPerceptron extends Perceptron {

    // ATTRIBUTES
    private String name;
    private ArrayList<String> inputNodes;
    private ArrayList<String> outputNodes;
    private double error;
    private double[] adjustment;


    // CONSTRUCTOR
    public SmartPerceptron(String name) {
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

    public double getError() {
        return error;
    }

    public double[] getAdjustment() {
        return adjustment;
    }

    // SETTERS
    public void setInputNode (String name) {
        this.inputNodes.add(name);
    }

    public void setOutputNode (String name) {
        this.outputNodes.add(name);
    }

    public void setError(double error) {
        this.error = error;
    }

    public void setAdjustment(double[] adjustment) {
        this.adjustment = adjustment;
    }
// METHODS


}
