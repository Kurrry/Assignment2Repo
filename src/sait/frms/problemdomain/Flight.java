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

    public Flight () {

    }

    /**
     *
     * constructor for Flight.
     * calls the parseCode method to verify flightCode is valid.
     * @param flightCode code for the Flight
     * @param fromCode code for departing city
     * @param toCode code for arriving city
     * @param weekday day of the week for Flight
     * @param time time that Flight is departing
     * @param seats number of seats on the Flight
     * @param costPerSeat cost per seat on the Flight
     * @throws NoFlightFoundException if flightCode is invalid
     */
    public Flight(String flightCode, String fromCode, String toCode, String weekday,
                  String time, int seats, double costPerSeat) throws NoFlightFoundException {

        parseCode(flightCode);

        this.airlineName = getAirlineName(flightCode.substring(0,2));
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
     * @return flightCode for the Flight
     */
    public String getFlightCode() {
        return flightCode;
    }

    /**
     *
     * @param substring first two characters of flightCode to indicate airlineName
     * @return airlineName name of the airline
     */
    public String getAirlineName(String substring) {

        switch(substring) {
            case "OA":
                airlineName = "Otto Airlines";
                break;

            case "CA":
                airlineName = "Conned Air";
                break;

            case "TB":
                airlineName = "Try a Buss Airways";
                break;

            case "VA":
                airlineName = "Vertical Airways";
                break;
        }
        return airlineName;
    }

    /**
     *
     * @return fromCode for departing city
     */
    public String getFromCode() {
        return fromCode;
    }

    /**
     *
     * @return toCode for arriving city
     */
    public String getToCode() {
        return toCode;
    }

    /**
     *
     * @return weekday for the Flight
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     *
     * @return time that flight is departing
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @return seats number of seats on Flight
     */
    public int getSeats() {
        return seats;
    }


    public void setSeats(int seats) {
        this.seats = seats;
    }

    /**
     *
     * @return costPerSeat the cost for a seat on Flight
     */
    public double getCostPerSeat() {
        return costPerSeat;
    }

    /**
     * is the flight domestic or international
     * @return true or false. return true if the first char in fromCode and toCode is Y.
     */
    public boolean isDomestic() {
        return fromCode.charAt(0) == 'Y' && toCode.charAt(0) == 'Y';
    }

    /**
     * Verify whether flightCode meets the pattern required for valid codes
     * @param code flightCode for verification
     * @return true if the code validates
     * @throws NoFlightFoundException if the code is invalid
     */
    private boolean parseCode(String code) throws NoFlightFoundException {

            Pattern regex = Pattern.compile("[ABCOTV]{2}[\\-]+[0-9]{4}");
            Matcher match = regex.matcher(code);
            boolean matches = match.matches();
            if (!matches) {
                throw new NoFlightFoundException(code);
            }

            return true;
    }
    @Override
    public String toString() {
        return getFlightCode() + ", From: " + getFromCode() + ", To: " + getToCode() + ", Day: " + getWeekday() +
                ", Cost: " + getCostPerSeat();
    }

}
