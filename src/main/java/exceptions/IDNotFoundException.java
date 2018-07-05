package exceptions;

public class IDNotFoundException extends Exception {

    public IDNotFoundException(String objectType, int objectId) {
        super("There is no object of type " + objectType + " with id " + objectId + " available!");
    }
}
