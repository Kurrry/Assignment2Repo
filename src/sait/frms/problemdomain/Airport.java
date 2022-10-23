package sait.frms.problemdomain;

public class Airport {
    private String airportCode;
    private String airportName;

    public Airport(String airportCode, String airportName) {
        this.airportCode = airportCode;
        this.airportName = airportName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public String getAirportName() {
        return airportName;
    }
}
