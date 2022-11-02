package sait.frms.problemdomain;

public class Airport {
    private String airportCode;
    private String airportName;

    /**
     * constructor for the Airport class
     *
     * @param airportCode code indicating the airport
     * @param airportName name of the airport
     */
    public Airport(String airportCode, String airportName) {
        this.airportCode = airportCode;
        this.airportName = airportName;
    }

    /**
     * Method to get the airport code
     *
     * @return airportCode code for the airport
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * method to get the airport name
     *
     * @return name of the airport
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * Method to create a String with the airportCode and airportName
     *
     * @return String of the airportCode and airportName
     */
    @Override
    public String toString() {
        return getAirportCode() + getAirportName();
    }
}
