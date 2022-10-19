package sait.frms.exception;

public class NoFlightFoundException extends Throwable {
    public NoFlightFoundException() {

    }

    public void exceptionMessage() {
        System.out.println("Invalid Flight Code");
    }
}
