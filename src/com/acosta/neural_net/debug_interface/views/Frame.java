package com.acosta.neural_net.debug_interface.views;

import javax.swing.*;

public abstract class Frame extends JPanel {
    protected JFrame frame;

    public Frame(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
