package com.company.neuralnet;

public interface IPerceptron {

    void updatePerceptron (Perceptron p, boolean addDelay);

    void wait (Runnable callback);
}
