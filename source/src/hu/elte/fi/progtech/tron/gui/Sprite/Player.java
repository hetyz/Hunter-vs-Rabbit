package hu.elte.fi.progtech.tron.gui.Sprite;

import hu.elte.fi.progtech.tron.gui.LightingPart;
import hu.elte.fi.progtech.tron.model.Direction;
import hu.elte.fi.progtech.tron.resources.ResourceLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class Player extends Sprite {
    private final String name;
    private Direction direction;

    public Direction getStartDirection() {
        return startDirection;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private final Direction startDirection;
    private int posX;
    private int posY;
    private final int startPosX;
    private final int startPosY;
    private boolean dead;

    public ArrayList<LightingPart> getLightingParts() {
        return lightingParts;
    }

    private final ArrayList<LightingPart> lightingParts = new ArrayList<>();

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    private int left;
    private int right;
    private int up;
    private int down;

    private Color playerColor;

    public Direction getDirection() {
        return direction;
    }

    public boolean isDead() {
        return !dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public String getName() {
        return name;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    public int getStartPosX() {
        return startPosX;
    }

    public int getStartPosY() {
        return startPosY;
    }

    public Player(int x, int y, Direction direction, String name, String color) {
        super(x, y, getImageByColor(color));
        this.posX = x;
        this.posY = y;
        this.startDirection = direction;
        this.direction = direction;
        this.name = name;
        this.startPosX = x;
        this.startPosY = y;
        switchColor(color);
        dead = false;
    }

    private void switchColor(String color) {
        switch (color) {
            case "YELLOW" -> playerColor = Color.YELLOW;
            case "GREEN" -> playerColor = Color.GREEN;
            case "ORANGE" -> playerColor = Color.ORANGE;
            case "RED" -> playerColor = Color.RED;
            case "BLUE" -> playerColor = Color.BLUE;
            default -> playerColor = Color.WHITE;
        }
    }

    private static Image getImageByColor(String color) {
        return ResourceLoader.readImage("images/" + color + ".png");
    }


    public void move(Direction direction) {
        lightingParts.add(new LightingPart(posX, posY));
        switchDirection(direction);
    }

    private void switchDirection(Direction direction) {
        switch (direction) {
            case NORTH:
                moveUp();
                break;
            case SOUTH:
                moveDown();
                break;
            case EAST:
                moveRight();
                break;
            case WEST:
                moveLeft();
                break;
            default:
                break;
        }
    }

    private void moveUp() {
        posY -= 5;
    }

    private void moveDown() {
        posY += 5;
    }

    private void moveRight() {
        posX += 5;
    }

    private void moveLeft() {
        posX -= 5;
    }

    private void dirNorth() {
        if (direction != Direction.SOUTH) {
            direction = Direction.NORTH;
        }
    }

    private void dirSouth() {
        if (direction != Direction.NORTH) {
            direction = Direction.SOUTH;
        }
    }

    private void dirEast() {
        if (direction != Direction.WEST) {
            direction = Direction.EAST;
        }
    }

    private void dirWest() {
        if (direction != Direction.EAST) {
            direction = Direction.WEST;
        }
    }

    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == left) {
            dirWest();
        } else if (e.getKeyCode() == right) {
            dirEast();
        } else if (e.getKeyCode() == up) {
            dirNorth();
        } else if (e.getKeyCode() == down) {
            dirSouth();
        }
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }
}
