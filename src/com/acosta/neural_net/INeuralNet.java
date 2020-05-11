package com.acosta.neural_net;

import com.acosta.neural_net.perceptron.INode;

public interface INeuralNet extends INode {
    void updateNeuralNet (NeuralNet nn, boolean addDelay);
}
