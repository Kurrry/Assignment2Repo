package sait.frms.manager;

import sait.frms.exception.NoFlightFoundException;
import sait.frms.problemdomain.Airport;
import sait.frms.problemdomain.Flight;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//"C:\\Users\\User\\Desktop\\CRPG251\\Assignment2\\CPRG251Assignment2DataFiles\\CPRG251Assignment2DataFiles\\flights.csv" avery laptop path
//"C:\\Users\\User\\Desktop\\CRPG251\\Assignment2\\CPRG251Assignment2DataFiles\\CPRG251Assignment2DataFiles\\airports.csv" avery laptop path
public class FlightManager {
    public final String WEEKDAY_ANY = "Any";
    private final ArrayList<Flight> flights = new ArrayList<>();
    private final ArrayList<Airport> airports = new ArrayList<>();

    /**
     * constructor for the FlightManager.
     * calls populateAirports and populateFlights
     */
    public FlightManager() {
        populateFlights();
        populateAirports();
    }

    /**
     *
     * @return the flights
     */
    public ArrayList<Flight> getFlights() {
        return this.flights;
    }

    /**
     *
     * @return airportCodes
     */
    public ArrayList<String> getAirports() {
        ArrayList<String> airportCodes = new ArrayList<>();

        for (Airport a : airports) {
            airportCodes.add(a.getAirportCode());
        }
        return airportCodes;
    }

    /**
     * This method has no use in the gui
     * @param code check for airport by code
     * @return airport string or default string
     */
    public Airport findAirportByCode(String code) {
        for(Airport a : airports) {
            if (a.getAirportCode().equals(code)) {
                return a;
            }
        }
        return null;
    }

    /**
     *
     * @param code check for flight by code
     * @return flight string or default string
     */
    public Flight findFlightByCode(String code) {
        for(Flight f : flights) {
            if (f.getFlightCode().equals(code)) {
                return f;
            }
        }
        return null;
    }

    /**
     * search flights list for all flights matching the parameters
     * @param fromCode fromCode for departing airport
     * @param toCode toCode for arriving airport
     * @param weekday day of the week the flight is
     * @return flightsFound containing flights meeting the parameters
     */
    public ArrayList<Flight> findFlights (String fromCode, String toCode, String weekday) {
        ArrayList<Flight> flightsFound = new ArrayList<>();

        if(weekday.equals(WEEKDAY_ANY)) {
            return anyDay(fromCode, toCode);
        }

        for (Flight f : flights) {
            if (f.getFromCode().equals(fromCode) && f.getToCode().equals(toCode) && f.getWeekday().equals(weekday)) {
                flightsFound.add(f);
            }
        }
        return flightsFound;
    }

    private ArrayList<Flight> anyDay (String fromCode, String toCode) {
        ArrayList<Flight> flightsFound = new ArrayList<>();

        for (Flight f : flights) {
            if (f.getFromCode().equals(fromCode) && f.getToCode().equals(toCode)) {
                flightsFound.add(f);
            }
        }
        return flightsFound;
    }

    /**
     * populate airports list from airports.csv
     */
    private void populateAirports() {
        File airportFile = new File("res\\airports.csv");

        try (Scanner fileScanner = new Scanner(airportFile)) {
            fileScanner.useDelimiter(",|\r\n");
            while (fileScanner.hasNextLine()) {
                String temp = fileScanner.nextLine();
                String[] tempAir = temp.split(",");

                String airportCode = tempAir[0];
                String airportName = tempAir[1];

                Airport airport = new Airport(airportCode, airportName);
                airports.add(airport);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * populate flights list using the flights.csv file.
     */
    private void populateFlights() {
        File flightFile = new File("res\\flights.csv");

        try (Scanner fileScanner = new Scanner(flightFile)) {
            fileScanner.useDelimiter(",|\r\n");
            while (fileScanner.hasNextLine()) {
                String temp = fileScanner.nextLine();
                String[] tempFlight = temp.split(",");

                String flightCode = tempFlight[0];
                String airlineName = tempFlight[1];
                String fromCode = tempFlight[2];
                String toCode = tempFlight[3];
                String weekday = tempFlight[4];
                String time = tempFlight[5];
                int seats = Integer.parseInt(tempFlight[6]);
                double costPerSeats = Double.parseDouble(tempFlight[7]);

                try {
                    Flight flight = new Flight(flightCode, airlineName, fromCode, toCode, weekday, time, seats, costPerSeats);
                    flights.add(flight);
                } catch (NoFlightFoundException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (FileNotFoundException ignored) {
            //ignored exception because file always exists for us+
        }
    }
}

//FRONT END WILL CATCH EXCEPTIONS
