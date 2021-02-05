package hu.elte.fi.progtech.tron.persistance.dao;

import hu.elte.fi.progtech.tron.persistance.entity.HighScore;
import hu.elte.fi.progtech.tron.persistance.connection.DataSource;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class HighScoreDao implements IEntityDao<HighScore> {

    private static final String TOP_HIGH_SCORE_QUERY = "SELECT * FROM high_score ORDER BY score DESC LIMIT 10";
    private static final String IF_EXIST_UPDATE_ELSE_INSERT_QUERY = "INSERT INTO high_score (name, score) VALUES (?, 1) ON DUPLICATE KEY UPDATE score = score + 1";

    private static final String ATTR_NAME_ID = "id";
    private static final String ATTR_NAME_NAME = "name";
    private static final String ATTR_NAME_WINS = "score";

    @Override
    public void mergeHighScores(String winnerName) throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(IF_EXIST_UPDATE_ELSE_INSERT_QUERY)) {
            preparedStatement.setString(1, winnerName);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<HighScore> getAllTop10() throws SQLException {
        try (Connection connection = DataSource.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(TOP_HIGH_SCORE_QUERY)) {

            final List<HighScore> entities = new ArrayList<>();

            while (resultSet.next()) {
                final HighScore entity = new HighScore();
                entity.setId(resultSet.getLong(ATTR_NAME_ID));
                entity.setName(resultSet.getString(ATTR_NAME_NAME));
                entity.setScore(resultSet.getInt(ATTR_NAME_WINS));
                entities.add(entity);
            }
            return entities;
        }
    }
}
