package persistence;

import data.Game;

import java.sql.SQLException;
import java.util.List;

public class GameSQLManager implements GenericSQLManager<Game> {
    @Override
    public List<Game> readAll() throws SQLException {
        return null;
    }

    @Override
    public void create(Game game) throws SQLException {

    }

    @Override
    public Game read(int id) throws SQLException {
        return null;
    }

    @Override
    public Boolean update(Game game) throws Exception {
        return null;
    }

    @Override
    public Boolean delete(Game game) throws Exception {
        return null;
    }
}
