package hu.elte.fi.progtech.tron.gui.Sprite;

import hu.elte.fi.progtech.tron.resources.ResourceLoader;

public class Boom extends Sprite {
    public Boom(int x, int y) {
        super(x, y, ResourceLoader.readImage("images/boom.png"));
    }
}
