package sait.frms.manager;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;

import sait.frms.exception.NoCitizenshipException;
import sait.frms.exception.NoNameException;
import sait.frms.exception.NoSeatsAvailableException;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

public class ReservationManager {

    private static final String RESERVE_FILE = "res\\reserves.bin";
    private static final String READ_WRITE = "rw";
    private final ArrayList<Reservation> reservations = new ArrayList<>();

    /**
     * constructor for the ReservationManager
     * populates the reservation list from the reserves.bin file using the populateFromBinary method
     */
    public ReservationManager() {
        try {
            populateFromBinary();
        } catch (IOException ex) {
            ex.printStackTrace();
            //placeholder
        }
    }

    /**
     * method to get the list of reservations
     * @return reservations ArrayList containing the reservations
     */
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    /**
     * method for making a reservation on a flight
     *
     * @param flight flight that is being booked
     * @param name name of the person booking the flight
     * @param citizenship citizenship of the person booking the flights
     * @return reservation reservation for the flight
     * @throws NoNameException if the no name is entered for the reservation
     * @throws NoCitizenshipException if no citizenship is entered for the reservation
     * @throws NoSeatsAvailableException if there are no seats left on the flight being reserved
     */
    public Reservation makeReservation(Flight flight, String name, String citizenship) throws NoNameException, NoCitizenshipException,
            NoSeatsAvailableException {

        if (name == null || name.equals("")) {
            throw new NoNameException();
        }

        if (citizenship == null || citizenship.equals("")) {
            throw new NoCitizenshipException();
        }

        if (getAvailableSeats(flight) == 0) {
            throw new NoSeatsAvailableException();
        }

        Reservation reservation = new Reservation(generateReservationCode(flight), flight.getFlightCode(), flight.getAirlineName(),
                name, citizenship, flight.getCostPerSeat());
        reservation.setActiveSeatControl(true, flight);
        reservations.add(reservation);

        return reservation;
    }

    /**
     * method for updating an existing reservation. Only name, citizenship and status may be changed after a reservation is made.
     *
     * @param flight flight for the reservation being updated
     * @param reservationCode reservationCode for the reservation being updated
     * @param name name of the person whose reservation is being updated
     * @param citizenship citizenship of the person whose reservation is being updated
     * @param status status of the reservation being updated. Active or Inactive
     */
    public void updateReservation(Flight flight, String reservationCode, String name, String citizenship, boolean status) {
        for (Reservation r : reservations) {
            if (r.getReservationCode().equals(reservationCode)) {
                r.setActiveSeatControl(status, flight);
                r.setName(name);
                r.setCitizenship(citizenship);
                break;
            }
        }
    }

    /**
     * method for searching the list of reservations based on any of the 3 params
     *
     * @param code code for the reservation being searched
     * @param airline name of the airline on a reservation
     * @param name name of the person on a reservation
     * @return foundRes ArrayList containing the reservations that match any of the params
     */
    public ArrayList<Reservation> findReservations(String code, String airline, String name) {
        ArrayList<Reservation> foundRes = new ArrayList<>();

        for (Reservation r : reservations) {
            if (r.getReservationCode().equalsIgnoreCase(code) || r.getAirline().equalsIgnoreCase(airline) ||
                    r.getName().equalsIgnoreCase(name)) {
                foundRes.add(r);
            }
        }
        return foundRes;
    }

