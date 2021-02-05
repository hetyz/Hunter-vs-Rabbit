package hu.elte.fi.progtech.tron.gui;

import java.awt.*;

public class LightingPart {

    private final int xCoor;
    private final int yCoor;
    private final int width;
    private final int height;

    public LightingPart(int xCoor, int yCoor) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        int size = 5;
        width = size;
        height = size;
    }

    public int getXCoor() {
        return xCoor;
    }

    public int getYCoor() {
        return yCoor;
    }

    public void draw(Graphics graphics, Color color) {
        graphics.setColor(color);
        graphics.fillRect(xCoor, yCoor, width, height);
    }
}
