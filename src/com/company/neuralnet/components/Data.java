package com.company.neuralnet.components;

import java.util.ArrayList;
import java.util.HashMap;

public final class Data extends Component {
    // ATTRIBUTES
    private int x;
    private int y;
    private int separation;
    private int inputAmount;

    private ArrayList<HashMap<String, Integer>> inputStrings;
    private ArrayList<HashMap<String, Integer>> weightStrings;
    private HashMap<String, Integer> outputString;

    public Data(int x, int y, int inputAmount, int margin) {
        super(x, y, margin * 7, margin + x + inputAmount * margin);

        this.x = x + margin;
        this.y = y + margin;
        this.separation = margin;
        this.inputAmount = inputAmount;

        inputStrings = new ArrayList<>();
        weightStrings = new ArrayList<>();
        outputString = new HashMap<>();

        setInputStrings();
        setWeightStrings();
        setOutputString();
    }


    //GETTERS
    public ArrayList<HashMap<String, Integer>> getInputStrings() {
        return inputStrings;
    }

    public ArrayList<HashMap<String, Integer>> getWeightStrings() {
        return weightStrings;
    }

    public HashMap<String, Integer> getOutputString() {
        return outputString;
    }


    // SETTERS
    private void setInputStrings () {
        for (int i = 0; i < inputAmount; i++) {
            HashMap<String, Integer> hm = new HashMap<>();
            hm.put("x", x); // X
            hm.put("y", y + i * separation); // Y
            inputStrings.add(hm);
        }
    }

    private void setWeightStrings () {
        for (int i = 0; i < inputAmount; i++) {
            HashMap<String, Integer> hm = new HashMap<String, Integer>();
            hm.put("x", x + separation * 2); // X
            hm.put("y", y + i * separation); // Y
            weightStrings.add(hm);
        }
    }

    private void setOutputString () {
        outputString.put("x", x + separation * 4); // X
        outputString.put("y", y); // Y
    }
}
