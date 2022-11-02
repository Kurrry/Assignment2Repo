package sait.frms.problemdomain;

public class Reservation {
	private String reservationCode;
	private String flightCode;
	private String airline;
	private String name;
	private String citizenship;
	private double cost;
	private boolean active;

	/**
	 * Constructor for the Reservation class
	 *
	 * @param reservationCode code for the reservation
	 * @param flightCode code for the flight
	 * @param airline name of the airline
	 * @param name name of the client
	 * @param citizenship citizenship of the client
	 * @param cost cost of the reservation
	 */
	public Reservation(String reservationCode, String flightCode, String airline, String name, String citizenship,
			double cost) {
		this.reservationCode = reservationCode;
		this.flightCode = flightCode;
		this.airline = airline;
		this.name = name;
		this.citizenship = citizenship;
		this.cost = cost;
	}


	/**
	 * @return the name of the client
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the citizenship
	 */
	public String getCitizenship() {
		return citizenship;
	}


	/**
	 * @param citizenship the citizenship to set
	 */
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}


	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * method to change the available seats on a flight.
	 * Only returns an accurate value until the window is closed and reloaded.
	 *
	 * @param active status of the reservation
	 * @param flight the flight manipulated
	 */
	public void setActiveSeatControl(boolean active, Flight flight) {
		this.active = active;
		if (active) {
			flight.decrementSeats();
		} else {
			flight.incrementSeats();
		}
	}


	/**
	 * @return the reservationCode
	 */
	public String getReservationCode() {
		return reservationCode;
	}


	/**
	 * @return the flightCode
	 */
	public String getFlightCode() {
		return flightCode;
	}


	/**
	 * @return the airline
	 */
	public String getAirline() {
		return airline;
	}


	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * method to create a string containing the reservation information
	 *
	 * @return String reservation information
	 */
	@Override
	public String toString(){
		return "Reservation code: " + getReservationCode() + ", Flight: " + getFlightCode() + ", Airline: " + getAirline() +
		", Name: " + getName() + ", Citizenship: " + getCitizenship() + ", Cost: " + getCost() + ", Active: " + isActive();
	}

}
