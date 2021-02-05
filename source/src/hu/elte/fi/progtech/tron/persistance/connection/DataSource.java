package hu.elte.fi.progtech.tron.persistance.connection;

import hu.elte.fi.progtech.tron.io.PropertiesReader;

import java.sql.Connection;
import java.sql.SQLException;


public final class DataSource {

    private final PropertiesDataSource dbConnection;

    private DataSource() {
        this.dbConnection = new PropertiesDataSource(PropertiesReader.readProperties("./config/config.properties"));
    }

    public Connection getConnection() throws SQLException {
        return dbConnection.getConnection();
    }

    public static DataSource getInstance() {
        return DataSourceInstanceHolder.INSTANCE;
    }

    private static class DataSourceInstanceHolder {
        private static final DataSource INSTANCE = new DataSource();
    }

}
