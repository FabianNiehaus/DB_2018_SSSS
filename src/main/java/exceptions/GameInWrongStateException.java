package exceptions;

import data.GameState;

public class GameInWrongStateException extends Exception {
    public GameInWrongStateException(GameState currentState, GameState correctState) {
        super("Das Spiel ist im Zustand " + currentState + ", nicht im Zustand " + correctState);
    }
}
