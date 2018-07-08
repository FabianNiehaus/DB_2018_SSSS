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
    private String table = "Spieler";

    public PlayerSQLManager() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();
    }

    public void create(Player player) throws SQLException {
        preStatement = connection.prepareStatement("INSERT INTO " + table + "(Name,Kennwort,Punkte,IstAdmin) VALUES(\'" + player.getLoginname() + "\',\'" + player.getPassword() + "\',\'" + player.getScore() + "\',\'" + player.isAdmin() + "\');");
        preStatement.executeUpdate();

    }

    public List<Player> readAll() throws SQLException {
        List<Player> players = new ArrayList<>();
        preStatement = connection.prepareStatement("SELECT * FROM " + table +";");
        resultSet = preStatement.executeQuery();
        while (resultSet.next()) {
            Player p = new Player(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    resultSet.getString(2),
                    resultSet.getString(3)
                    );

            p.setAdmin(resultSet.getBoolean(4));
            players.add(p);
            System.out.println(p.toString());

        }
        return players;
    }


    public Player read(int id) throws SQLException {
        preStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE id=\"" + id +"\";");
        resultSet = preStatement.executeQuery();

        Player p = new Player(
                Integer.parseInt(resultSet.getString(1)),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4)
        );
        return null;
    }

    public Boolean update(Player player) throws Exception {
        //TODO:bisher nur score update
        preStatement = connection.prepareStatement("UPDATE  " + table + " SET Punkte =\'" + player.getScore() + "\' WHERE id= \'" + player.getId() + "\';" );
        preStatement.executeUpdate();
        return null;

    }

    public Boolean delete(Player player) throws Exception {
        preStatement = connection.prepareStatement("DELETE FROM " + table + " WHERE id=\"" + player.getId() +"\";");
        preStatement.executeUpdate();
        return true;
    }
}
