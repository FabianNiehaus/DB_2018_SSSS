package persistence;

import data.Game;
import data.GameBoard;
import data.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import java.util.List;

public class GameSQLManager implements GenericSQLManager<Game> {
    private Connection connection;
    private PreparedStatement preStatement;
    private ResultSet resultSet;
    private String table = "Spiel";

    public GameSQLManager() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();
    }


    public List<Game> readAll() throws Exception {
        List<Game> games = new ArrayList<>();
        preStatement = connection.prepareStatement("SELECT * FROM " + table +";");
        resultSet = preStatement.executeQuery();
        while (resultSet.next()) {
            WordSQLManager wordSQLManager = new WordSQLManager();
            Game g = new Game(
                    getAdminOfGame(resultSet.getString(0)),
                    Integer.parseInt(resultSet.getString(0)),
                    wordSQLManager.readBuzzwordCategory(resultSet.getString(2))
            );

            //lade spieler mit boards in Hashmap
            LinkedHashMap<Player, GameBoard> playersAndBoards = new LinkedHashMap<>();
            PlayerSQLManager playerSQLManager = new PlayerSQLManager();
            List<Player> players = playerSQLManager.readAll();

            for (Player p : players) {
                if (isPlayerInGame(p.getId(),g.getId())) {
                    GameBoard gameboard = loadGameboardWithPlayerId(p.getId());
                    playersAndBoards.put(p, gameboard);
                }
            }

        }
        return games;
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

    private GameBoard loadGameboardWithPlayerId(int playerId) {
        //TODO:lade Gameboard von Spieler
        return null;
    }


    private boolean isPlayerInGame(int playerId, int gameId) {
        //TODO:überprüfen ob spieler in
        return true;
    }


    private Player getAdminOfGame(String string) {
        //Todo: finde admin von game
        return null;
    }
}
