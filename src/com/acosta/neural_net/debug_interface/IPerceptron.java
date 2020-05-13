package com.acosta.neural_net.debug_interface;

import com.acosta.neural_net.perceptron.Node;
import com.acosta.neural_net.perceptron.Perceptron;

public interface IPerceptron extends IDebug {
    void updatePerceptron (Perceptron p, boolean addDelay);

    void setState (Node n, boolean state);
}
