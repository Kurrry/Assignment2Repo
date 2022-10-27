package sait.frms.manager;

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

	public void printReservations() {
		for (Reservation r : reservations) {
			System.out.println(r);
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

		Reservation reservation = new Reservation(generateReservationCode(flight), flight.getFlightCode(), flight.getAirlineName(flight.getFlightCode()),
				name, citizenship, flight.getCostPerSeat());
		reservation.setActiveSeatControl(status, flight);
		reservations.add(reservation);

		return reservation;
	}

	public void updateReservation (Flight flight, String reservationCode, String name, String citizenship, boolean status) {
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

		for(Reservation r : reservations) {
			if (r.getFlightCode().equals(code) || r.getAirline().equals(airline) || r.getName().equals(name)) {
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
				break;
			}
		}
		return reserve;
	}

	public void persist() {
		try (RandomAccessFile raf = new RandomAccessFile(RESERVE_FILE, READ_WRITE)) {
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
		String reservationCode = "";
		String flightCode = "";
		String airline = "";
		String name = "";
		String citizenship = "";
		double cost = 0;
		boolean isActive = false;

		RandomAccessFile raf = new RandomAccessFile(RESERVE_FILE, READ_WRITE);
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
}




//persist() method saves reservations to the binary file
//idea for persist(). turn values into a string and write as a whole string then split string during populate method
