package com.company.neuralnet.components;

import java.util.ArrayList;
import java.util.HashMap;

public class Scheme extends Component {
    // ATTRIBUTES
    private ArrayList<HashMap<String, Integer>> inputPoints;
    private HashMap<String, Integer> perceptronOvals;
    private HashMap<String, Integer> outputsPoint;

    private int x;
    private int y;
    private int separation;
    private int inputAmount;

    // CONSTRUCTOR
    public Scheme (int x, int y, int inputAmount, int size) {
        super(x, y, size * 5, inputAmount * size);

        this.x = x + size;
        this.y = y + size;
        this.separation = size;
        this.inputAmount = inputAmount;

        inputPoints = new ArrayList<>();
        perceptronOvals = new HashMap<>();
        outputsPoint = new HashMap<>();

        setInputsPoints();
        setPerceptronOvals();
        setOutputsPoint();
    }


    // GETTERS
    public ArrayList<HashMap<String, Integer>> getInputPoints () {
        return inputPoints;
    }

    public HashMap<String, Integer> getPerceptronOvals () {
        return perceptronOvals;
    }

    public HashMap<String, Integer> getOutputsPoint() {
        return outputsPoint;
    }

    // SETTERS
    private void setInputsPoints () {
        for (int i = 0; i < inputAmount; i++) {
            HashMap hm = new HashMap<String, Integer>();
            hm.put("x1", x);
            hm.put("y1", y + (i * separation));
            hm.put("x2", x + separation * 4);
            hm.put("y2", (y + super.getHeight() / 2) - separation / 2);
            inputPoints.add(hm);
        }
    }

    private void setPerceptronOvals () {
        perceptronOvals.put("x", (x + separation * 4) - (separation * 2) / 2);
        perceptronOvals.put("y", ((y + super.getHeight() / 4) - separation / 4) - (super.getHeight() / 4 - separation / 4));
        perceptronOvals.put("width", separation * 2);
        perceptronOvals.put("height", separation * 2);
    }

    private void setOutputsPoint () {
        outputsPoint.put("x1", x + separation * 4);
        outputsPoint.put("y1", (y + super.getHeight() / 2) - separation / 2);
        outputsPoint.put("x2", x + separation * 8);
        outputsPoint.put("y2", (y + super.getHeight() / 2) - separation / 2);
    }
}
