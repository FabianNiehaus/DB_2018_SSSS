package exceptions;

public class IncorrectPasswordException extends Exception{
    public IncorrectPasswordException() {
        super("Password ist nicht korrekt!");
    }
}
