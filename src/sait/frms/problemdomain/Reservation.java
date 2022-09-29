package sait.frms.problemdomain;

public class Reservation {
	private String reservationCode;
	private String flightCode;
	private String airline;
	private String name;
	private String citizenship;
	private double cost;
	private boolean active;
	
	
	public Reservation(String reservationCode, String flightCode, String airline, String name, String citizenship,
			double cost, boolean active) {
		super();
		this.reservationCode = reservationCode;
		this.flightCode = flightCode;
		this.airline = airline;
		this.name = name;
		this.citizenship = citizenship;
		this.cost = cost;
		this.active = active;
	}


	/**
	 * @return the name
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


	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
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
	
	@Override
	public String toString(){
		return "Reservation code: " + getReservationCode() + ", Flight: " + getFlightCode() + " Airline: " + getAirline() +
		" Name: " + getName() + " Citizenship: " + getCitizenship() + " Cost: " + getCost() + " Active: " + isActive();
	}

}
