package sait.frms.manager;

import java.util.ArrayList;
import java.util.Random;

import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

public class ReservationManager {
	
	ArrayList<Reservation> reservations = new ArrayList<>();
	
	public ReservationManager() {

	}

	public Reservation makeReservation(Flight flight, String name, String citizenship) {
		StringBuilder reservationCode = new StringBuilder();
		Random random = new Random();

		if (flight.isDomestic()) {
			reservationCode.append("D");
			for (int i = 0; i < 4; i++) {
				reservationCode.append(random.nextInt(10));
			}
		}
		Reservation reservation = new Reservation(String.valueOf(reservationCode), flight.getFlightCode(), flight.getAirlineName(flight.getFlightCode()),
				name, citizenship, flight.getCostPerSeat());
		reservation.setActive(true);

		return reservation;
	}
	
	/*public Reservation makeReservation(Flight flight, String name, String citizenship)  {
		
	}*/

}


//persist() method saves reservations to the binary file
