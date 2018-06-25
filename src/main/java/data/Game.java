package data;

import java.util.LinkedList;

public class Game {

    public Game(int id, boolean isActive, BuzzwordCategory buzzwordCategory) {
        this.id = id;
        this.isActive = isActive;
        this.buzzwordCategory = buzzwordCategory;
        this.buzzwords = this.buzzwordCategory.getBuzzwords();
    }

    private int id;

    private boolean isActive;

    private BuzzwordCategory buzzwordCategory;

    private LinkedList<Buzzword> buzzwords;

    private LinkedList<Player> players;

}
