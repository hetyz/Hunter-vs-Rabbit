package hu.elte.fi.progtech.tron.gui;

import hu.elte.fi.progtech.tron.model.Direction;
import hu.elte.fi.progtech.tron.model.logic.TronLogic;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TronCreatePlayersMenu {
    private String playerName;
    private final TronLogic tronLogic;
    private String choseColor;
    private String[] colorArray = {"GREEN", "YELLOW", "ORANGE", "RED", "BLUE"};
    private final List<String> list = new ArrayList<>(Arrays.asList(colorArray));

    public TronCreatePlayersMenu(TronLogic tronLogic, TronBoard tronBoard) {
        this.tronLogic = tronLogic;

        while (tronLogic.getPlayersSize() != 2) {
            createName();
            createChoseColor();

            if (tronLogic.getPlayersSize() == 1) {
                tronLogic.createPlayer(tronLogic.getRows() / 2, tronLogic.getColumns() / 2, Direction.WEST, playerName, choseColor);
            } else {
                tronLogic.createPlayer(tronLogic.getRows() / 2, tronLogic.getColumns() / 2, Direction.EAST, playerName, choseColor);
            }
        }

        tronBoard.startNewGame();
    }

    private void createChoseColor() {
        Object[] possibilities = colorArray;
        choseColor = (String) JOptionPane.showInputDialog(
                null,
                "Color",
                "Choose your Color",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities, "Yellow");

        if (choseColor == null) {
            choseColor = colorArray[0];
        }

        list.remove(choseColor);
        colorArray = list.toArray(new String[0]);
    }

    private void createName() {
        do {
            playerName = JOptionPane.showInputDialog(null, "Please enter player " + (tronLogic.getPlayersSize() + 1) + " name \n" +
                            "(Lower- or Uppercase MAKES not difference!)",
                    "Enter your name", JOptionPane.PLAIN_MESSAGE);
            if (playerName == null || playerName.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a name!");
            } else if (tronLogic.getPlayersSize() + 1 == 2) {
                if (playerName.equals(tronLogic.returnPlayerOneName())) {
                    JOptionPane.showMessageDialog(null, "Your name is the same like the first player!\n" +
                            "Please give another name!");
                } else if (playerName.toUpperCase().equals(tronLogic.returnPlayerOneName().toUpperCase())) {
                    JOptionPane.showMessageDialog(null, "Your name is the same like the first player\n" +
                            "but with Lower- or Uppercase!\n" +
                            "Please give another name!");
                }
            }
        } while (playerName == null || playerName.equals("")
                || (tronLogic.getPlayersSize() + 1 == 2
                && playerName.toUpperCase().equals(tronLogic.returnPlayerOneName().toUpperCase())));
    }
}
