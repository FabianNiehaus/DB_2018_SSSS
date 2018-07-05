package data;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Game {

    public Game(int id, boolean isActive, BuzzwordCategory buzzwordCategory) {
        this.id = id;
        this.isActive = isActive;
        this.buzzwordCategory = buzzwordCategory;
        this.buzzwords = this.buzzwordCategory.getBuzzwords();
    }

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

    public boolean isActive() {
        return isActive;
    }

    public BuzzwordCategory getBuzzwordCategory() {
        return buzzwordCategory;
    }

    public LinkedList<Buzzword> getBuzzwords() {
        return buzzwords;
    }

    private boolean isActive;

    private BuzzwordCategory buzzwordCategory;

    private LinkedList<Buzzword> buzzwords;

    private LinkedHashMap<Player, GameBoard> playersAndBoards;

    public void addPlayerToGame(Player player){
        GameBoard newGameBoard = new GameBoard();
        playersAndBoards.put(player, newGameBoard);
    }

}
