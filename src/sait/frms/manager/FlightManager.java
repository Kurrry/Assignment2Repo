package sait.frms.manager;

import sait.frms.problemdomain.Flight;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FlightManager {
    public final String WEEKDAY_ANY = "";
    public final String WEEKDAY_SUNDAY = "Sunday";
    public final String WEEKDAY_MONDAY = "Monday";
    public final String WEEKDAY_TUESDAY = "Tuesday";
    public final String WEEKDAY_WEDNESDAY = "Wednesday";
    public final String WEEKDAY_THURSDAY = "Thursday";
    public final String WEEKDAY_FRIDAY = "Friday";
    public final String WEEKDAY_SATURDAY = "Saturday";
    private ArrayList<Flight> flights;
    private ArrayList<String> airports;

    public FlightManager() {

    }

    public ArrayList<String> getAirports() {
        File airportFile = new File("C:\\Users\\User\\Desktop\\CRPG251\\Assignment2\\CPRG251Assignment2DataFiles\\CPRG251Assignment2DataFiles\\airportList.csv");

        try (Scanner fileScanner = new Scanner(airportFile)) {
            while (fileScanner.hasNextLine()) {
                airports.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return airports;
    }

    public ArrayList<Flight> getFlights() {
        File flightFile = new File("C:\\Users\\User\\Desktop\\CRPG251\\Assignment2\\CPRG251Assignment2DataFiles\\CPRG251Assignment2DataFiles\\flights.csv");

        /*String flightCode, String fromCode, String toCode, String weekday,
                  String time, int seats, double costPerSeat*/
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

                //need to finish still
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return flights;
    }

}

//FRONT END WILL CATCH EXCEPTIONS
