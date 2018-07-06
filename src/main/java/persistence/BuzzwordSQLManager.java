package persistence;

import data.Buzzword;

import java.sql.*;
import java.util.List;

public class BuzzwordSQLManager implements GenericSQLManager<Buzzword> {

    private Connection connection;
    private PreparedStatement preStatement;
    private ResultSet resultSet;
    private String table = "Buzzword";

    public BuzzwordSQLManager() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();
    }

    @Override
    public List<Buzzword> readAll() throws SQLException {
        return null;
    }

    @Override
    public void create(Buzzword buzzword) throws SQLException {

    }

    @Override
    public Buzzword read(int id) {
        return null;
    }

    @Override
    public Boolean update(Buzzword buzzword) throws Exception {
        return null;
    }

    @Override
    public Boolean delete(Buzzword buzzword) throws Exception {
        return null;
    }
}
