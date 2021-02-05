package hu.elte.fi.progtech.tron.gui;

import hu.elte.fi.progtech.tron.model.logic.TronLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class TronFrame extends JFrame {
    private final TronLogic tronLogic;
    private final TronBoard tronBoard;

    public TronFrame(TronLogic tronLogic) {
        this.tronLogic = tronLogic;
        setTitle("Tron");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        tronBoard = new TronBoard(tronLogic);

        getContentPane().add(tronBoard, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);

        setJMenuBar(new TronGameMenuBar());
    }


    private class TronGameMenuBar extends JMenuBar {

        public TronGameMenuBar() {
            JMenu gameMenu = new JMenu("Game");
            JMenuItem newGameItem = new JMenuItem(createNewGameAction());
            newGameItem.setAccelerator(KeyStroke.getKeyStroke('N',
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
            gameMenu.add(newGameItem);
            gameMenu.add(new JSeparator());

            JMenuItem restartMenuItem = new JMenuItem(createRestartAction());
            restartMenuItem.setMnemonic(KeyEvent.VK_R);

            gameMenu.add(restartMenuItem);

            gameMenu.add(new JSeparator());

            JMenuItem exitItem = new JMenuItem(createExitAction());
            exitItem.setMnemonic(KeyEvent.VK_X);

            gameMenu.add(exitItem);

            add(gameMenu);

            JMenu helpMenu = new JMenu("Help");

            JMenuItem helpMenuItem = new JMenuItem(createHelpAction());
            helpMenuItem.setMnemonic(KeyEvent.VK_H);

            helpMenu.add(helpMenuItem);
            add(helpMenu);

            JMenu scoreBoardMenu = new JMenu("Scoreboard");

            JMenuItem scoreBoardMenuItem = new JMenuItem(createScoreBoardAction());
            scoreBoardMenuItem.setMnemonic(KeyEvent.VK_S);

            scoreBoardMenu.add(scoreBoardMenuItem);
            add(scoreBoardMenu);
        }

        private Action createRestartAction() {
            return new AbstractAction("<html><u>R</u>estart</html>") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    if (!tronLogic.getPlayers().isEmpty()) {
                        tronBoard.setRunning(true);
                        tronBoard.startNewGame();
                    } else {
                        JOptionPane.showMessageDialog(null, "Start a game first!", "Restart", JOptionPane.PLAIN_MESSAGE, null);
                    }
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

        private Action createHelpAction() {
            return new AbstractAction("<html><u>H</u>elp</html>") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    JOptionPane.showMessageDialog(null, "You will lead one-one biker, try not to collision to each others, or to the walls!\n" +
                                    "Player one: Up, Down, Left, Right\n" +
                                    "Player two: W, S, A, D\n" +
                                    "Have fun!",
                            "Help", JOptionPane.PLAIN_MESSAGE, null);
                }
            };
        }

        private Action createScoreBoardAction() {
            return new AbstractAction("<html><u>S</u>coreboard</html>") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    new HighScoreWindowFrame();
                }
            };
        }

        private Action createNewGameAction() {
            return new AbstractAction("New Game") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    if (!tronLogic.getPlayers().isEmpty()) {
                        tronLogic.setPlayersToClear();
                    }

                    new TronCreatePlayersMenu(tronLogic, tronBoard);
                }
            };
        }
    }
}
