package hu.elte.fi.progtech.common.gui;

import javax.swing.*;

public class TabletopGameButton extends JButton {
    private final int row;
    private final int column;

    public TabletopGameButton(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

