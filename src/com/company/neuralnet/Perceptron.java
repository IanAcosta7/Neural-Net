package com.company.neuralnet;

import org.ejml.equation.Equation;
import org.ejml.simple.*;

import java.util.EmptyStackException;
import java.util.Random;

public final class Perceptron {

    private SimpleMatrix inputs;
    private SimpleMatrix outputs;
    private SimpleMatrix weights;
    private int iterations;

    public Perceptron (int iterations) {
        this.iterations = iterations;
    }

    // GETTERS
    public SimpleMatrix getInputs () { return inputs; }
    public SimpleMatrix getOutputs () { return outputs; }
    public SimpleMatrix getWeights () { return weights; }
    public int getIterations () { return iterations; }

    // SETTERS
    public void setInputs (double[][] inputs) {
        this.inputs = new SimpleMatrix(inputs);
        setRandomWeights();
    }

    public void setOutputs (double[][] outputs) {
        this.outputs = new SimpleMatrix(outputs);
    }

    public void setWeights (double[][] weights) {
        this.weights = new SimpleMatrix(weights);
    }

    public void setIterations (int iterations) {
        this.iterations = iterations;
    }

    // METHODS
    // The sigmoid function normalizes the value received between 1 and -1
    public SimpleMatrix sigmoid (SimpleMatrix x) {
        Equation eq = new Equation();

        eq.alias(x.getDDRM(), "x");                        // These three lines just process
        eq.process("r = 1 / (1 + exp(-x))");            // the sigmoid function "1 / (1 + exp(-x))"
        return SimpleMatrix.wrap(eq.lookupMatrix("r"));   //  which returns another matrix
    }

    public SimpleMatrix sigmoidDerivative (SimpleMatrix x) {
        return normalMultiplication(x, x.negative().plus(1));
    }

    public void train () {
            SimpleMatrix final_outputs = null;

            if (inputs == null) throw new NullPointerException("inputs equals null");
            if (outputs == null) throw new NullPointerException("outputs equals null");

            for (int i = 0; i < iterations; i++) {
                final_outputs  = think(inputs);
                SimpleMatrix error = outputs.minus(final_outputs);

                SimpleMatrix adjustments = inputs.transpose().mult(normalMultiplication(error, sigmoidDerivative(final_outputs)));
                weights = weights.plus(adjustments);
            }
    }

    public SimpleMatrix think (SimpleMatrix inputs) {
        SimpleMatrix outputs = null;

        if (weights != null) {
            outputs = sigmoid(inputs.mult(weights)); // sigmoid(x1*w1+x2*w2+x3*w3)
        }

        return outputs;
    }

    public SimpleMatrix think (double[][] inputs) {
        SimpleMatrix inputsMatrix = new SimpleMatrix(inputs);
        SimpleMatrix outputs = null;

        if (weights != null) {
            outputs = sigmoid(inputsMatrix.mult(weights)); // sigmoid(x1*w1+x2*w2+x3*w3)
        }

        return outputs;
    }

    // Multiplies 2 matrix but in the normal way
    private SimpleMatrix normalMultiplication (SimpleMatrix A, SimpleMatrix B) {
        SimpleMatrix c = new SimpleMatrix(A.numRows(), A.numCols());
        for (int row = 0; row < A.numRows();row++) {
            for (int col = 0; col < A.numCols();col++) {
                c.set(row, col, A.get(row, col) * B.get(row, col));
            }
        }
        return c;
    }

    // setRandomWeights generates n random numbers for the weights
    private void setRandomWeights () {
        Random rand = new Random(2);
        double[][] randomWeights = new double[inputs.numCols()][1];

        for (int i = 0; i < 3; i++) {
            randomWeights[i][0] = (2 * rand.nextDouble()) - 1;
        }

        setWeights(randomWeights);
    }
}
