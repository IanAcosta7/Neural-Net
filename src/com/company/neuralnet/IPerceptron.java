package com.company.neuralnet;

public interface IPerceptron {

    void showInputs (double[] currentI);

    void showOutput (double currentO);

    void showWeights(double[] w);

    void showIterations (int currentI);

    void setIterations (int i);

    void wait (Runnable callback);
}
