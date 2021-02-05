package hu.elte.fi.progtech.tron.model.logic;

import hu.elte.fi.progtech.tron.gui.Sprite.Player;
import hu.elte.fi.progtech.tron.model.Direction;
import hu.elte.fi.progtech.tron.persistance.dao.HighScoreDao;

import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.sql.SQLException;
import java.util.*;

public class TronLogic {
    private int rows = 497;
    private int columns = 478;
    private final List<Player> players = new ArrayList<>();

    private final HighScoreDao highScoreDao;

    private int drawMatch = 2;

    private boolean endGame = false;

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    private String winningPlayer = "null";

    public String getWinningPlayer() {
        return winningPlayer;
    }

    public void setWinningPlayer(String winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    public int getPlayersSize() {
        return players.size();
    }

    public boolean isEndGame() {
        return endGame;
    }

    public TronLogic() {
        this.highScoreDao = new HighScoreDao();
    }

    public void newGame(int rows, int columns) {
        endGame = false;
        this.rows = rows;
        this.columns = columns;
    }

    public String returnPlayerOneName() {
        return players.get(0).getName();
    }

    public void setPlayersToClear() {
        players.clear();

        drawMatch = 2;
        endGame = false;
    }

    public int getDrawMatch() {
        return drawMatch;
    }

    public void saveWinner() {
        try {
            if (drawMatch == 1) {
                highScoreDao.mergeHighScores(winningPlayer.toUpperCase());
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to save high_score to database!", ex);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    private boolean isCollision(Player player) {

        for (Player p : players) {
            Ellipse2D.Double playerBox
                    = new Ellipse2D.Double(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
            for (int i = 0; i < player.getLightingParts().size(); i++) {
                if (!player.equals(p)) {
                    if (playerBox.contains(p.getLightingParts().get(i).getXCoor(), p.getLightingParts().get(i).getYCoor())) {
                        return true;
                    } else if (player.getDirection() == Direction.WEST) {
                        if (player.getPosX() == p.getLightingParts().get(i).getXCoor() && player.getPosY() == p.getLightingParts().get(i).getYCoor()) {
                            return true;
                        }
                    } else if (player.getDirection() == Direction.EAST) {
                        if (player.getPosX() == p.getLightingParts().get(i).getXCoor() && player.getPosY() == p.getLightingParts().get(i).getYCoor()) {
                            return true;
                        }
                    }
                }
            }
        }

        if (player.getPosX() < 0 || player.getPosX() > 496 || player.getPosY() < 0 || player.getPosY() > 477) {
            return true;
        }

        return false;
    }

    public void tick() {
        String notWonYet = "No one";
        for (Player p : players) {
            if (p.isDead()) {
                if (!isCollision(p)) {
                    notWonYet = p.getName();
                    p.move(p.getDirection());
                } else {
                    drawMatch--;
                    p.setVisible(false);
                    p.setDead(true);
                    endGame = true;
                }
            }
        }

        whoWon(notWonYet);
    }

    private void whoWon(String notWonYet) {
        if (endGame) {
            if (drawMatch == 1) {
                winningPlayer = notWonYet;
            }
        }
    }

    public void setDrawMatch(int drawMatch) {
        this.drawMatch = drawMatch;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public void createPlayer(int x, int y, Direction dir, String playerName, String choseColor) {
        Player player = new Player(x, y, dir, playerName, choseColor);

        if (players.size() == 0) {
            player.setLeft(KeyEvent.VK_LEFT);
            player.setRight(KeyEvent.VK_RIGHT);
            player.setUp(KeyEvent.VK_UP);
            player.setDown(KeyEvent.VK_DOWN);
        } else {
            player.setLeft(KeyEvent.VK_A);
            player.setRight(KeyEvent.VK_D);
            player.setUp(KeyEvent.VK_W);
            player.setDown(KeyEvent.VK_S);
        }
        players.add(player);
    }
}
