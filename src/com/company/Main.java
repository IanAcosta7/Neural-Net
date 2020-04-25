package com.company;

import java.text.DecimalFormat;
import java.util.Scanner;

import com.company.neuralnet.*;
import com.company.neuralnet.views.*;

public class Main {

    private static Scanner scan;

    public static void main(String[] args) {
        scan = new Scanner(System.in);

        int[][][] hiddenLayers = {
                {
                    {2, NeuralNet.AND},
                    {2, NeuralNet.AND}
                } ,
                {
                    {2, NeuralNet.OR}
                }
        };

        double[] inp = {1, 1};


        double[] userInputs = new double[4];

        userInputs[0] = inp[0];
        userInputs[1] = inp[1] == 0 ? 1 : 0;
        userInputs[2] = inp[0] == 0 ? 1 : 0;
        userInputs[3] = inp[1];

        NeuralNet net = new NeuralNet(hiddenLayers);
        net.initialize();
        net.setIterations(20000);
        net.train();

        /*double[][] inputs = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };
        double[] outputs = {0, 1, 1, 0};

        //Debug debugFrame = new Debug(250);

        Perceptron per = new Perceptron(20000);

        per.setInputs(inputs);
        per.setOutputs(outputs);
        per.train();

        double[] userInputs = new double[2];
        System.out.println("Input 1: ");
        userInputs[0] = scan.nextDouble();
        System.out.println("Input 2: ");
        userInputs[1] = scan.nextDouble();

         */

        double finalOutput = net.think(userInputs);

        DecimalFormat formatter = new DecimalFormat("0.00");
        System.out.println("Net output: " + formatter.format(finalOutput));
    }
}
