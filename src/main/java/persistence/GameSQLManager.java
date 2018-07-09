package persistence;

import data.Buzzword;
import data.Game;
import data.GameBoard;
import data.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    private GameBoard loadGameboardWithPlayerId(int playerId) throws SQLException {
        //TODO:lade Gameboard von Spieler

        List<Game> games = new ArrayList<>();
        GameBoard gameBoard = null;
        preStatement = connection.prepareStatement("SELECT * FROM spielfeld " +
                "WHERE spielfeld.SpielerID = " + playerId);
        resultSet = preStatement.executeQuery();

        if (resultSet != null ){

                String words = resultSet.getString(1);
                String[] wordArray = words.split(",");
                GameBoard.SingleCell[][] cellMatrix = new GameBoard.SingleCell[5][5];

                for (int i = 0 ; i < wordArray.length ; i++) {

                    cellMatrix[i / 5][i % 5].setBuzzword(new Buzzword(wordArray[i]));

                }

                gameBoard.setCellMatrix(cellMatrix);

        } else {

            return null;

        }

        return gameBoard;

    }

    public void saveGame(Game game) throws SQLException {

        Player currentPlayer = null;
        GameBoard currentBoard = null;
        String words = "";
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<Player, GameBoard> entry :
                game.getGamePlayersAndBoards().entrySet()) {

            currentPlayer = entry.getKey();
            currentBoard = entry.getValue();

            for ( String currentWord : currentBoard.getBuzzwords() ) {

                builder.append(currentWord + ",");

            }

            words = builder.toString();
            builder.setLength(0);

            preStatement = connection.prepareStatement(
                    "INSERT INTO spielfeld" +
                    "(`ID`," +
                    "`Woerter`," +
                    "`SpielerID`)" +
                    "VALUES " +
                    "(" + game.getId() + "," +
                    words + "," +
                    currentPlayer.getId() + ");"
            );
            preStatement.executeQuery();

        }

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
