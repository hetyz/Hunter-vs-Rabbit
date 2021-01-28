/*package hu.elte.fi.progtech.hg.gui;

//import hu.elte.fi.progtech.common.gui.ElapsedTimeCalcAction;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    //private final JLabel elapsedTimeLabel;
    private final JLabel stepCounterLabel;
    private final JLabel whoseTurnLabel;

    //private Timer elapsedTimeSetterTimer;

    public InfoPanel(){
        setPreferredSize(new Dimension(HuntingGameConstants.FRAME_WIDTH,50));

        Font textFont = new Font("Garamond", Font.ITALIC, 16);

        //JLabel elapsedTimeTextLabel = createLabel("Elapsed time: ", textFont);
        JLabel whoseTurnTextLabel = createLabel("Turn: ", textFont);
        whoseTurnLabel = createLabel("", textFont);
        //elapsedTimeLabel = createLabel("", textFont);
        JLabel stepCounterTextLabel = createLabel("Steps: ", textFont);
        stepCounterLabel = createLabel("", textFont);

        //add(elapsedTimeTextLabel);
        //add(elapsedTimeLabel);

        add(whoseTurnTextLabel);
        add(whoseTurnLabel);
        add(new JLabel("    "));
        add(stepCounterTextLabel);
        add(stepCounterLabel);

       // newGame();
    }

    private JLabel createLabel(String text, Font font){
        final JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    public final void newGame(){
        /*if(elapsedTimeSetterTimer != null) {
            elapsedTimeSetterTimer.stop();
        }*/

//elapsedTimeLabel.setText("00:00:00");
        /*setSteps(0);
        setWhoseTurn("Hunter");
        //elapsedTimeSetterTimer = new Timer(1000, new ElapsedTimeCalcAction(elapsedTimeLabel));
        //elapsedTimeSetterTimer.start();
    }

    public void setSteps(int steps){
        stepCounterLabel.setText(String.valueOf(steps));
    }

    public void setWhoseTurn(String player) { whoseTurnLabel.setText(player); }

}

*/
package hu.elte.fi.progtech.hg.gui;

import hu.elte.fi.progtech.hg.logic.GameStage;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private final JLabel stepCounterLabel;
    private final JLabel whoseTurnLabel;
    private final JLabel infoLabel;

    public InfoPanel() {
        setPreferredSize(new Dimension(HuntingGameConstants.FRAME_WIDTH, HuntingGameConstants.INFO_PANEL_HEIGHT));

        stepCounterLabel = new JLabel("Game -> New Game or Ctrl + N", SwingConstants.CENTER);
        Font textFont = new Font(Font.MONOSPACED, Font.BOLD, 16);
        stepCounterLabel.setFont(textFont);
        whoseTurnLabel = new JLabel("", SwingConstants.CENTER);
        whoseTurnLabel.setFont(textFont);
        infoLabel = new JLabel("", SwingConstants.CENTER);
        infoLabel.setFont(textFont);

        add(whoseTurnLabel);
        add(stepCounterLabel);
        add(infoLabel);
    }

    public final void newGame() {
        setSteps(0);
        setWhoseTurn("Hunter");
        infoLabel.setText("");
    }

    public void setSteps(int steps) {
        String stepsValue = String.valueOf(steps);
        stepCounterLabel.setText("Steps: " + stepsValue);
    }

    public void setWhoseTurn(String player) {
        whoseTurnLabel.setText("Turn: " + player + " ");
    }


    public void setStatus(GameStage stage) {
        if (stage != GameStage.PLAYING) {
            setGameStage(stage);
        } else {
            infoLabel.setText(" ");
        }
    }

    private void setGameStage(GameStage stage) {
        String won = (stage == GameStage.HUNTER_WON) ? "Hunter Won" : "Rabbit Won";
        Color color = (stage == GameStage.HUNTER_WON) ? Color.RED : Color.GREEN;
        infoLabel.setText(won);
        infoLabel.setForeground(color);
        stepCounterLabel.setText("");
        whoseTurnLabel.setText("");
    }
}
