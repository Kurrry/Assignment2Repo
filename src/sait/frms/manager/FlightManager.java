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
                //pretty sure this is done. could swap the variables to inline but descriptive name good
            }
        } catch (FileNotFoundException ignored) {

        }
    }

    public String findAirportByCode(String code) {
        return "Finish me senpai";
    }

}

//FRONT END WILL CATCH EXCEPTIONS
