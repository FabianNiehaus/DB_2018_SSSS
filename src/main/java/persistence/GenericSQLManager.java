package persistence;

import java.sql.SQLException;
import java.util.List;

public interface GenericSQLManager<T> {

    List<T> readAll() throws SQLException ;

    void create(T t) throws SQLException;

    T read(int id) throws SQLException;

    Boolean update(T t) throws Exception;

    Boolean delete(T t) throws Exception;
}
