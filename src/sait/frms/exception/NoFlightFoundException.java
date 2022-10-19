package sait.frms.exception;

public class NoFlightFoundException extends Exception {
    public NoFlightFoundException() {
        System.out.println("Invalid Flight Code");
    }
}
