package com.company;

import javax.swing.*;
import java.awt.*;

public class Frame extends JPanel {

    public String str;
    private JFrame frame;

    public Frame () {
        frame = new JFrame("Perceptron");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void draw (JFrame frame) {
        while (true) {
            frame.repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        // Here we can draw things
    }

    public JFrame getFrame () {return frame;}
}
