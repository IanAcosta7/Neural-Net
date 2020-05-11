package com.acosta.neural_net.debug_interface.components;

import com.acosta.neural_net.NeuralNet;
import com.acosta.neural_net.perceptron.Node;

import java.awt.*;

public class NetData extends Component {
    private NeuralNet net;
    private int size;

    public NetData(int x, int y, int size, Graphics g) {
        super(x, y, 0, 0, g);
        this.net = null;
        this.size = size;
    }

    public void setNeuralNet (NeuralNet net) {
        this.net = net;
        /*
        if (net.getNetTInputs() != null) {
            super.setWidth(size * (net.getLAYERS().size() * 20)); //TODO: CHANGE 20
            super.setHeight(size * (net.getNetTInputs().length * 20)); //TODO: FOR SIZE OF PER
        }
        */
        draw();
    }

    @Override
    public void draw() {
        int prevWidth = x;
        int prevHeight = y;

        for (int i = 0; i < net.getLAYERS().size(); i++) {
            int layerWidth = 0;
            int layerHeight = 0;

            for (int j = 0; j < net.getLAYERS().get(i).size(); j++) {
                Node node = net.getLAYERS().get(i).get(j);

                Data data = new Data(i * prevWidth, j * prevHeight, 2, g);
                data.setPerceptron(node);

                prevWidth = data.getWidth();
                prevHeight = data.getHeight();

                // SET SUPER ATTRIBUTES
                layerWidth = data.getWidth();
                layerHeight += data.getHeight();
            }
            width += layerWidth;
            if (layerHeight > height)
                height = layerHeight;
        }

        drawBorders();
    }
}
