package com.company;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.company.neuralnet.*;
import com.company.neuralnet.views.*;

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
        net.connect("1", "01");
        net.connect("00", "10");
        net.connect("01", "10");
        net.connect("10", "0");

        net.setIterations(1);
        /*for (int i = 0; i < inputs.length; i++) {
            Random rand = new Random();

            int random = rand.nextInt((3 - 0) + 1) + 0;

            net.train(inputs[random], outputs[random]);
        }*/
        for (int i = 0; i < 20000; i++) {
            net.train(inputs[0], outputs[0]);
            net.train(inputs[1], outputs[1]);
            net.train(inputs[2], outputs[2]);
            net.train(inputs[3], outputs[3]);
            if (i == 19998)
                System.out.println("test");
        }

        double[] newInputs = {0, 0};

        net.setInputs(newInputs);
        double finalOutputs[] = net.think();

        for (int i = 0; i < finalOutputs.length; i++) {
            DecimalFormat formatter = new DecimalFormat("0.00");
            System.out.println("Net output: " + formatter.format(finalOutputs[i]));
        }

        double[] newInputs2 = {0, 1};

        net.setInputs(newInputs2);
        double finalOutputs2[] = net.think();

        for (int i = 0; i < finalOutputs.length; i++) {
            DecimalFormat formatter = new DecimalFormat("0.00");
            System.out.println("Net output: " + formatter.format(finalOutputs2[i]));
        }

        double[] newInputs3 = {1, 0};

        net.setInputs(newInputs3);
        double finalOutputs3[] = net.think();

        for (int i = 0; i < finalOutputs.length; i++) {
            DecimalFormat formatter = new DecimalFormat("0.00");
            System.out.println("Net output: " + formatter.format(finalOutputs3[i]));
        }

        double[] newInputs4 = {1, 1};

        net.setInputs(newInputs4);
        double finalOutputs4[] = net.think();

        for (int i = 0; i < finalOutputs.length; i++) {
            DecimalFormat formatter = new DecimalFormat("0.00");
            System.out.println("Net output: " + formatter.format(finalOutputs4[i]));
        }
    }
}
