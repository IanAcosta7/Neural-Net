package com.acosta.neural_net.perceptron;

import com.acosta.neural_net.perceptron.Perceptron;

public interface IPerceptron {

    void updatePerceptron (Perceptron p, boolean addDelay);

    void setState (Perceptron p, boolean state);

    void wait (Runnable callback);
}
