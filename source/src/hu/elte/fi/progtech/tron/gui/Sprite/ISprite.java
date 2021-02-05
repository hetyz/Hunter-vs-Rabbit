package hu.elte.fi.progtech.tron.gui.Sprite;

import java.awt.*;

public interface ISprite {
    boolean isVisible();

    void setVisible(boolean visible);

    Image getImage();

    int getWidth();

    int getHeight();
}
