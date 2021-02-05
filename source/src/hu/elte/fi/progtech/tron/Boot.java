package hu.elte.fi.progtech.tron;

import hu.elte.fi.progtech.tron.gui.TronFrame;
import hu.elte.fi.progtech.tron.model.logic.TronLogic;

import javax.swing.*;

public class Boot {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TronFrame(new TronLogic()).setVisible(true));
    }
}
