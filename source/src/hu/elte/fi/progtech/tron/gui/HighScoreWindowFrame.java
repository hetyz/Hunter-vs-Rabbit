package hu.elte.fi.progtech.tron.gui;

import hu.elte.fi.progtech.tron.persistance.dao.HighScoreDao;
import hu.elte.fi.progtech.tron.persistance.entity.HighScore;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class HighScoreWindowFrame {
    private Object[][] data;

    public HighScoreWindowFrame() {
        createFrame();
    }

    private void createFrame() {
        EventQueue.invokeLater(() -> {
            JButton b = new JButton("Close");
            b.setBounds(90, 250, 100, 50);
            JFrame frame = new JFrame("Scoreboard");
            frame.add(b);
            b.addActionListener(e -> {
                frame.setVisible(false);
                frame.dispose();
                frame.removeAll();
            });
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setPreferredSize(new Dimension(300, 350));
            frame.setLocationRelativeTo(null);
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            JPanel panel = new JPanel();

            ArrayList<HighScore> highScores;
            HighScoreDao highScoreDao = new HighScoreDao();
            try {
                highScores = (ArrayList<HighScore>) highScoreDao.getAllTop10();
            } catch (SQLException ex) {
                throw new IllegalArgumentException("Failed to load the Scoreboard menu", ex);
            }

            String[] cols = new String[]{"Pos", "Name", "Wins",};

            assert highScores != null;
            if (highScores.size() < 10) {
                data = new Object[highScores.size()][3];
            } else {
                data = new Object[10][3];
            }

            for (int i = 0; i < 10 && i < highScores.size(); i++) {
                data[i][0] = i + 1;
                data[i][1] = highScores.get(i).getName();
                data[i][2] = highScores.get(i).getScore();
            }

            JTable table = new JTable(data, cols);

            frame.add(new JScrollPane(table));

            frame.pack();
            frame.getContentPane().add(BorderLayout.CENTER, panel);
            frame.setVisible(true);
            frame.setResizable(false);
        });
    }
}
