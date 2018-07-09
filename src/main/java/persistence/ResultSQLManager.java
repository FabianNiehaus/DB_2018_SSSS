package persistence;

import data.Result;

import java.sql.SQLException;
import java.util.List;

public class ResultSQLManager implements GenericSQLManager<Result> {
    @Override
    public List<Result> readAll() throws SQLException {
        return null;
    }

    @Override
    public void create(Result ergebnis) throws SQLException {

    }

    @Override
    public Result read(int id) throws SQLException {
        return null;
    }

    @Override
    public Boolean update(Result ergebnis) throws Exception {
        return null;
    }

    @Override
    public Boolean delete(Result ergebnis) throws Exception {
        return null;
    }
}
