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

    public Flight(String flightCode, String fromCode, String toCode, String weekday,
                  String time, int seats, double costPerSeat) {

        try {
            parseCode(flightCode);
            throw new NoFlightFoundException();
        } catch (NoFlightFoundException NF) {
            NF.exceptionMessage();
        }

        this.airlineName = getAirlineName(flightCode.substring(0,2));
        this.flightCode = flightCode;
        this.fromCode = fromCode;
        this.toCode = toCode;
        this.weekday = weekday;
        this.time = time;
        this.seats = seats;
        this.costPerSeat = costPerSeat;
    }

    public String getFlightCode() {
        return flightCode;
    }

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

    public String getFromCode() {
        return fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getTime() {
        return time;
    }

    public int getSeats() {
        return seats;
    }

    public double getCostPerSeat() {
        return costPerSeat;
    }

    public boolean isDomestic() {
        return flightCode.charAt(0) == 'Y';
    }

    private void parseCode(String code) throws NoFlightFoundException {

            Pattern regex = Pattern.compile("[ABCOTV]{2}[\\-]+[0-9]{4}");
            Matcher match = regex.matcher(code);
            boolean matches = match.matches();
            if (!matches) {
                throw new NoFlightFoundException();
            }
    }
    @Override
    public String toString() {
        return getFlightCode() + ", From: " + getFromCode() + ", To: " + getToCode() + ", Day: " +
                ", Cost: " + getCostPerSeat();
    }

}
