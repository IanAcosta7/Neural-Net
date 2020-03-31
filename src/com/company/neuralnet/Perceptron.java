package com.company.neuralnet;

import org.ejml.equation.Equation;
import org.ejml.simple.*;
import java.util.Random;
import org.ejml.simple.*;

public final class Perceptron {

    private double[][] inputs;
    private double[][] outputs;
    private double[][] weights = {{0}, {0}, {0}};

    public Perceptron () {
        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            weights[i][0] = (2 * rand.nextDouble()) - 1;
        }
        /*double[][] weights = {
                {(2 * rand.nextDouble()) - 1},
                {(2 * rand.nextDouble()) - 1},
                {(2 * rand.nextDouble()) - 1}
        };*/
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

    public void train () {
        SimpleMatrix training_inputs = new SimpleMatrix(inputs);
        SimpleMatrix training_outputs = new SimpleMatrix(outputs).transpose();
        SimpleMatrix synaptic_weights = new SimpleMatrix(weights);

        System.out.println("Random Synaptic Weights: " + synaptic_weights);


        SimpleMatrix final_outputs = null;

        for (int i = 0; i < 1; i++) {
            SimpleMatrix input_layer = training_inputs;

            final_outputs  = sigmoid(input_layer.mult(synaptic_weights));
        }

        System.out.print("Outputs after training: " + final_outputs);
    }
}
