package com.company;

import com.company.neuralnet.*;
import org.ejml.simple.SimpleMatrix;

public class Main {

    public static void main(String[] args) {
        double[][] inputs = {
                {0, 0, 1},
                {1, 1, 1},
                {1, 0, 1},
                {0, 1, 1}
        };
        double[][] outputs = {
                {0, 1, 1, 0}
        };

        Perceptron net = new Perceptron();

        net.setInputs(inputs);
        net.setOutputs(outputs);

        net.train();
    }
}
