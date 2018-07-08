package data;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Game {

    public Game(Player admin, int id, BuzzwordCategory buzzwordCategory) {
        this.admin = admin;
        this.id = id;
        this.buzzwordCategory = buzzwordCategory;
        this.gameState = GameState.OPEN;
        this.buzzwords = buzzwordCategory.getBuzzwords();
    }

    private GameState gameState;

    public Player getAdmin() {
        return admin;
    }

    private Player admin;

    private int id;

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    private Player winner = null;

    public int getId() {
        return id;
    }

    public BuzzwordCategory getBuzzwordCategory() {
        return buzzwordCategory;
    }

    public LinkedList<Buzzword> getBuzzwords() {
        return buzzwords;
    }

    private BuzzwordCategory buzzwordCategory;

    private LinkedList<Buzzword> buzzwords;

    private LinkedHashMap<Player, GameBoard> playersAndBoards = new LinkedHashMap<>();

    public void addPlayerToGame(Player player){
        GameBoard newGameBoard = new GameBoard();
        newGameBoard.setGameBoard(buzzwords);
        playersAndBoards.put(player, newGameBoard);
    }

    public LinkedHashMap<Player, GameBoard> getGamePlayersAndBoards() {

        return playersAndBoards;

    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameBoard  getPlayerGameBoard(Player player){
        return playersAndBoards.get(player);
    }
}
