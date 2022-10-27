package sait.frms.application;

import sait.frms.exception.NoCitizenshipException;
import sait.frms.exception.NoFlightFoundException;
import sait.frms.exception.NoNameException;
import sait.frms.exception.NoSeatsAvailableException;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

public class TestClass {
    public static void main(String[] args) throws NoFlightFoundException, NoCitizenshipException, NoSeatsAvailableException, NoNameException {
        Flight testFlight = new Flight("OA-9255","ORD","YUL","Monday","18:45",292,396.00);

        System.out.println(testFlight);

        ReservationManager manager = new ReservationManager();
        manager.printReservations();

        /*
        Reservation testReserve = manager.makeReservation(testFlight, "bill", "na", true);
        System.out.println(testReserve);
        String testReserveCode = testReserve.getReservationCode();

        manager.updateReservation(testFlight, testReserveCode, "bob", "sa", false);

        System.out.println(testReserve);
        System.out.println(manager.findReservations(testReserveCode, "Otto Airlines", "bob"));
        System.out.println(manager.findReservationByCode(testReserveCode));
        manager.persist();*/
    }
}
