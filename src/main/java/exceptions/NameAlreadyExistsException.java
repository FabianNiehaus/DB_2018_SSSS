package exceptions;

public class NameAlreadyExistsException extends Exception {
    public NameAlreadyExistsException() {
        super("Name already in use!");
    }
}
