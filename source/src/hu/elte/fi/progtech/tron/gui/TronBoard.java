package hu.elte.fi.progtech.tron.gui;

import hu.elte.fi.progtech.tron.gui.Sprite.Boom;
import hu.elte.fi.progtech.tron.gui.Sprite.Player;
import hu.elte.fi.progtech.tron.model.logic.Stopwatch;
import hu.elte.fi.progtech.tron.model.logic.TronLogic;
import hu.elte.fi.progtech.tron.resources.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class TronBoard extends JPanel {
    private final TronLogic tronLogic;

    private static final int BOARD_WIDTH = 502;
    private static final int BOARD_HEIGHT = 504;
    private final BufferedImage image = ResourceLoader.readImage("images/Background.png");
    private final Font gameOverFont;
    private Boom boom;

    public void setRunning(boolean running) {
        isRunning = running;
    }

    private boolean isRunning = true;
    private final Stopwatch stopWatch = new Stopwatch();
    private final Font timerFont;
    private Timer timer;
    private Timer stopWatchTimer;


    public TronBoard(TronLogic tronLogic) {
        this.tronLogic = tronLogic;
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setFocusable(true);

        this.timerFont = new Font("Garamond", Font.BOLD, 20);
        this.gameOverFont = new Font("Garamond", Font.BOLD, 30);

        KeyListener tronKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                for (Player player : tronLogic.getPlayers()) {
                    player.keyPressed(e);
                }
                repaint();
            }
        };
        addKeyListener(tronKeyListener);
    }

    public void startNewGame() {
        stop();
        resetStopWatch();

        isRunning = true;

        boom = new Boom(0, 0);

        tronLogic.newGame(BOARD_WIDTH, BOARD_HEIGHT);

        resetPlayers();

        stopWatchTimer = new Timer(1000, stopWatchTimerAction);
        stopWatchTimer.start();

        timer = new Timer(30, doOneStep);
        timer.start();
    }

    private void resetPlayers() {
        for (Player player : tronLogic.getPlayers()) {
            player.getLightingParts().clear();
            player.setDirection(player.getStartDirection());
            player.setPosY(player.getStartPosY());
            player.setPosX(player.getStartPosX());
            player.setPlayerColor(player.getPlayerColor());
            player.setVisible(true);
            player.setDead(false);
        }
        tronLogic.setWinningPlayer("null");
        tronLogic.setDrawMatch(2);
        tronLogic.setEndGame(false);
    }

    private void stop() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    private void resetStopWatch() {
        if (stopWatchTimer != null) {
            stopWatchTimer.stop();
            stopWatch.resetTimer();
        }
    }

    private final Action stopWatchTimerAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            stopWatch.startTimer();
        }
    };

    private final Action doOneStep = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!tronLogic.isEndGame()) {
                if (tronLogic.getPlayersSize() == 2) {
                    tronLogic.tick();
                    repaint();
                }
            } else {
                stopWatchTimer.stop();
                isRunning = false;
                timer.stop();
                for (Player player : tronLogic.getPlayers()) {
                    if (player.isDead()) {
                        tronLogic.saveWinner();
                    }
                }
            }
        }
    };

    private void drawTimer(Graphics2D graphics2D) {
        if (tronLogic.getPlayersSize() == 2) {
            graphics2D.setFont(timerFont);

            String timer = String.format("Timer: %d", stopWatch.getExecutionTime());
            graphics2D.drawString(timer, 10, 20);
        }
    }

    private void drawInGame(Graphics2D graphics2D, Graphics g) {
        g.setColor(Color.MAGENTA);
        drawTimer(graphics2D);

        for (Player p : tronLogic.getPlayers()) {
            for (int i = 0; i < p.getLightingParts().size(); i++) {
                g.setColor(Color.MAGENTA);

                if (p.isVisible()) {
                    p.getLightingParts().get(i).draw(g, p.getPlayerColor());
                    graphics2D.drawImage(p.getImage(), p.getPosX() - 2, p.getPosY() - 2, this);
                } else {
                    p.getLightingParts().get(i).draw(g, Color.GRAY);
                    graphics2D.drawImage(boom.getImage(), p.getPosX() - 5, p.getPosY() - 8, this);
                }

                repaint();
            }
        }
    }

    private void drawGameOver(Graphics2D graphics2D) {
        graphics2D.setFont(gameOverFont);

        FontMetrics fontMetrics = getFontMetrics(gameOverFont);
        for (Player p : tronLogic.getPlayers()) {
            if (p.isDead()) {
                graphics2D.setColor(p.getPlayerColor());
            }
        }

        String gameOverMessage = "Game over!";
        graphics2D.drawString(gameOverMessage,
                (BOARD_WIDTH - fontMetrics.stringWidth(gameOverMessage)) / 2,
                (BOARD_HEIGHT / 2) - fontMetrics.getHeight());

        String wonTheGame;
        if (tronLogic.getDrawMatch() == 1) {
            wonTheGame = tronLogic.getWinningPlayer().toUpperCase() + " won the match!";
        } else {
            wonTheGame = "It's a draw!";
        }
        graphics2D.drawString(wonTheGame,
                (BOARD_WIDTH - fontMetrics.stringWidth(wonTheGame)) / 2,
                (BOARD_HEIGHT / 2) - fontMetrics.getHeight() / 3);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        graphics2D.drawImage(image, 0, 0, this);

        if (isRunning) {
            drawInGame(graphics2D, g);
        } else {
            drawInGame(graphics2D, g);
            drawGameOver(graphics2D);
        }

        Toolkit.getDefaultToolkit().sync();
    }
}
