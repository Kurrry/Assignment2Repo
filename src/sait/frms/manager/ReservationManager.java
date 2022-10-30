package sait.frms.manager;

import java.io.EOFException;
import java.io.FileNotFoundException;
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
    ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationManager() {
        try {
            populateFromBinary();
        } catch (IOException ex) {
            ex.printStackTrace();
            //placeholder
        }
    }

    public Reservation makeReservation(Flight flight, String name, String citizenship, boolean status) throws NoNameException, NoCitizenshipException,
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
        reservation.setActiveSeatControl(status, flight);
        reservations.add(reservation);

        return reservation;
    }

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

    public ArrayList<Reservation> findReservations(String code, String airline, String name) {
        ArrayList<Reservation> foundRes = new ArrayList<>();

        for (Reservation r : reservations) {
            if (r.getFlightCode().equals(code) || r.getAirline().equals(airline) || r.getName().equals(name)) {
                foundRes.add(r);
            }
        }
        return foundRes;
    }

    public Reservation findReservationByCode(String code) {
        Reservation reserve = null;

        for (Reservation r : reservations) {
            if (r.getReservationCode().equals(code)) {
                reserve = r;
                break;
            }
        }
        return reserve;
    }

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

    private int getAvailableSeats(Flight flight) {
        return flight.getSeats();
    }

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
