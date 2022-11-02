package sait.frms.problemdomain;

import sait.frms.exception.NoFlightFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Flight {
    private String flightCode;
    private String airlineName;
    private String fromCode;
    private String toCode;
    private String weekday;
    private String time;
    private int seats;
    private double costPerSeat;

    /**
     * constructor for the Flight class
     *
     * @param flightCode code for the flight
     * @param airlineName name of the airline
     * @param fromCode code for the departing airport
     * @param toCode code for the arriving airport
     * @param weekday day of the week the flight is on
     * @param time time that the flight is departing
     * @param seats seats available on the flight
     * @param costPerSeat cost of each seat
     * @throws NoFlightFoundException if the flightCode does not match the required pattern
     */
    public Flight(String flightCode, String airlineName, String fromCode, String toCode, String weekday,
                  String time, int seats, double costPerSeat) throws NoFlightFoundException {

        parseCode(flightCode);

        this.airlineName = airlineName;
        this.flightCode = flightCode;
        this.fromCode = fromCode;
        this.toCode = toCode;
        this.weekday = weekday;
        this.time = time;
        this.seats = seats;
        this.costPerSeat = costPerSeat;
    }

    /**
     *
     * @return flightCode code for the flight
     */
    public String getFlightCode() {
        return flightCode;
    }

    /**
     *
     * @return airlineName name of the airline
     */
    public String getAirlineName() {
        return airlineName;
    }

    /**
     *
     * @return code for the departing airport
     */
    public String getFromCode() {
        return fromCode;
    }

    /**
     *
     * @return code for the arriving airport
     */
    public String getToCode() {
        return toCode;
    }

    /**
     *
     * @return day of the week the flight is on
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     *
     * @return time that the flight is departing
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @return seats available on the flight
     */
    public int getSeats() {
        return seats;
    }

    /**
     * decrement the number of seats available on a flight
     */
    public void decrementSeats() {
        this.seats--;
    }

    /**
     * increment the number of seats available on a flight
     */
    public void incrementSeats() {
        this.seats++;
    }

    /**
     *
     * @return cost of a seat on a flight
     */
    public double getCostPerSeat() {
        return costPerSeat;
    }

    /**
     * Method determines flight is domestic if to and from airport code start with Y
     *
     * @return true if the flight is domestic or false if it's international
     */
    public boolean isDomestic() {
        return fromCode.charAt(0) == 'Y' && toCode.charAt(0) == 'Y';
    }

    /**
     * Method to validate the flight code matches the required code pattern
     *
     * @param code flight code being validated
     * @throws NoFlightFoundException if the code does not match the required pattern
     */
    private void parseCode(String code) throws NoFlightFoundException {
        Pattern regex = Pattern.compile("[ABCOTV]{2}[\\-]+\\d{4}");
        Matcher match = regex.matcher(code);
        boolean matches = match.matches();
        if (!matches) {
            throw new NoFlightFoundException(code);
        }
    }

    /**
     * method to make a string containing the flight information
     *
     * @return String containing the flight information
     */
    @Override
    public String toString() {
        return getFlightCode() + ", From: " + getFromCode() + ", To: " + getToCode() + ", Day: " + getWeekday() +
                ", Cost: " + getCostPerSeat();
    }

}