    /**
     * Method to save the list of reservations to the reserves.bin binary file.
     * Method is called when the user exits the system window via the 'X' button.
     */
    public void persist() {
        try (RandomAccessFile raf = new RandomAccessFile(RESERVE_FILE, READ_WRITE)) {
            for (Reservation r : reservations) {
                String reservationCode = String.format("%-5s", r.getReservationCode());
                String flightCode = String.format("%-7s", r.getFlightCode());
                String airline = String.format("%-17s", r.getAirline());
                String name = String.format("%-50s", r.getName());
                String citizenship = String.format("%-75s", r.getCitizenship());

                raf.writeUTF(reservationCode);
                raf.writeUTF(flightCode);
                raf.writeUTF(airline);
                raf.writeUTF(name);
                raf.writeUTF(citizenship);
                raf.writeDouble(r.getCost());
                raf.writeBoolean(r.isActive());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            //place holder
        }
    }

    /**
     * Method to find the available seats
     *
     * @param flight flight whose seats are being counted
     * @return int indicating the number of seats available
     */
    private int getAvailableSeats(Flight flight) {
        return flight.getSeats();
    }

    /**
     * Method to generate the reservation code for a reservation after it is made
     *
     * @param flight flight that the reservation is being made for
     * @return reservationCode code associated with a reservation
     */
    private String generateReservationCode(Flight flight) {
        StringBuilder reservationCode = new StringBuilder();
        Random random = new Random();

        if (flight.isDomestic()) {
            reservationCode.append("D");
        } else {
            reservationCode.append("I");
        }

        for (int i = 0; i < 4; i++) {
            int temp = random.nextInt(10);
            if (i == 0 && temp == 0) {
                temp++;
            }
            reservationCode.append(temp);
        }

        return String.valueOf(reservationCode);
    }

    /**
     * method for populating the reservations ArrayList from the reserves.bin file.
     * @throws IOException if the file does not exist
     */
    private void populateFromBinary() throws IOException {
        boolean endOfFile = false;
        String reservationCode;
        String flightCode;
        String airline;
        String name;
        String citizenship;
        double cost;
        boolean isActive;

        RandomAccessFile raf = new RandomAccessFile(RESERVE_FILE, READ_WRITE);
        if (raf.length() != 0) {
            while (!endOfFile) {
                try {
                    reservationCode = raf.readUTF().trim();
                    flightCode = raf.readUTF().trim();
                    airline = raf.readUTF().trim();
                    name = raf.readUTF().trim();
                    citizenship = raf.readUTF().trim();
                    cost = raf.readDouble();
                    isActive = raf.readBoolean();

                    Reservation tempReserve = new Reservation(reservationCode, flightCode, airline, name, citizenship, cost);
                    tempReserve.setActive(isActive);
                    reservations.add(tempReserve);
                } catch (EOFException e) {
                    endOfFile = true;
                }
            }

        }
        raf.close();
    }
}

//code for testing purposes
/*for testing purposes
    public void printReservations() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(RESERVE_FILE, READ_WRITE);
        boolean endOfFile = false;
        String reservationCode;
        String flightCode;
        String airline;
        String name;
        String citizenship;
        double cost;
        boolean isActive;

        while (!endOfFile) {
            try {
                reservationCode = raf.readUTF().trim();
                flightCode = raf.readUTF().trim();
                airline = raf.readUTF().trim();
                name = raf.readUTF().trim();
                citizenship = raf.readUTF().trim();
                cost = raf.readDouble();
                isActive = raf.readBoolean();
                System.out.println(reservationCode + " " + flightCode + " " + airline + " " + name + " " + citizenship + " " + cost
                        + " " + isActive);
            } catch (IOException e) {
                endOfFile = true;
            }
        }
        raf.close();

        System.out.println("\n" + reservations);
    }*/
/*
	private void persistTest() {
		try (RandomAccessFile raf = new RandomAccessFile(RESERVE_FILE, WRITE)) {
			for (Reservation r : reservations) {
				String writeMsg = String.format("%b,%s-5,%s-7,%s-17,%s-50,%s-75,%f\n", r.isActive(), r.getReservationCode(), r.getFlightCode(),
						r.getAirline(), r.getName(), r.getCitizenship(), r.getCost());

				raf.writeUTF(writeMsg);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			//place holder
		}
	}


	private void populateTest() throws IOException {
		boolean endOfFile = false;
		String reservationCode = "";
		String flightCode = "";
		String airline = "";
		String name = "";
		String citizenship = "";
		double cost = 0;
		boolean isActive = false;
		String[] temp;

		RandomAccessFile raf = new RandomAccessFile(RESERVE_FILE, READ);
		while (!endOfFile) {
			try {
				temp = raf.readLine().split(",");
				isActive = Boolean.parseBoolean(temp[0]);
				reservationCode = temp[1].trim();
				flightCode = temp[2].trim();
				airline = temp[3].trim();
				name = temp[4].trim();
				citizenship = temp[5].trim();
				cost = Double.parseDouble(temp[6]);
			} catch (IOException e) {
				endOfFile = true;
			}
			Reservation tempReserve = new Reservation(reservationCode, flightCode, airline, name, citizenship, cost);
			tempReserve.setActive(isActive);
			reservations.add(tempReserve);
		}
		raf.close();
	}*/

//persist() method saves reservations to the binary file
//idea for persist(). turn values into a string and write as a whole string then split string during populate method
