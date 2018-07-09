package exceptions;

import data.GameState;

public class GameInWrongStateException extends Exception {
    public GameInWrongStateException(GameState currentState) {
        super("Das Spiel ist im Zustand " + currentState + "!");
    }
}
