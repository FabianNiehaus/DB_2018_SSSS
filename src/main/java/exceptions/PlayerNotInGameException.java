package exceptions;

public class PlayerNotInGameException extends Exception {
    public PlayerNotInGameException(int playerID) {
        super("Der Spieler mit der ID " + String.valueOf(playerID) + " nimmt aktuell an keinem Spiel teil!" );
    }
}
