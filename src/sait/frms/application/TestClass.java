package sait.frms.application;

import sait.frms.exception.NoFlightFoundException;
import sait.frms.problemdomain.Flight;

public class TestClass {
    public static void main(String[] args) throws NoFlightFoundException {
        Flight testFlight = new Flight("ZA-9255","ORD","YUL","Monday","18:45",292,396.00);

        System.out.println(testFlight);
    }
}
