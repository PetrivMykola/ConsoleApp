package main.java.net.petriv.developer.exceptions;

public class IdAlredyExistException extends RuntimeException {
    String msg;

    public IdAlredyExistException (String msg) {
        super(msg);

    }

    public IdAlredyExistException() {
        super();
    }
}
