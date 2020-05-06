package com.acosta.neural_net;

import com.acosta.neural_net.perceptron.Perceptron;
import com.acosta.neural_net.perceptron.Node;

import java.util.ArrayList;
import java.util.Random;

public class NeuralNet {

    // ATTRIBUTES
    private ArrayList<ArrayList<Node>> layers;
    private int iterations;

    private double[] netTInputs;
    private double[] netTOutputs;
    private double[] outputs;

    private double cost;


    // CONSTRUCTOR
    public NeuralNet (int[] layers) {
        this.layers = new ArrayList<>();
        this.iterations = 0;

        netTInputs = null;
        netTOutputs = null;
        outputs = null;

        cost = 0;

        initLayers(layers);
    }


    // GETTERS
    public int getIterations() {
        return iterations;
    }

    public double getCost() {
        return cost;
    }

    // SETTERS
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setInputs (double[] inputs) {
        this.netTInputs = inputs;

        for (Node perceptron : layers.get(0)) { // FOREACH FIRST LAYER PERCEPTRON
            double[] newInputs = new double[0];
            for (int i = 0; i < inputs.length;i++) {
                for (String inputName : perceptron.getInputNodes()) { // FOREACH INPUT NODE
                    if (inputName.equals(Integer.toString(i))) {
                        double[] auxInputs = new double[newInputs.length + 1];

                        System.arraycopy(newInputs, 0, auxInputs, 0, newInputs.length);

                        auxInputs[newInputs.length] = inputs[i];
                        newInputs = auxInputs;
                    }
                }
            }
            perceptron.setTInputs(newInputs);
        }
    }

    public void setOutputs (double[] newOutputs) {
        this.netTOutputs = newOutputs;

        // FOREACH OUTPUT
        for (int i = 0; i < newOutputs.length; i++) {
            // FOREACH LAST LAYER PERCEPTRON
            for (Node perceptron : layers.get(layers.size() - 1)) {
                // FOREACH OUTPUT NODE
                for (String outputName : perceptron.getOutputNodes()) {
                    if (outputName.equals(Integer.toString(i)))
                        perceptron.setTOutput(newOutputs[i]);
                }
            }
        }
    }


    // METHODS
    private void initLayers (int[] layers) {
        for (int i = 0; i < layers.length; i++) {
            this.layers.add(new ArrayList<>());
            for (int j = 0; j < layers[i]; j++) {
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append(i);
                strBuilder.append(j);
                this.layers.get(i).add(new Node(strBuilder.toString(), 0.5));
            }
        }
    }

    public void train (double[][] newInputs, double[][] newOutputs) {
        Random rand = new Random();

        if (newInputs.length == newOutputs.length) {
            for (int i = 0; i < iterations; i++) {
                //Train a random set of inputs
                int number = rand.nextInt(newInputs.length);

                setInputs(newInputs[number]);
                setOutputs(newOutputs[number]);

                outputs = think();

                // CALCULATE COST
                cost = 0;
                for (int j = 0; j < layers.get(layers.size() - 1).size(); j++) {
                    Node lastLayerPerceptron = layers.get(layers.size() - 1).get(j);

                    double error = lastLayerPerceptron.getTOutput() - outputs[j];
                    cost += Math.pow(error, 2); // sum(aj - tj)^2

                    lastLayerPerceptron.setPartialError(error);
                }

                backpropagate();
            }
        }
    }

    public double[] think () {
        // FOREACH LAYER
        for (ArrayList<Node> layer : layers) {
            outputs = new double[layer.size()];
            // FOREACH PERCEPTRON
            for (int i = 0; i < layer.size(); i++) {
                outputs[i] = layer.get(i).think();

                for (String outputName : layer.get(i).getOutputNodes()) {
                    Node outputNode = getNode(outputName);

                    if (outputNode != null) {
                        double[] auxInputs;

                        if (outputNode.getTInputs() == null)
                            auxInputs = new double[outputNode.getInputNodes().size()];
                        else
                            auxInputs = outputNode.getTInputs();

                        auxInputs[outputNode.getInputNodes().indexOf(layer.get(i).getName())] = outputs[i];

                        outputNode.setTInputs(auxInputs);
                    }
                }
            }
        }

        return outputs;
    }

    public double[] think (double[] newInputs) {
        setInputs(newInputs);
        // FOREACH LAYER
        for (ArrayList<Node> layer : layers) {
            outputs = new double[layer.size()];
            // FOREACH PERCEPTRON
            for (int i = 0; i < layer.size(); i++) {
                outputs[i] = layer.get(i).think();

                for (String outputName : layer.get(i).getOutputNodes()) {
                    Node outputNode = getNode(outputName);

                    if (outputNode != null) {
                        double[] auxInputs;

                        if (outputNode.getTInputs() == null)
                            auxInputs = new double[outputNode.getInputNodes().size()];
                        else
                            auxInputs = outputNode.getTInputs();

                        auxInputs[outputNode.getInputNodes().indexOf(layer.get(i).getName())] = outputs[i];

                        outputNode.setTInputs(auxInputs);
                    }
                }
            }
        }
        return outputs;
    }

    public double[][] think (double[][] newInputs) {
        double[][] finalOutputs = new double[newInputs.length][layers.get(layers.size() -1).size()];

        for (int i = 0; i < newInputs.length; i++) {
            finalOutputs[i] = think(newInputs[i]);
        }

        return finalOutputs;
    }

    public void connect (String giver, String taker) {
        if (getNode(giver) != null)
            getNode(giver).setOutputNode(taker);
        if (getNode(taker) != null)
            getNode(taker).setInputNode(giver);
    }

    private Node getNode (String name) {
        Node result = null;

        for (ArrayList<Node> layer : layers) {
            for (Node perceptron : layer) {
                if (perceptron.getName().equals(name))
                    result = perceptron;
            }
        }

        return result;
    }

    private void backpropagate () {
        // FOREACH LAYER
        for (int i = layers.size() - 2; i >= 0; i--) {
            // FOREACH PERCEPTRON
            for (Node node : layers.get(i)) {
                // Calculates partial error
                double partialError = 0;

                // Foreach Output Node
                for (String outputName : node.getOutputNodes()) {
                    Node outputNode = getNode(outputName);

                    partialError += outputNode.getPartialError()
                            * Perceptron.sigmoidDerivative(outputNode.getOutput())
                            * outputNode.getWeights()[outputNode.getInputNodes().indexOf(node.getName())];
                }

                node.setPartialError(partialError);
            }
        }
        adjustAll();
    }

    private void adjustAll() {
        for (ArrayList<Node> layer : layers) {
            for (Node perceptron : layer) {
                perceptron.adjustWeights(perceptron.getPartialError());
                perceptron.adjustBiasWeights(perceptron.getPartialError());
            }
        }
    }

}
