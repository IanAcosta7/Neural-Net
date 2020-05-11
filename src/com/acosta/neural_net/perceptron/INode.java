package com.acosta.neural_net.perceptron;

public interface INode extends IPerceptron{

    void updateNode (Node n, boolean addDelay);
}
