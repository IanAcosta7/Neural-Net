package com.acosta;

import java.text.DecimalFormat;
import java.util.Scanner;

import com.acosta.neural_net.*;
import com.acosta.neural_net.debug_interface.views.Debug;

public class Main {

    private static Scanner scan;

    public static void main(String[] args) {
        scan = new Scanner(System.in);

        int[] hiddenLayers = {2, 1};

        double[][] inputs = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

        double[][] outputs = {
                {0},
                {1},
                {1},
                {0}
        };

        //Debug debug = new Debug(500);
        //NeuralNet net = new NeuralNet(hiddenLayers, debug);
        NeuralNet net = new NeuralNet(inputs[0].length, outputs[0].length, hiddenLayers);

        net.setIterations(20000);

        net.train(inputs, outputs);

        double[][] newInputs = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

        Debug debug = new Debug(5000);
        net.setINet(debug);

        double finalOutputs[][] = net.think(newInputs);

        for (int i = 0; i < finalOutputs.length; i++) {
            DecimalFormat formatter = new DecimalFormat("0.00");
            System.out.println("Net output: " + formatter.format(finalOutputs[i][0]));
        }
    }
}
