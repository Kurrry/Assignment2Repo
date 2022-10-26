package sait.frms.exception;

public class NoSeatsAvailableException extends Exception{
    public NoSeatsAvailableException() {

    }

    public NoSeatsAvailableException(String s) {
        System.out.println(s);
    }
}
