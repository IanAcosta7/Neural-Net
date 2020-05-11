package com.acosta.neural_net.debug_interface.components;

import java.awt.*;

public abstract class Component {
    // ATTRIBUTES
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Graphics g;

    public Component(int x, int y, int width, int height, Graphics g) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.g = g;
    }

    // GETTERS
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // SETTERS
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    protected void drawBorders () {
        g.drawRect(x, y, width, height);
    }

    public abstract void draw ();
}
