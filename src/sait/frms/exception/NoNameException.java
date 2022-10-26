package sait.frms.exception;

public class NoNameException extends Exception {
    public NoNameException() {

    }

    public NoNameException(String s) {
        System.out.println(s);
    }
}
