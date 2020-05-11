package com.acosta.neural_net.perceptron;

public interface IPerceptron {
    void updatePerceptron (Perceptron p, boolean addDelay);

    void setState (Node n, boolean state);

    void wait (Runnable callback);
}
