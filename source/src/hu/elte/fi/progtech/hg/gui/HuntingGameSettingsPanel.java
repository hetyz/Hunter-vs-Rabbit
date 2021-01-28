package hu.elte.fi.progtech.hg.gui;

import javax.swing.*;

public class HuntingGameSettingsPanel extends JPanel {

    private final JSpinner widthSpinner;

    public HuntingGameSettingsPanel() {
        widthSpinner = new JSpinner(
                new SpinnerNumberModel(HuntingGameConstants.STANDARD_GAME_WIDTH, HuntingGameConstants.MIN_GAME_WIDTH,
                        HuntingGameConstants.MAX_GAME_WIDTH, 2)
        );

        add(new JLabel("Size (3, 5, 7): ", SwingConstants.CENTER));
        add(widthSpinner);
    }

    public int getGameWidth() {
        return (Integer) widthSpinner.getValue();
    }
}
