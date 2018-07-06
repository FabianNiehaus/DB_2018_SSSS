package persistence;

import data.Buzzword;

import java.sql.SQLException;
import java.util.List;

public class WordSQLManager implements GenericSQLManager<Buzzword> {
    @Override
    public List<Buzzword> readAll() throws SQLException {
        return null;
    }

    @Override
    public void create(Buzzword buzzword) throws SQLException {

    }

    @Override
    public Buzzword read(int id) throws SQLException {
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
