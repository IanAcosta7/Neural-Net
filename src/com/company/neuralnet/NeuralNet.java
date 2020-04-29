package com.company.neuralnet;

import java.util.ArrayList;

public class NeuralNet {

    // ATTRIBUTES
    private ArrayList<ArrayList<SmartPerceptron>> layers;
    private int iterations;


    // CONSTRUCTOR
    public NeuralNet (int[] layers) {
        this.layers = new ArrayList<>();
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
        int d = 0;
        for (SmartPerceptron perceptron : layers.get(0)) { // FOREACH FIRST LAYER PERCEPTRON
            double[][] newInputs = new double[1][0];
            for (String inputName : perceptron.getInputNodes()) { // FOREACH INPUT NODE
                if (inputName.equals(Integer.toString(d))) {
                    double[][] auxInputs = new double[1][newInputs[0].length + 1];
                    for (int j = 0; j < newInputs[0].length; j++) {
                        auxInputs[0][j] = newInputs[0][j];
                    }
                    auxInputs[0][newInputs[0].length] = inputs[d];
                    newInputs = auxInputs;
                    d++;
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

    public void train (double[] inputs, double outputs[]) {

        // TODO: THIS NEEDS TO BE CHANGED
        // Set first inputs
        setInputs(inputs);
        setOutputs(outputs);


        for (int i = 0; i < iterations; i++) {
            practice();
        }
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
        ArrayList<SmartPerceptron> lastLayer = layers.get(layers.size() - 1);
        //int d = 0;
        for (int i = 0; i < lastLayer.size(); i++) {
            double adjustments;

            // Last layer only haves one output
            double error = lastLayer.get(i).getOutputs()[0] - outputs[i];
            lastLayer.get(i).setError(error);

            adjustments = error * lastLayer.get(i).sigmoidDerivative(outputs[i]);

            //TODO TEST
            //lastLayer.get(i).adjustWeights(new double[]{adjustments});
            //lastLayer.get(i).adjustBiasWeights(new double[]{adjustments});

            lastLayer.get(i).setAdjustment(new double[]{adjustments});

            /*for (int j = 0; j < lastLayer.get(i).getOutputNodes().size(); j++) {
                adjustments = new double[lastLayer.get(i).getOutputNodes().size()];
                if (lastLayer.get(i).getOutputNodes().get(j).equals(Integer.toString(d))) {
                    double error = lastLayer.get(i).getOutputs()[0] - outputs[d];
                    lastLayer.get(i).setError(error);
                    adjustments[j] = error * lastLayer.get(i).sigmoidDerivative(outputs[d]);
                }
                d++;
            }
            lastLayer.get(i).adjustWeights(adjustments);
            lastLayer.get(i).adjustBiasWeights(adjustments);*/
        }

        // BACKPROPAGATE
        for (int i = layers.size() - 2; i >= 0; i--) {
            // CALCULATE ERROR
            for (int j = 0; j < layers.get(i).size(); j++) {
                SmartPerceptron perceptron = layers.get(i).get(j);
                double hidError = 0;
                //double[] adjustments = new double[perceptron.getWeights().length];
                double adjustment;

                // Foreach perceptron in the next layer
                for (int k = 0; k < layers.get(i + 1).size(); k++) {
                    SmartPerceptron nextLayerPerceptron = layers.get(i + 1).get(k);

                    // Calculate weight sum
                    double weightSum = 0;
                    for (int l = 0; l < nextLayerPerceptron.getWeights().length; l++) {
                        weightSum += nextLayerPerceptron.getWeights()[l];
                    }
                    double hiddenWeight = nextLayerPerceptron.getWeights()[nextLayerPerceptron.getInputNodes().indexOf(perceptron.getName())];
                    double weightCalculus = hiddenWeight / weightSum;

                    hidError +=  weightCalculus * nextLayerPerceptron.getError();
                }

                perceptron.setError(hidError);

                // ADJUST WEIGHTS
                /*for (int k = 0; k < perceptron.getWeights().length; k++) {
                    adjustments[k] = hidError * perceptron.sigmoidDerivative(perceptron.getCurrentOutput());
                    //adjustments[k] = hidError * perceptron.sigmoidDerivative(perceptron.getInputs()[0][k]) * perceptron.getInputs()[0][k];
                }*/

                adjustment = hidError * perceptron.sigmoidDerivative(perceptron.getCurrentOutput());

                //TODO TEST
                //perceptron.adjustWeights(new double[]{adjustment});
                //perceptron.adjustBiasWeights(new double[]{adjustment});
                perceptron.setAdjustment(new double[]{adjustment});
            }
        }
        adjustAll();
    }

    private void adjustAll() {
        for (ArrayList<SmartPerceptron> layer : layers) {
            for (SmartPerceptron perceptron : layer) {
                perceptron.adjustWeights(perceptron.getAdjustment());
                perceptron.adjustBiasWeights(perceptron.getAdjustment());
            }
        }
    }

    public static double not (double i) {
        return i == 0 ? 1 : 0;
    }
}
