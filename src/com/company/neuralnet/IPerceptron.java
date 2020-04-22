package com.company.neuralnet;

public interface IPerceptron {

    void updatePerceptron (Perceptron p, boolean addDelay);

    void setState (Perceptron p, boolean state);

    void wait (Runnable callback);
}
