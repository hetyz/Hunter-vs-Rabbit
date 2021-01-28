package hu.elte.fi.progtech.hg.gui;

import hu.elte.fi.progtech.hg.logic.HuntingGameLogic;
import hu.elte.fi.progtech.resources.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HuntingGameFrame extends JFrame {
    private final HuntingGameLogic huntingGameLogic;
    private final HuntingGamePanel huntingGamePanel;
    private final InfoPanel infoPanel;

    public HuntingGameFrame(final HuntingGameLogic huntingGameLogic) {
        this.huntingGameLogic = huntingGameLogic;
        initFrameProperties();

        this.infoPanel = new InfoPanel();
        getContentPane().add(infoPanel, BorderLayout.NORTH);
        this.huntingGamePanel = new HuntingGamePanel(huntingGameLogic, infoPanel);
        getContentPane().add(huntingGamePanel, BorderLayout.CENTER);
        setJMenuBar(new HuntingGameMenuBar());

        pack();
    }

    private void initFrameProperties() {
        setTitle(HuntingGameConstants.TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(HuntingGameConstants.FRAME_WIDTH, HuntingGameConstants.FRAME_HEIGHT);
        setPreferredSize(new Dimension(HuntingGameConstants.FRAME_WIDTH, HuntingGameConstants.FRAME_HEIGHT));
        setLocationRelativeTo(null);
        setResizable(false);


        try {
            BufferedImage imageLogo = ImageLoader.readImage("images/gameLogo.jpg");
            setIconImage(imageLogo);
            BufferedImage backgroundImage = ImageLoader.readImage("images/gameBackground.jpg");
            ImageIcon imageIcon = new ImageIcon(backgroundImage);
            JLabel background = new JLabel("", imageIcon, JLabel.CENTER);
            background.setBounds(0, 0, HuntingGameConstants.FRAME_WIDTH, HuntingGameConstants.FRAME_HEIGHT);
            add(background);
        } catch (IOException ex) {
            System.err.println("Failed to read the MAIN picture" + ", Exception Message: " + ex.getMessage());
        }
    }


    private class HuntingGameMenuBar extends JMenuBar {

        private final HuntingGameSettingsPanel settingsPanel;

        public HuntingGameMenuBar() {
            this.settingsPanel = new HuntingGameSettingsPanel();
            JMenu gameMenu = new JMenu("Game");
            JMenuItem newGameItem = new JMenuItem(createNewGameAction());
            newGameItem.setAccelerator(KeyStroke.getKeyStroke('N',
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
            gameMenu.add(newGameItem);
            gameMenu.add(new JSeparator());

            JMenuItem exitItem = new JMenuItem(createExitAction());
            exitItem.setMnemonic(KeyEvent.VK_X);

            gameMenu.add(exitItem);

            add(gameMenu);

            JMenu helpMenu = new JMenu("Help");

            JMenuItem helpMenuItem = new JMenuItem(createHelpAction());
            helpMenuItem.setMnemonic(KeyEvent.VK_H);
            try {
                BufferedImage helpImage = ImageLoader.readImage("images/help.png");
                helpMenuItem.setIcon(new ImageIcon(helpImage));
            } catch (IOException ex) {
                System.err.println("Failed to set help icon! " + ex.getMessage());
            }
            helpMenu.add(helpMenuItem);
            add(helpMenu);
        }

        private Action createHelpAction() {
            return new AbstractAction("<html><u>H</u>elp</html>") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    JOptionPane.showMessageDialog(null, new HuntingGameHelpPanel(),
                            "Help", JOptionPane.PLAIN_MESSAGE, null);
                }
            };
        }

        private Action createExitAction() {
            return new AbstractAction("<html>E<u>x</u>it</html>") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    System.exit(0);
                }
            };
        }

        private Action createNewGameAction() {
            return new AbstractAction("New Game") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    int result = JOptionPane.showOptionDialog(null, settingsPanel,
                            "Game settings", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, new String[]{"Start game"}, "Start game");
                    if (result != JOptionPane.CLOSED_OPTION) {
                        huntingGameLogic.newGame(settingsPanel.getGameWidth());
                        huntingGamePanel.newGame();
                        infoPanel.newGame();
                    }
                }
            };
        }
    }

}
