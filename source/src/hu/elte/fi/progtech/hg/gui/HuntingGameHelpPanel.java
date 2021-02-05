package hu.elte.fi.progtech.hg.gui;

import hu.elte.fi.progtech.resources.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HuntingGameHelpPanel extends JPanel {
    private final Font labelFont;

    public HuntingGameHelpPanel() {
        this.labelFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
        setPreferredSize(new Dimension(HuntingGameConstants.HELP_PANEL_WIDTH, HuntingGameConstants.HELP_PANEL_HEIGHT));

        setLayout(new GridLayout(3, 2));

        JLabel leftClickImg = createImageLabel();
        JLabel leftClickText = createTextLabel("Select and move");

        JLabel newGameOption = createTextLabel("New game");
        newGameOption.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel newGameText = createTextLabel("<html>Use the Game menu/New Game option or the <u>CTRL+N</u> shortcut key.</html>");

        add(leftClickImg);
        add(leftClickText);

        add(newGameOption);
        add(newGameText);
    }

    private JLabel createTextLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(Color.BLACK);
        return label;
    }

    private JLabel createImageLabel() {
        try {
            BufferedImage image = ImageLoader.readImage("images/left_click.png");
            return new JLabel(new ImageIcon(image), SwingConstants.CENTER);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load image from path: " + "images/left_click.png" + "!", ex);
        }
    }
}

