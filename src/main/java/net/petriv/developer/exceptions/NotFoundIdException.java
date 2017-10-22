package main.java.net.petriv.developer.exceptions;

    public class NotFoundIdException extends RuntimeException {
        String msg;

        public NotFoundIdException() {
            super();
        }

        public NotFoundIdException(String msg) {
            super(msg);
        }
    }

