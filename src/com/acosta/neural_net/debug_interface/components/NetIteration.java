package com.acosta.neural_net.debug_interface.components;

import com.acosta.neural_net.NeuralNet;

import java.awt.*;

public class NetIteration extends Component {

    private NeuralNet net;
    private int size;
    private boolean drawBorders;

    public NetIteration(int x, int y, int size, Graphics g) {
        super(x, y, 0, 0, g);
        this.size = size * 10;
    }

    public void setNeuralNet (NeuralNet net) {
        this.net = net;
        draw();
    }

    @Override
    public void draw() {
        String text = net.getCurrentIteration() + " / " + net.getIterations();

        g.drawString(text, x + size / 2, y + size / 2);

        height = size;
        width = size * 3;

        if (drawBorders)
            drawBorders();
    }
}
