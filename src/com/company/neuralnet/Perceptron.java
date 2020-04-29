package com.company.neuralnet;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class Perceptron {

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

    /*// NN ATTRIBUTES
    private int inputAmount;
    private int type;*/

    public Perceptron () {
        // DEFAULT VALUES;
        this.iterations = 0;
        this.inputs = null;
        this.outputs = null;
        this.weights = null;
        this.biasWeight = 0;
        this.currentIteration = -1;
        this.currentInputs = null;
        this.currentOutput = -1;

        this.iPer = null;
    }

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

    // CONSTRUCTOR FOR NN
    /*public Perceptron (int inputAmount, int type) {
        this.inputAmount = inputAmount;
        this.type = type;

        // DEFAULT VALUES;
        this.iterations = 0;
        this.inputs = null;
        this.outputs = null;
        this.weights = null;
        this.biasWeight = 0;
        this.currentIteration = -1;
        this.currentInputs = null;
        this.currentOutput = -1;

        this.iPer = null;

        init();
    }*/

    // NEW CONSTRUCTOR
    /*public Perceptron (int inputAmount) {
        this.inputAmount = inputAmount;

        // DEFAULT VALUES;
        this.iterations = 0;
        this.inputs = null;
        this.outputs = null;
        this.weights = null;
        this.biasWeight = 0;
        this.currentIteration = -1;
        this.currentInputs = null;
        this.currentOutput = -1;

        this.iPer = null;

        init();
    }*/

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

    /*
    public int getInputAmount() {
        return inputAmount;
    }

    public int getType() {
        return type;
    }*/

    // SETTERS
    public void setInputs (double[][] inputs) {
        this.inputs = inputs;
        if (weights == null) {
            setRandomWeights();
            setRandomBiasWeights();
        }
    }

    public void setOutputs (double[] outputs) {
        this.outputs = outputs;
    }

    public void setWeights (double[] weights) {
        this.weights = weights;

        //test
        /*this.weights = new double[inputs[0].length];
        for (int i = 0; i < inputs[0].length; i++) {
            this.weights[i] = 1;
        }*/
    }

    public void setBiasWeight(double biasWeight) {
        this.biasWeight = biasWeight;

        //test
        //this.biasWeight = 1;
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

    //TODO: WILL NEED TO RETURN AN ARRAY OF OUTPUTS
    public double think () {
        return think(inputs[0]);
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

    public void adjustWeights(double[] adjustments) {
        for (int c = 0; c < inputs[0].length; c++) {
            for (int f = 0; f < inputs.length; f++) {
                double realAdjustment = inputs[f][c] * adjustments[f];
                weights[c] += realAdjustment;
            }

            if (iPer != null)
                iPer.updatePerceptron(this, false);
        }
    }

    public void adjustBiasWeights(double[] adjustments) {
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


    // NN METHODS
    private void init () {
        //initInputs();
        setRandomWeights();
        setRandomBiasWeights();
        //initOutputs();
    }

    /*
    // TODO: Maybe i could use Bitsets
    private void initInputs () {
        double newInputs[][] = new double[(int)Math.pow(2, inputAmount)][inputAmount];

        for (int x = 0; x < newInputs[0].length; x++) {
            boolean isTrue = false;
            int i = 0;
            for (int y = 0; y < newInputs.length; y++) {
                if (i == ((int)Math.pow(2, inputAmount) / 2) / (x + 1)) {
                    isTrue = !isTrue;
                    i = 0;
                }

                newInputs[y][x] = isTrue ? 1 : 0;
                i++;
            }
        }
        inputs = newInputs;
    }*/

    /*private void initOutputs () {
        double[] newOutputs = new double[inputs.length];

        // OR
        if (type == NeuralNet.OR) {
            for (int y = 0; y < inputs.length; y++) {
                int value = 0;
                for (int x = 0; x < inputs[y].length; x++) {
                    if (inputs[y][x] == 1)
                        value = 1;
                    newOutputs[y] = value;
                }
            }
        }

        // AND
        if (type == NeuralNet.AND) {
            for (int y = 0; y < inputs.length; y++) {
                int value = 1;
                for (int x = 0; x < inputs[y].length; x++) {
                    if (inputs[y][x] == 0)
                        value = 0;
                    newOutputs[y] = value;
                }
            }
        }

        // NOR
        if (type == NeuralNet.NOR) {
            for (int y = 0; y < inputs.length; y++) {
                int value = 1;
                for (int x = 0; x < inputs[y].length; x++) {
                    if (inputs[y][x] == 1)
                        value = 0;
                    newOutputs[y] = value;
                }
            }
        }

        // NAND
        if (type == NeuralNet.NAND) {
            for (int y = 0; y < inputs.length; y++) {
                int value = 0;
                for (int x = 0; x < inputs[y].length; x++) {
                    if (inputs[y][x] == 0)
                        value = 1;
                    newOutputs[y] = value;
                }
            }
        }

        outputs = newOutputs;
    }*/
}
