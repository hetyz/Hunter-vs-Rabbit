package hu.elte.fi.progtech.tron.gui.Sprite;

import java.awt.*;

public class Sprite implements ISprite {

    protected final int x;
    protected final int y;
    private final Image image;

    protected final int width;
    protected final int height;

    private boolean visible = true;

    protected Sprite(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
