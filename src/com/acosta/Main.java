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

        NeuralNet net = new NeuralNet(hiddenLayers);

        // Connections
        net.connect("0", "00");
        net.connect("0", "01");
        net.connect("1", "00");
        net.connect("1", "01");
        net.connect("00", "10");
        net.connect("01", "10");
        net.connect("10", "0");

        net.setIterations(20000);

        net.train(inputs, outputs);

        double[][] newInputs = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

        Debug debug = new Debug(500);
        net.setINet(debug);

        double finalOutputs[][] = net.think(newInputs);

        for (int i = 0; i < finalOutputs.length; i++) {
            DecimalFormat formatter = new DecimalFormat("0.00");
            System.out.println("Net output: " + formatter.format(finalOutputs[i][0]));
        }
    }
}
