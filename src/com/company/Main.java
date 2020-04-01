package com.company;

import java.util.Scanner;
import com.company.neuralnet.*;
import org.ejml.simple.SimpleMatrix;

public class Main {

    private static Scanner scan;

    public static void main(String[] args) {
        scan = new Scanner(System.in);

        double[][] inputs = {
                {0, 0, 1},
                {1, 1, 1},
                {1, 0, 1},
                {0, 1, 1}
        };
        double[][] outputs = {
                {0},
                {1},
                {1},
                {0}
        };

        Perceptron net = new Perceptron(20000);

        net.setInputs(inputs);
        net.setOutputs(outputs);
        net.train();

        double[][] userInputs = new double[1][3];
        System.out.println("Input 1: ");
        userInputs[0][0] = scan.nextDouble();
        System.out.println("Input 2: ");
        userInputs[0][1] = scan.nextDouble();
        System.out.println("Input 3: ");
        userInputs[0][2] = scan.nextDouble();

        SimpleMatrix output = net.think(userInputs);
        System.out.println("Net outputs: " + output);
    }
}
