package com.company.neuralnet;

import java.util.ArrayList;
import java.util.Random;

public class NeuralNet {

    // ATTRIBUTES
    private ArrayList<ArrayList<SmartPerceptron>> layers;
    private int iterations;


    // CONSTRUCTOR
    public NeuralNet (int[] layers) {
        this.layers = new ArrayList<>();
        this.iterations = 0;
        initLayers(layers);
    }

    // GETTERS
    public int getIterations() {
        return iterations;
    }

    // SETTERS
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setInputs (double[] inputs) {
        for (SmartPerceptron perceptron : layers.get(0)) { // FOREACH FIRST LAYER PERCEPTRON
            double[][] newInputs = new double[1][0];
            for (int i = 0; i < inputs.length;i++) {
                for (String inputName : perceptron.getInputNodes()) { // FOREACH INPUT NODE
                    if (inputName.equals(Integer.toString(i))) {
                        double[][] auxInputs = new double[1][newInputs[0].length + 1];
                        for (int j = 0; j < newInputs[0].length; j++) {
                            auxInputs[0][j] = newInputs[0][j];
                        }
                        auxInputs[0][newInputs[0].length] = inputs[i];
                        newInputs = auxInputs;
                    }
                }
            }
            perceptron.setInputs(newInputs);
        }
    }

    public void setOutputs (double[] outputs) {
        int d = 0;
        for (SmartPerceptron perceptron : layers.get(layers.size() - 1)) { // FOREACH LAST LAYER PERCEPTRON
            double[][] newOutputs = new double[1][0];
            for (String outputName : perceptron.getOutputNodes()) { // FOREACH OUTPUT NODE
                if (outputName.equals(Integer.toString(d))) {
                    double[][] auxOutputs = new double[1][newOutputs[0].length + 1];
                    for (int j = 0; j < newOutputs[0].length; j++) {
                        auxOutputs[0][j] = newOutputs[0][j];
                    }
                    auxOutputs[0][newOutputs[0].length] = outputs[d];
                    newOutputs = auxOutputs;
                    d++;
                }
            }
            perceptron.setOutputs(newOutputs[0]);
        }
    }

    // METHODS
    public void train (double[][] inputs, double[][] outputs) {
        Random rand = new Random();

        if (inputs.length == outputs.length) {
            for (int i = 0; i < iterations; i++) {
                //Train a random set of inputs
                int number = rand.nextInt(inputs.length);

                setInputs(inputs[number]);
                setOutputs(outputs[number]);

                practice();
            }
        }
    }

    // TODO METHODS TO REFACTOR:
    private void initLayers (int[] layers) {
        for (int i = 0; i < layers.length; i++) {
            this.layers.add(new ArrayList<>());
            for (int j = 0; j < layers[i]; j++) {
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append(i);
                strBuilder.append(j);
                this.layers.get(i).add(new SmartPerceptron(strBuilder.toString()));
            }
        }
    }

    public void connect (String giver, String taker) {
        if (getPerceptron(giver) != null)
            getPerceptron(giver).setOutputNode(taker);
        if (getPerceptron(taker) != null)
            getPerceptron(taker).setInputNode(giver);
    }

    private SmartPerceptron getPerceptron (String name) {
        SmartPerceptron result = null;

        for (ArrayList<SmartPerceptron> layer : layers) {
            for (SmartPerceptron perceptron : layer) {
                if (perceptron.getName().equals(name))
                    result = perceptron;
            }
        }

        return result;
    }

    private void practice () {
        double[] outputs = think();

        correct(outputs);
    }

    public double[] think () {
        double[] outputs = null;

        // THINKING PROCESS
        for (int i = 0; i < layers.size(); i++) {
            outputs = new double[layers.get(i).size()];
            for (int j = 0; j < layers.get(i).size(); j++) {
                // Get layer outputs
                outputs[j] = layers.get(i).get(j).think();
                // Set next layer inputs
                for (int k = 0; k < layers.get(i).get(j).getOutputNodes().size(); k++) {
                    SmartPerceptron outputNode = getPerceptron(layers.get(i).get(j).getOutputNodes().get(k));

                    if (outputNode != null)
                        outputNode.setInputs(new double[][]{outputs});
                }
            }
        }

        return outputs;
    }

    private void correct (double[] outputs) {
        // CORRECT LAST LAYER
        for (int i = 0; i < layers.get(layers.size() - 1).size(); i++) {
            SmartPerceptron lastLayerPerceptron = layers.get(layers.size() - 1).get(i);

            double totalError = -(lastLayerPerceptron.getOutputs()[0] - outputs[i]);

            lastLayerPerceptron.setPartialError(totalError);
        }

        // BACKPROPAGATE
        for (int i = layers.size() - 2; i >= 0; i--) {
            // CALCULATE ERROR
            for (int j = 0; j < layers.get(i).size(); j++) {
                SmartPerceptron perceptron = layers.get(i).get(j);
                double partialError = 0;

                // Foreach perceptron in the next layer
                for (int k = 0; k < layers.get(i + 1).size(); k++) {
                    SmartPerceptron nextLayerPerceptron = layers.get(i + 1).get(k);

                    double nextLayerError = nextLayerPerceptron.getPartialError();

                    partialError += nextLayerError * Perceptron.sigmoidDerivative(nextLayerPerceptron.getCurrentOutput()) * nextLayerPerceptron.getWeights()[nextLayerPerceptron.getInputNodes().indexOf(perceptron.getName())];
                }

                perceptron.setPartialError(partialError);
            }
        }
        adjustAll();
    }

    private void adjustAll() {
        for (ArrayList<SmartPerceptron> layer : layers) {
            for (SmartPerceptron perceptron : layer) {
                perceptron.adjustWeights(perceptron.getPartialError());
                perceptron.adjustBiasWeights(perceptron.getPartialError());
            }
        }
    }
}
