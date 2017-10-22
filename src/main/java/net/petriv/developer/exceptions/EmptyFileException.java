package main.java.net.petriv.developer.exceptions;

public class EmptyFileException extends RuntimeException {
    String msg;

    public EmptyFileException(String msg) {
        super(msg);
    }

    public EmptyFileException() {
        super();
    }
}
