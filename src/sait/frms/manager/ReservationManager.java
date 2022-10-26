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
	private static final String READ = "r";
	private static final String WRITE = "w";
	ArrayList<Reservation> reservations = new ArrayList<>();
	
	public ReservationManager() {

	}

	public Reservation makeReservation(Flight flight, String name, String citizenship) throws NoNameException, NoCitizenshipException,
			NoSeatsAvailableException {
		StringBuilder reservationCode = new StringBuilder();


		if (name == null || name.equals("")) {
			throw new NoNameException();
		}

		if (citizenship == null || citizenship.equals("")) {
			throw new NoCitizenshipException();
		}

		if (getAvailableSeats(flight) == 0) {
			throw new NoSeatsAvailableException();
		}

		Reservation reservation = new Reservation(generateReservationCode(flight), flight.getFlightCode(), flight.getAirlineName(flight.getFlightCode()),
				name, citizenship, flight.getCostPerSeat());
		flight.decrementSeats(1);
		reservation.setActive(true);
		reservations.add(reservation);

		return reservation;
	}
	
	public ArrayList<Reservation> findReservations(String code, String airline, String name) {
		ArrayList<Reservation> foundRes = new ArrayList<>();

		for(Reservation r : reservations) {
			if (r.getFlightCode().equals(code) && r.getAirline().equals(airline) && r.getName().equals(name)) {
				foundRes.add(r);
			}
		}
		return foundRes;
	}

	public Reservation findReservationByCode (String code) {
		Reservation reserve = null;

		for (Reservation r : reservations) {
			if (r.getReservationCode().equals(code)) {
				reserve = r;
			}
		}
		return reserve;
	}

	public void persist() {
		try (RandomAccessFile raf = new RandomAccessFile(RESERVE_FILE, WRITE)) {
			for (Reservation r : reservations) {
				String reservationCode = String.format("%s-5", r.getReservationCode());
				String flightCode = String.format("%s-7", r.getFlightCode());
				String airline = String.format("%s-17", r.getAirline());
				String name = String.format("%s-50", r.getName());
				String citizenship = String.format("%s-75", r.getCitizenship());

				raf.writeUTF(reservationCode);
				raf.writeUTF(flightCode);
				raf.writeUTF(airline);
				raf.writeUTF(name);
				raf.writeUTF(citizenship);
				raf.writeDouble(r.getCost());
				raf.writeBoolean(r.isActive());
				raf.writeUTF("\n");
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
			for (int i = 0; i < 4; i++) {
				int temp = random.nextInt(10);
				if (i == 0 && temp == 0) {
					temp++;
				}
				reservationCode.append(temp);
			}
		}
		return String.valueOf(reservationCode);
	}

	private void populateFromBinary() throws IOException {
		boolean endOfFile = false;
		String reservationCode = "";
		String flightCode = "";
		String airline = "";
		String name = "";
		String citizenship = "";
		double cost = 0;
		boolean isActive = false;

		RandomAccessFile raf = new RandomAccessFile(RESERVE_FILE, READ);
			while (!endOfFile) {
				try {
					reservationCode = raf.readUTF();
					flightCode = raf.readUTF();
					airline = raf.readUTF();
					name = raf.readUTF();
					citizenship = raf.readUTF();
					cost = raf.readDouble();
					isActive = raf.readBoolean();
				} catch (IOException e) {
					endOfFile = true;
				}
				Reservation tempReserve = new Reservation(reservationCode, flightCode, airline, name, citizenship, cost);
				tempReserve.setActive(isActive);
				reservations.add(tempReserve);
			}
			raf.close();
	}
}


//persist() method saves reservations to the binary file
