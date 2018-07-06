package persistence;

import data.Ergebnis;

import java.sql.SQLException;
import java.util.List;

public class ResultSQLManager implements GenericSQLManager<Ergebnis> {
    @Override
    public List<Ergebnis> readAll() throws SQLException {
        return null;
    }

    @Override
    public void create(Ergebnis ergebnis) throws SQLException {

    }

    @Override
    public Ergebnis read(int id) throws SQLException {
        return null;
    }

    @Override
    public Boolean update(Ergebnis ergebnis) throws Exception {
        return null;
    }

    @Override
    public Boolean delete(Ergebnis ergebnis) throws Exception {
        return null;
    }
}
