package com.acosta.neural_net.debug_interface.components;

import com.acosta.neural_net.NeuralNet;
import com.acosta.neural_net.perceptron.Node;

import java.awt.*;

public class NetScheme extends Component {

    private NeuralNet net;
    private int size;
    private boolean drawBorders;

    public NetScheme(int x, int y, int size, Graphics g) {
        super(x, y, 0, 0, g);
        this.net = null;
        this.size = size * 10;
    }

    public void setNeuralNet (NeuralNet net) {
        this.net = net;
    }

    public void setDrawBorders(boolean drawBorders) {
        this.drawBorders = drawBorders;
    }

    @Override
    public void draw() {
        int xPointer = x;
        int yPointer = y;

        FontMetrics fm = g.getFontMetrics();
        int inputSize = size * 3;

        // DRAW INPUTS
        if (net.getNetTInputs() != null) {
            for (double input : net.getNetTInputs()) {
                String text = Double.toString(input);

                g.drawString(text, xPointer + inputSize / 2 - fm.stringWidth(text) / 2, yPointer + inputSize / 2);

                g.drawRect(xPointer, yPointer, inputSize, inputSize);
                yPointer += inputSize;
            }

            height = net.getNetTInputs().length * inputSize;
            xPointer += inputSize;
            yPointer = y;
        }

        // DRAW NODES
        if (net.getLAYERS() != null) {
            int finalX = 0;
            for (int i = 0; i < net.getLAYERS().size(); i ++) {
                for (int j = 0; j < net.getLAYERS().get(i).size(); j++) {
                    Node node = net.getLAYERS().get(i).get(j);
                    int ovalSize = inputSize / 2;

                    // DRAW LINES
                    int nodeX = xPointer + inputSize + i * inputSize;
                    int nodeY = yPointer + j * inputSize + inputSize / 2;
                    if (node.getINPUT_NODES() != null) {
                        for (int k = 0; k < node.getINPUT_NODES().size(); k++) {
                            Color prevColor = g.getColor();

                            if (node.getInputs() != null && node.getInputs()[k] >= 0.5) {
                                int value = (int)(255 - node.getInputs()[k] * 255);
                                g.setColor(new Color(value, 255, value));
                            }

                            g.drawLine(
                                    xPointer + i * inputSize,
                                    yPointer + k * inputSize + inputSize / 2,
                                    nodeX,
                                    nodeY);

                            g.setColor(prevColor);
                        }
                    }

                    // DRAW NODE
                    Color prevColor = g.getColor();

                    if (!Double.isNaN(node.getOutput()) && node.getOutput() >= 0.5) {
                        int value = (int)(255 - node.getOutput() * 255);
                        g.setColor(new Color(value, 255, value));
                    }

                    g.fillOval(
                            nodeX - ovalSize / 2,
                            nodeY - ovalSize / 2,
                            ovalSize,
                            ovalSize);

                    g.setColor(prevColor);

                    finalX = nodeX;
                }
            }
            xPointer = finalX;
            yPointer = y;
        }

        // DRAW OUTPUTS
        if (net.getNetTOutputs() != null) {
            for (int i = 0; i < net.getNetTOutputs().length; i++) {
                // DRAW LAST LINES
                Color prevColor = g.getColor();

                if (net.getOutputs() != null && net.getOutputs()[i] >= 0.5) {
                    int value = (int) (255 - net.getOutputs()[i] * 255);
                    g.setColor(new Color(value, 255, value));
                }

                g.drawLine(xPointer,
                        yPointer + i * inputSize + inputSize / 2,
                        xPointer + inputSize,
                        yPointer + i * inputSize + inputSize / 2);

                g.setColor(prevColor);

                // DRAW RECTANGLES
                String text = Double.toString(net.getNetTOutputs()[i]);

                g.drawString(text,
                        inputSize + (xPointer+ inputSize / 2 - fm.stringWidth(text) / 2),
                        yPointer + inputSize / 2 + i * inputSize);

                g.drawRect(
                        xPointer + inputSize,
                        yPointer + i * inputSize,
                        inputSize,
                        inputSize);
                yPointer += inputSize;

                width = (xPointer + 2 * inputSize) - x;
            }
        }

        if (drawBorders)
            drawBorders();
    }
}
