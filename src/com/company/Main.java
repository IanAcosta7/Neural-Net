package com.company;

import java.text.DecimalFormat;
import java.util.Scanner;

import com.company.neuralnet.*;
import com.company.neuralnet.views.*;

public class Main {

    private static Scanner scan;

    public static void main(String[] args) {
        scan = new Scanner(System.in);

        double[][] inputs = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };
        double[] outputs = {0, 0, 0, 1};

        Debug debugFrame = new Debug(250);

        Perceptron per = new Perceptron(20000, debugFrame);

        per.setInputs(inputs);
        per.setOutputs(outputs);
        per.train();

        double[] userInputs = new double[2];
        System.out.println("Input 1: ");
        userInputs[0] = scan.nextDouble();
        System.out.println("Input 2: ");
        userInputs[1] = scan.nextDouble();

        double finalOutput = per.think(userInputs);

        DecimalFormat formatter = new DecimalFormat("0.00");
        System.out.println("Net output: " + formatter.format(finalOutput));
    }
}
