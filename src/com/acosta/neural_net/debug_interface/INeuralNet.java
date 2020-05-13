package com.acosta.neural_net.debug_interface;

import com.acosta.neural_net.NeuralNet;

public interface INeuralNet extends IDebug {
    void updateNeuralNet (NeuralNet nn, boolean addDelay);
}
