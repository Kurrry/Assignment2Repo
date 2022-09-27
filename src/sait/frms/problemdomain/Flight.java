package sait.frms.problemdomain;

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

    public Flight(String flightCode, String airlineName, String fromCode, String toCode, String weekday,
                  String time, int seats, double costPerSeat) {
        this.flightCode = flightCode;
        this.airlineName = airlineName;
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

    public String getAirlineName() {
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

    private void parseCode(String code) {
        System.out.println(code);
    }
    @Override
    public String toString() {
        return getFlightCode() + ", From: " + getFromCode() + ", To: " + getToCode() + ", Day: " +
                ", Cost: " + getCostPerSeat();
    }

}
