package sait.frms.application;

import sait.frms.problemdomain.Flight;

public class TestClass {
    public static void main(String[] args) {
        Flight testFlight = new Flight("OA-9255","ORD","YUL","Monday","18:45",292,396.00);

        System.out.println(testFlight);
    }
}
