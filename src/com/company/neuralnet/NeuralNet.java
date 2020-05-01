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
            //double error = lastLayer.get(i).getOutputs()[0] - outputs[i];

            // ------------
            double totalError = -(lastLayer.get(i).getOutputs()[0] - outputs[i]);
            double[] errorPerWeight = new double[lastLayer.get(i).getWeights().length];

            for (int j = 0; j < errorPerWeight.length; j++) {
                errorPerWeight[j] = totalError;
            }

            /*for (int j = 0; j < lastLayer.get(i).getWeights().length; j++) {
                double weightSum = 0;
                for (int k = 0; k < lastLayer.get(i).getWeights().length; k++) {
                    weightSum += lastLayer.get(i).getWeights()[k];
                }
                weightSum += lastLayer.get(i).getBiasWeight();
                errorPerWeight[j] = totalError * (lastLayer.get(i).getWeights()[j] / weightSum);

                if (j == 0)
                    lastLayer.get(i).setBiasError(totalError * (lastLayer.get(i).getBiasWeight() / weightSum));
            }*/
            // ------------

            lastLayer.get(i).setError(errorPerWeight);
            lastLayer.get(i).setBiasError(errorPerWeight[0]);
            //adjustments = error * lastLayer.get(i).sigmoidDerivative(outputs[i]);


            lastLayer.get(i).setAdjustment(errorPerWeight);

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
                double[] errorPerWeight = new double[perceptron.getWeights().length];
                double totalHidError = 0;
                double biasError = 0;
                //double[] adjustments = new double[perceptron.getWeights().length];
                double adjustment;

                // Foreach perceptron in the next layer
                for (int k = 0; k < layers.get(i + 1).size(); k++) {
                    SmartPerceptron nextLayerPerceptron = layers.get(i + 1).get(k);

                    // Calculate weight sum
                    /*double weightSum = 0;
                    for (int l = 0; l < nextLayerPerceptron.getWeights().length; l++) {
                        weightSum += nextLayerPerceptron.getWeights()[l];
                    }
                    double hiddenWeight = nextLayerPerceptron.getWeights()[nextLayerPerceptron.getInputNodes().indexOf(perceptron.getName())];
                    double weightCalculus = hiddenWeight / weightSum;*/

                    //double nextLayerError = 0;

                    /*for (int l = 0; l < layers.get(i + 1).size(); l++) { //same loop as before, this is bad...
                        for (int m = 0; m < layers.get(i + 1).get(l).getInputNodes().size(); m++) {
                            if (layers.get(i + 1).get(l).getInputNodes().get(m).equals(perceptron.getName()))
                                nextLayerError += layers.get(i + 1).get(l).getError()[m];
                        }
                    }*/

                    double nextLayerError = nextLayerPerceptron.getError()[nextLayerPerceptron.getInputNodes().indexOf(perceptron.getName())];

                    for (int l = 0; l < errorPerWeight.length; l++) {
                        errorPerWeight[l] = nextLayerError * nextLayerPerceptron.sigmoidDerivative(nextLayerPerceptron.getCurrentOutput()) * nextLayerPerceptron.getWeights()[nextLayerPerceptron.getInputNodes().indexOf(perceptron.getName())];
                    }
                    if (biasError == 0) {
                        biasError = nextLayerError * nextLayerPerceptron.sigmoidDerivative(nextLayerPerceptron.getCurrentOutput()) * nextLayerPerceptron.getWeights()[nextLayerPerceptron.getInputNodes().indexOf(perceptron.getName())];
                    }


                    //totalHidError +=  weightCalculus * nextLayerError;
                    //totalHidError += nextLayerError;
                }


                // ------------
                //double[] errorPerWeight = new double[perceptron.getWeights().length];

                /*for (int k = 0; k < perceptron.getWeights().length; k++) {
                    double weightSum = 0;
                    for (int l = 0; l < perceptron.getWeights().length; l++) {
                        weightSum += perceptron.getWeights()[l];
                    }
                    weightSum += perceptron.getBiasWeight();
                    errorPerWeight[k] = totalHidError * (perceptron.getWeights()[k] / weightSum);

                    if (k == 0)
                        perceptron.setBiasError(totalHidError * (perceptron.getBiasWeight() / weightSum));
                }*/
                // ------------


                perceptron.setError(errorPerWeight);
                perceptron.setBiasError(biasError);
                perceptron.setAdjustment(errorPerWeight);

                // ADJUST WEIGHTS
                /*for (int k = 0; k < perceptron.getWeights().length; k++) {
                    adjustments[k] = hidError * perceptron.sigmoidDerivative(perceptron.getCurrentOutput());
                    //adjustments[k] = hidError * perceptron.sigmoidDerivative(perceptron.getInputs()[0][k]) * perceptron.getInputs()[0][k];
                }*/

                //adjustment = hidError * perceptron.sigmoidDerivative(perceptron.getCurrentOutput());

                //TODO TEST
                //perceptron.adjustWeights(new double[]{adjustment});
                //perceptron.adjustBiasWeights(new double[]{adjustment});
                //perceptron.setAdjustment(new double[]{adjustment});
            }
        }
        adjustAll();
    }

    private void adjustAll() {
        for (ArrayList<SmartPerceptron> layer : layers) {
            for (SmartPerceptron perceptron : layer) {
                perceptron.adjustWeights2(perceptron.getError());
                perceptron.adjustBiasWeights2(perceptron.getBiasError());
            }
        }
    }

    /*private double calculateAdjustment (double error, double thisOutput, double thisInput) {
        double adjustment;

        // f: finalLayer p: previousLayers
        // -(error) * sigmoidDerivative(fO) * pO
        adjustment = -error * layers.get(0).get(0).sigmoidDerivative(thisOutput) * thisInput;
    }*/

    public static double not (double i) {
        return i == 0 ? 1 : 0;
    }
}
