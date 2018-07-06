package persistence;

import data.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerSQLManager implements GenericSQLManager<Player> {

    private Connection connection;
    private PreparedStatement preStatement;
    private ResultSet resultSet;
    private String table = "Player";

    public PlayerSQLManager() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();
    }

    @Override
    public void create(Player player) throws SQLException {

    }

    public List<Player> readAll() throws SQLException {
        List<Player> players = new ArrayList<>();
        preStatement = connection.prepareStatement("SELECT * FROM " + table +";");
        resultSet = preStatement.executeQuery();
        while (resultSet.next()) {
            Player p = new Player(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
                    );

            players.add(p);
            System.out.println(p.toString());

        }
        return players;
    }

    @Override
    public Player read(int id) throws SQLException {
        preStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE name=\"" + id +"\";");
        resultSet = preStatement.executeQuery();

        Player p = new Player(
                Integer.parseInt(resultSet.getString(1)),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4)
        );
        return null;
    }

    @Override
    public Boolean update(Player player) throws Exception {
        return null;
    }

    @Override
    public Boolean delete(Player player) throws Exception {
        return null;
    }
}
