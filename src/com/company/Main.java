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

        Perceptron per = new Perceptron(2, 1, debugFrame);

        for (int i = 0; i < 20000; i++) {
            per.practice(inputs[0], outputs[0]);
            per.practice(inputs[1], outputs[1]);
            per.practice(inputs[2], outputs[2]);
            per.practice(inputs[3], outputs[3]);
        }


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
