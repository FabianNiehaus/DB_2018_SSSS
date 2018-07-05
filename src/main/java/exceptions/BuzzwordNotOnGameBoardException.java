package exceptions;

public class BuzzwordNotOnGameBoardException extends Exception {

    public BuzzwordNotOnGameBoardException() {
        super("Das Spielfeld beinhaltet das Buzzword nicht!");
    }
}
