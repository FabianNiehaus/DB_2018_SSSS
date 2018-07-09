package data;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Game {

    private GameState gameState;
    private Player admin;
    private int id;
    private LinkedList<Player> winners = new LinkedList<>();
    private BuzzwordCategory buzzwordCategory;
    private LinkedList<Buzzword> buzzwords;
    private LinkedHashMap<Player, GameBoard> playersAndBoards = new LinkedHashMap<>();

    public Game(Player admin, int id, BuzzwordCategory buzzwordCategory) {
        this.admin = admin;
        this.id = id;
        this.buzzwordCategory = buzzwordCategory;
        this.gameState = GameState.OPEN;
        this.buzzwords = buzzwordCategory.getBuzzwords();
    }

    public Player getAdmin() {
        return admin;
    }

    public LinkedList<Player> getWinners() {
        return winners;
    }

    public void setWinners(LinkedList<Player> winner) {
        this.winners = winner;
    }

    public int getId() {
        return id;
    }

    public BuzzwordCategory getBuzzwordCategory() {
        return buzzwordCategory;
    }

    public LinkedList<Buzzword> getBuzzwords() {
        return buzzwords;
    }

    public void addPlayerToGame(Player player) {
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

    public GameBoard getPlayerGameBoard(Player player) {
        return playersAndBoards.get(player);
    }
}
