package com.company.neuralnet.components;

public class Data extends Component {
    // ATTRIBUTES
    private int marginX;
    private int marginY;

    private int[][] inputStrings;
    private int[][] weightStrings;
    private int[] outputString;

    public Data(int x, int y, int inputAmount, int margin, int offset) {
        super(x, y, margin * 5, margin * offset + inputAmount * margin);

        marginX = margin;
        marginY = margin * offset;
    }


    //GETTERS
    public int[][] getInputStrings() {
        return inputStrings;
    }

    public int[][] getWeightStrings() {
        return weightStrings;
    }

    public int[] getOutputString() {
        return outputString;
    }


    // SETTERS
    public void setInputStrings (int amount) {
        inputStrings = new int[amount][2];

        for (int i = 0; i < amount; i++) {
            inputStrings[i][0] = marginX; // X
            inputStrings[i][1] = marginY * (i + 1); // Y
        }
    }

    public void setWeightStrings (int amount) {

        weightStrings = new int[amount][2];

        for (int i = 0; i < amount; i++) {
            weightStrings[i][0] = marginX * 2; // X
            weightStrings[i][1] = marginY * (i + 1); // Y
        }
    }

    public void setOutputString () {
        outputString = new int[2];

        outputString[0] = marginX * 3; // X
        outputString[1] = marginY; // Y
    }
}
