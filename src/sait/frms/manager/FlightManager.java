package sait.frms.manager;

import sait.frms.exception.NoFlightFoundException;
import sait.frms.problemdomain.Airport;
import sait.frms.problemdomain.Flight;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FlightManager {
    public final String WEEKDAY_ANY = "Any";
    public final String WEEKDAY_SUNDAY = "Sunday";
    public final String WEEKDAY_MONDAY = "Monday";
    public final String WEEKDAY_TUESDAY = "Tuesday";
    public final String WEEKDAY_WEDNESDAY = "Wednesday";
    public final String WEEKDAY_THURSDAY = "Thursday";
    public final String WEEKDAY_FRIDAY = "Friday";
    public final String WEEKDAY_SATURDAY = "Saturday";
    private ArrayList<Flight> flights = new ArrayList<>();
    private ArrayList<Airport> airports = new ArrayList<>();

    public FlightManager() {
        populateAirports();
        populateFlights();
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public ArrayList<Airport> getAirports() {
        return airports;
    }

    public String findAirportByCode(String code) {
        for(Airport a : airports) {
            if (a.getAirportCode().equals(code)) {
                return a.toString();
            }
        }
        return "No airport found with that code";
    }

    public String findFlightByCode(String code) {
        for(Flight f : flights) {
            if (f.getFlightCode().equals(code)) {
                return f.toString();
            }
        }
        return "No flight found with that code";
    }

    public ArrayList<Flight> findFlights (String fromCode, String toCode, String weekday) {
        ArrayList<Flight> flightsFound = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getFromCode().equals(fromCode) && f.getToCode().equals(toCode) && f.getWeekday().equals(weekday)) {
                flightsFound.add(f);
            }
        }
        return flightsFound;
    }

    private void populateAirports() {
        File airportFile = new File("C:\\Users\\User\\Desktop\\CRPG251\\Assignment2\\CPRG251Assignment2DataFiles\\CPRG251Assignment2DataFiles\\airports.csv");

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

    private void populateFlights() {
        File flightFile = new File("C:\\Users\\User\\Desktop\\CRPG251\\Assignment2\\CPRG251Assignment2DataFiles\\CPRG251Assignment2DataFiles\\flights.csv");

        try (Scanner fileScanner = new Scanner(flightFile)) {
            fileScanner.useDelimiter(",|\r\n");
            while (fileScanner.hasNextLine()) {
                String temp = fileScanner.nextLine();
                String[] tempFlight = temp.split(",");

                String flightCode = tempFlight[0];
                String fromCode = tempFlight[1];
                String toCode = tempFlight[2];
                String weekday = tempFlight[3];
                String time = tempFlight[4];
                int seats = Integer.parseInt(tempFlight[5]);
                double costPerSeats = Double.parseDouble(tempFlight[6]);

                try {
                    Flight flight = new Flight(flightCode, fromCode, toCode, weekday, time, seats, costPerSeats);
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
