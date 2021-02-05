package hu.elte.fi.progtech.tron.persistance.dao;

import hu.elte.fi.progtech.tron.persistance.entity.Identifiable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface IEntityDao<E extends Identifiable<Long> & Serializable> {

    List<E> getAllTop10() throws SQLException;

    void mergeHighScores(String winnerName) throws SQLException;
}
