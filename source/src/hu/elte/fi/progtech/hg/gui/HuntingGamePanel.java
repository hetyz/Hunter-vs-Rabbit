package hu.elte.fi.progtech.hg.gui;

import hu.elte.fi.progtech.hg.logic.FieldState;
import hu.elte.fi.progtech.hg.logic.GameStage;

import hu.elte.fi.progtech.hg.logic.HuntingGameLogic;
import hu.elte.fi.progtech.common.gui.TabletopGameButton;
import hu.elte.fi.progtech.hg.logic.PlayerState;
import hu.elte.fi.progtech.resources.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HuntingGamePanel extends JPanel {
    private final HuntingGameLogic huntingGameLogic;
    private final ActionListener gameButtonActionListener = new GameButtonActionListener();
    private final InfoPanel infoPanel;
    private ImageIcon fieldImageIcon;
    private ImageIcon rabbitImageIcon;
    private ImageIcon hunterImageIcon;
    private ImageIcon neighborImageIcon;
    private ImageIcon selectedRabbitImageIcon;
    private ImageIcon selectedHunterImageIcon;
    private int gameSize;

    public HuntingGamePanel(final HuntingGameLogic huntingGameLogic, final InfoPanel infoPanel) {
        this.huntingGameLogic = huntingGameLogic;
        this.infoPanel = infoPanel;
    }

    public void newGame() {
        setupGamePanel();
        imageLoadAndResize();
        drawGameImages();
    }

    private void setupGamePanel() {
        removeAll();
        gameSize = huntingGameLogic.getSize();
        setLayout(new GridLayout(gameSize, gameSize));
        for (int row = 0; row < gameSize; row++) {
            for (int column = 0; column < gameSize; column++) {
                final TabletopGameButton btn = new TabletopGameButton(row, column);
                btn.setPreferredSize(new Dimension(HuntingGameConstants.BTN_WIDTH, HuntingGameConstants.BTN_HEIGHT));
                btn.addActionListener(gameButtonActionListener);
                btn.setFocusable(false);
                add(btn);
            }
        }
        revalidate();
        repaint();
    }

    private void imageLoadAndResize() {
        try {
            BufferedImage master;
            final int size = getGameSize();

            master = ImageLoader.readImage("images/grass.jpg");
            final Image fieldImage = master.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            fieldImageIcon = new ImageIcon(fieldImage);

            master = ImageLoader.readImage("images/rabbit.jpg");
            final Image rabbitImage = master.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            rabbitImageIcon = new ImageIcon(rabbitImage);

            master = ImageLoader.readImage("images/selectedRabbit.jpg");
            final Image selectedRabbitImage = master.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            selectedRabbitImageIcon = new ImageIcon(selectedRabbitImage);

            master = ImageLoader.readImage("images/hunter.jpg");
            final Image hunterImage = master.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            hunterImageIcon = new ImageIcon(hunterImage);

            master = ImageLoader.readImage("images/selectedHunter.jpg");
            final Image selectedHunterImage = master.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            selectedHunterImageIcon = new ImageIcon(selectedHunterImage);

            master = ImageLoader.readImage("images/neighbor.jpg");
            final Image neighborImage = master.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            neighborImageIcon = new ImageIcon(neighborImage);
        } catch (IOException ex) {
            System.err.println("Failed to read the GAME picture" + ", Exception Message: " + ex.getMessage());
        }
    }

    private int getGameSize() {
        int size;
        if (gameSize == 3) {
            size = 125;
        } else if (gameSize == 5) {
            size = 75;
        } else {
            size = 55;
        }
        return size;
    }

    private void drawGameImages() {
        for (Component component : getComponents()) {
            TabletopGameButton btn = (TabletopGameButton) component;
            tryImageDraw(btn);
            btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        }
    }

    private void refreshUI(TabletopGameButton gameButton) {

        infoPanel.setWhoseTurn(huntingGameLogic.getPlayer());
        infoPanel.setSteps(huntingGameLogic.getSteps());

        tryImageDraw(gameButton);

        for (Component component : getComponents()) {
            TabletopGameButton btn = (TabletopGameButton) component;
            int row = btn.getRow();
            int column = btn.getColumn();
            if (row == huntingGameLogic.getPrevX() && column == huntingGameLogic.getPrevY()) {
                trySelectedImageDraw(btn);
            }
            if (row + 1 == huntingGameLogic.getPrevX() && column == huntingGameLogic.getPrevY() ||
                    row - 1 == huntingGameLogic.getPrevX() && column == huntingGameLogic.getPrevY() ||
                    row == huntingGameLogic.getPrevX() && column == huntingGameLogic.getPrevY() + 1 ||
                    row == huntingGameLogic.getPrevX() && column == huntingGameLogic.getPrevY() - 1) {
                tryImageDraw(btn);
            }
        }
    }

    private void tryImageDraw(TabletopGameButton gameButton) {
        int row = gameButton.getRow();
        int column = gameButton.getColumn();
        FieldState fieldState = huntingGameLogic.getFieldValue(row, column);

        setButtonIconByFieldState(gameButton, fieldState);
    }

    private void trySelectedImageDraw(TabletopGameButton gameButton) {
        int row = gameButton.getRow();
        int column = gameButton.getColumn();
        FieldState fieldState = huntingGameLogic.getFieldValue(row, column);
        PlayerState playerState = huntingGameLogic.getPlayerState();
        if (fieldState == FieldState.RABBIT && playerState == PlayerState.RABBIT_TURN) {
            gameButton.setIcon(selectedRabbitImageIcon);
        } else if (fieldState == FieldState.HUNTER && playerState == PlayerState.HUNTER_TURN) {
            gameButton.setIcon(selectedHunterImageIcon);
        }
    }

    private void setButtonIconByFieldState(TabletopGameButton gameButton, FieldState fieldState) {
        if (fieldState == FieldState.FIELD) {
            gameButton.setIcon(fieldImageIcon);
        } else if (fieldState == FieldState.RABBIT) {
            gameButton.setIcon(rabbitImageIcon);
        } else if (fieldState == FieldState.HUNTER) {
            gameButton.setIcon(hunterImageIcon);
        } else if (fieldState == FieldState.NEIGHBOR) {
            gameButton.setIcon(neighborImageIcon);
        } else {
            gameButton.setIcon(fieldImageIcon);
        }
    }

    private void cleanMap() {
        for (Component component : getComponents()) {
            TabletopGameButton btn = (TabletopGameButton) component;
            int row = btn.getRow();
            int column = btn.getColumn();

            FieldState fieldState = huntingGameLogic.getFieldValue(row, column);

            if (fieldState == FieldState.NEIGHBOR) {
                btn.setIcon(fieldImageIcon);
            } else if (fieldState == FieldState.HUNTER) {
                btn.setIcon(hunterImageIcon);
            } else if (fieldState == FieldState.RABBIT) {
                btn.setIcon(rabbitImageIcon);
            }
        }
    }

    private class GameButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            GameStage stage = huntingGameLogic.getStage();
            if (stage != GameStage.PLAYING) {
                return;
            }
            cleanMap();

            TabletopGameButton gameButton = (TabletopGameButton) event.getSource();
            int row = gameButton.getRow();
            int column = gameButton.getColumn();

            if (huntingGameLogic.isSelected()) {
                huntingGameLogic.availableMove(row, column);
            }

            huntingGameLogic.setSelected(true);

            huntingGameLogic.setPrevX(row);
            huntingGameLogic.setPrevY(column);
            huntingGameLogic.availableMove(row, column);

            refreshUI(gameButton);
            checkForEndGame();
        }
    }

    private void checkForEndGame() {
        if (huntingGameLogic.isGameEnd()) {
            GameStage stage = huntingGameLogic.getStage();
            infoPanel.setStatus(stage);
            if (!huntingGameLogic.isAvailableMove()) {
                JOptionPane.showMessageDialog(null, "Hunter won the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Rabbit won the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
            huntingGameLogic.newGame(huntingGameLogic.getSize());
            newGame();
            infoPanel.newGame();
        }
    }
}