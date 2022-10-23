package sait.frms.exception;

public class NoFlightFoundException extends Exception {
    public NoFlightFoundException() {

    }

    public NoFlightFoundException(String s) {
        System.out.println(s);
    }
}
