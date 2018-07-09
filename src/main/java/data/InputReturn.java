package data;

import java.util.LinkedHashMap;

public class InputReturn {
    private Game game;
    private LinkedHashMap<Player, int[]> playerPositions;
    private Buzzword buzzword;

    public InputReturn(Game game, LinkedHashMap<Player, int[]> playerPositions, Buzzword buzzword) {

        this.game = game;
        this.playerPositions = playerPositions;
        this.buzzword = buzzword;
    }

    public Game getGame() {
        return game;
    }

    public LinkedHashMap<Player, int[]> getPlayerPositions() {
        return playerPositions;
    }

    public Buzzword getBuzzword() {
        return buzzword;
    }
}
