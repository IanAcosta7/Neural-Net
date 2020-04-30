package com.company.neuralnet;

import java.util.ArrayList;

public class SmartPerceptron extends Perceptron {

    // ATTRIBUTES
    private String name;
    private ArrayList<String> inputNodes;
    private ArrayList<String> outputNodes;
    private double[] errors;
    private double[] adjustment;
    private double biasError;


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

    public double[] getError() {
        return errors;
    }

    public double[] getAdjustment() {
        return adjustment;
    }

    public double getBiasError() {
        return biasError;
    }

    // SETTERS
    public void setInputNode (String name) {
        this.inputNodes.add(name);
    }

    public void setOutputNode (String name) {
        this.outputNodes.add(name);
    }

    public void setError(double[] error) {
        this.errors = error;
    }

    public void setAdjustment(double[] adjustment) {
        this.adjustment = adjustment;
    }

    public void setBiasError(double biasError) {
        this.biasError = biasError;
    }
// METHODS


}
