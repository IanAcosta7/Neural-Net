package com.company.neuralnet;

import java.util.ArrayList;

public class NeuralNet {
    // ATTRIBUTES
    private int iterations;
    private int[][][] hiddenLayers;
    private ArrayList<ArrayList<Perceptron>> layers;

    //STATIC ATTRIBUTES
    public static final int OR;
    public static final int AND;
    public static final int NOR;
    public static final int NAND;


    // CONSTRUCTOR
    public NeuralNet (int[][][] hiddenLayers) {
        this.hiddenLayers = hiddenLayers;
        this.layers = new ArrayList<>();
    }

    // SETTERS
    public void setIterations(int iterations) {
        this.iterations = iterations;

        for (ArrayList<Perceptron> layer : layers) {
            for (Perceptron perceptron : layer) {
                perceptron.setIterations(iterations);
            }
        }
    }


    // METHODS
    public void initialize () {
        for (int x = 0; x < hiddenLayers.length; x++) {
            for (int y = 0; y < hiddenLayers[x].length; y++) {
                if (layers.size() < hiddenLayers.length)
                    layers.add(new ArrayList<>());
                layers.get(x).add(new Perceptron(hiddenLayers[x][y][0], hiddenLayers[x][y][1]));
            }
        }
    }

    public void train () {
        for (ArrayList<Perceptron> layer : layers) {
            for (Perceptron perceptron : layer) {
                perceptron.train();
            }
        }
    }

    public double think (double[] inputs) {
        double[] outputs = new double[layers.get(0).size()];
        double finalOutput;

        //FOR SEQUENCE
        for (int y = 0; y < layers.size(); y++) {
            int d = 0;
            for (int x = 0; x < layers.get(y).size(); x++) {
                //Divide inputs
                double[] perceptronInputs = new double[layers.get(y).get(x).getInputAmount()];
                for (int i = 0; i < layers.get(y).get(x).getInputAmount(); i++) {
                    perceptronInputs[i] = inputs[d];
                    d++;
                }

                outputs[x] = layers.get(y).get(x).think(perceptronInputs);
            }
            inputs = outputs;
        }

        finalOutput = outputs[0];
        return finalOutput;


        // 2
        /*
        ArrayList<ArrayList<Double>> newInputs = new ArrayList<>();

        for (int i = 0; i < inputs.length; i++) {
            newInputs.add(i, new ArrayList<>());
            for (int d = 0; d < inputs[i].length; d++) {
                newInputs.get(i).set(d, inputs[i][d]);
            }
        }

        // Every perceptron has an array of inputs
        for (ArrayList<Double> perInputs : newInputs) {
            double perOutput = 0;
            for (Double input : perInputs) {
                perOutput =
            }
        }*/

        // 1
        /*double[] firstOutputs = new double[inputs.length];
        double finalOutput;

        // First layer
        for (int i = 0; i < layers.get(0).size(); i++) {
            firstOutputs[i] = layers.get(0).get(i).think(inputs[i]);
        }

        // Rest of the layers
        for (int i = 1; i < layers.size(); i++) {
            layers.get(i).get(i).think(inputs[i]);
        }

        return 2;*/
    }

    static {
        OR = 0;
        AND = 1;
        NOR = 2;
        NAND = 3;
    }
}
