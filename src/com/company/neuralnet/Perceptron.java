package com.company.neuralnet;

import org.ejml.equation.Equation;
import org.ejml.simple.*;
import java.util.Random;

public final class Perceptron {

    private double[][] inputs;
    private double[][] outputs;
    private double[][] weights = {
            {0},
            {0},
            {0}
    };

    public Perceptron () {
        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            weights[i][0] = (2 * rand.nextDouble()) - 1;
        }
    }

    // GETTERS

    // SETTERS
    public void setInputs(double[][] inputs) {
        this.inputs = inputs;
    }

    public void setOutputs(double[][] outputs) {
        this.outputs = outputs;
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
        SimpleMatrix training_inputs = new SimpleMatrix(inputs);
        SimpleMatrix training_outputs = new SimpleMatrix(outputs).transpose();
        SimpleMatrix synaptic_weights = new SimpleMatrix(weights);

        System.out.println("Random Synaptic Weights: " + synaptic_weights);

        SimpleMatrix final_outputs = new SimpleMatrix();

        for (int i = 0; i < 20000; i++) {
            final_outputs  = sigmoid(training_inputs.mult(synaptic_weights)); // sigmoid(x1*w1+x2*w2+x3*w3)
            SimpleMatrix error = training_outputs.minus(final_outputs);

            SimpleMatrix adjustments = training_inputs.transpose().mult(normalMultiplication(error, sigmoidDerivative(final_outputs)));

            synaptic_weights = synaptic_weights.plus(adjustments);
        }

        System.out.println("Synaptic weights after training: " + synaptic_weights);
        System.out.println("Outputs after training: " + final_outputs);
    }

    private SimpleMatrix normalMultiplication (SimpleMatrix a, SimpleMatrix b) {
        SimpleMatrix c = new SimpleMatrix(a.numRows(), a.numCols());
        for (int i = 0; i < a.numRows();i++) {
            for (int d = 0; d < a.numCols();d++) {
                c.set(i, d, a.get(i, d) * b.get(i, d));
            }
        }
        return c;
    }
}
