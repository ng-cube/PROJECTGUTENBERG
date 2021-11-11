import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
//import java.text.ParsePosition;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;

public class BookingManagement implements OPInterface {
	private Scanner sc = new Scanner(System.in);
	private ArrayList<Reservation> ReservationList;
	private ArrayList<Table> TableList = new ArrayList<>();
	

	public BookingManagement() {
		this.ReservationList = new ArrayList<>();
		if (!read()) {
			System.out.println("File corrupted.");
		}
	}

	public boolean readTable() {
		Path path = Paths.get("Table.txt");
		try (BufferedReader br = Files.newBufferedReader(path)) {
			String line = br.readLine();

			while (line != null) {
				String[] tokens = line.split(",");

				ArrayList<String> reserveSlots = new ArrayList<>();
				for(int i=0;i<tokens.length-4;i++){
					reserveSlots.add(tokens[4+i]);
				}

				Table table = new Table(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Boolean.parseBoolean(tokens[2]), 
										Boolean.parseBoolean(tokens[3]),reserveSlots);
				TableList.add(table);
				line = br.readLine();
			}
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public boolean read() {
		Path path = Paths.get("Reservation.txt");
		//ParsePosition pos = new ParsePosition(0);
		try (BufferedReader br = Files.newBufferedReader(path)) {
			String line = br.readLine();

			while (line != null) {
				String[] tokens = line.split(",");
				Date date;
				DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
				try {
					date = df1.parse(tokens[5]);
				} catch (ParseException e) {
					date = new Date();
				}
				Reservation reservation = new Reservation(Integer.parseInt(tokens[0]), tokens[1], tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), date, Integer.parseInt(tokens[6]));
				ReservationList.add(reservation);
				line = br.readLine();
			}
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public void displayInterface() throws IOException {
		readTable();
		int user_choice = 0;
		int reservation_no;
		while (true) {
			System.out.println("Welcome to booking management.");
			System.out.println("(1) - Add a reservation");
			System.out.println("(2) - Display all reservations");
			System.out.println("(3) - Edit a reservation");
			System.out.println("(4) - Choose a single reservation to view");
			System.out.println("(5) - Remove a reservation");
			System.out.println("(6) - Quit");
			user_choice = sc.nextInt();

			switch (user_choice) {
				case 1:
					boolean added = false;
					// Add
					try {
						added = add();
					} catch (IOException e) {
						System.out.println("Unable to write to file.");
					}
					if (!added) {
						System.out.println("The order was not created. Please refer to error message above.");
					} else {
						System.out.println("Order created successfully.");
					}
					break;

				case 2:
					// VIEW a current order
					display();
					break;

				case 3:
					// Edit an order
					System.out.println("From number 0 to "+(this.ReservationList.size()-1)+", please enter the ID of the reservation you would like to modify:");
					reservation_no = sc.nextInt();
					if (invalid(reservation_no)) {
						System.out.println("Invalid ID. Please try again.");
						break;
					}
					boolean edited = false;
					try {
						edited = edit(reservation_no);
					} catch (IOException e) {
						System.out.println("Unable to write to file.");
					}

					if (edited) {
						System.out.println("The operation was successful.");
					} else {
						System.out.println("The operation was unsuccessful, please refer to the error above.");
					}

					break;

				case 4:
					System.out.println("Please enter the ID of the reservation you would like to check:");
					reservation_no = sc.nextInt();
					if (invalid(reservation_no)) {
						System.out.println("Invalid ID, please try again later.");
						break;
					}
					Reservation reservation = get(reservation_no);
					reservation.printReservation();

					break;

				case 5:
					System.out.println("Please enter the ID of the reservation you would like to remove:");
					reservation_no = sc.nextInt();
					boolean removed = false;
					try {
						removed = remove(reservation_no);
					} catch (IOException e) {
						System.out.println("Unable to write to file.");
					}
					if (!removed) {
						System.out.println("The reservation was not removed.Please refer to error message above.");
					} else {
						System.out.println("Reservation removed successfully.");
					}
					break;

				case 6:
					writeTable();
					return;

				default:
					System.out.println("Invalid input, please select one of the options above.");
					break;
			}
		}
	}


	public boolean add() throws IOException {
		Date date;
		DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");

		//available tables

		System.out.println("Please enter the number of pax");
		int nrPax = sc.nextInt();

		int tableID;
		tableID = assignTable(nrPax);
		if (tableID == -1) {
			System.out.println("Table is not assigned successfully");
			return false;
		}

		System.out.println("Please enter the name of the customer");
		sc.nextLine();
		String name = sc.nextLine();

		System.out.println("Please enter the contact number of the customer");
		String contact = sc.nextLine();

		System.out.println("Please enter the reservation date:");
		String inputdate = sc.nextLine();
		try {
			date = df1.parse(inputdate);
		} catch (ParseException e) {
			date = new Date();
		}

		System.out.println("Please enter the reservation time:");
		int hour = sc.nextInt();
		this.ReservationList.add(new Reservation(this.ReservationList.size(), name, contact, nrPax, tableID, date, hour));
		
		//update the corresponding Table object
		String slot = strDateTime(hour, date);
		for(int i=0;i<10;i++){
			if (i==tableID){
				TableList.get(i).getSlots().add(slot);
			}
		}
		//create object
		write();
		writeTable();
		System.out.println("New reservation created successfully.");
		return true;
	}

	/**
	 * @param id
	 */
	public boolean remove(int id) throws IOException {
		if (id >= 0 && id <= ReservationList.size()) {
			ReservationList.remove(id);
			write();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param id
	 */
	public boolean edit(int id) throws IOException {
		boolean updated = false;
		Reservation reservation = ReservationList.get(id);
		System.out.println("What would you like to edit:");
		//System.out.println("(1) - Number of Pax");
		//System.out.println("(2) - Table ID");
		System.out.println("(1) - Name");
		System.out.println("(2) - Contact Number");
		System.out.println("(3) - Quit");

		int user_choice = sc.nextInt();

		switch (user_choice) {
			/*case 1:
				System.out.println("Updated number of pax: ");
				int nrPax = sc.nextInt();
				reservation.setNrPax(nrPax);
				ReservationList.set(id, reservation);
				updated = true;
				break;*/

			/*case 2:
				System.out.println("Change to Table ID: ");
				int TableID = sc.nextInt();
				reservation.setTableID(TableID);
				ReservationList.set(id, reservation);
				updated = true;
				break;*/

			case 1:
				System.out.println("Please enter the new customer name: ");
				sc.nextLine();
				String name = sc.nextLine();
				reservation.setName(name);
				ReservationList.set(id, reservation);
				updated = true;
				break;

			case 2:
				System.out.println("Please enter the new contact number: ");
				sc.nextLine();
				String contactnr = sc.nextLine();
				reservation.setContact(contactnr);
				ReservationList.set(id, reservation);
				updated = true;
				break;

			case 3:

				break;

			default:
				System.out.println("Invalid input, please enter a valid option.");
				break;
		}
		write();
		return updated;
	}


	public void display() {
		for (Reservation item : ReservationList) {
			item.printReservation();
		}
		return;
	}


	public void sort() {
		return;
	}

	public int removeExpiredReservation() {
		int removed = 0;
		Date date = new Date();//current date

		for (Reservation item : ReservationList) {
			int before = item.getDate().compareTo(date);
			if (before < 0) {
				ReservationList.remove(item);
				removed++;
			} else if (before == 0) {
				Calendar rightNow = Calendar.getInstance();
				int hour = rightNow.get(Calendar.HOUR_OF_DAY);

				if (hour > item.getStartTime()) {
					ReservationList.remove(item);
					removed++;
				}
			}
		}

		return removed;
	}
	

	public ArrayList<Table> getAvailableTables() {
		int nrOfAvailableTables = 0;
		ArrayList<Table> availableTables = new ArrayList<>();

		Date date;
		DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");

		System.out.println("Please enter the date you would like to check:");
		String inputdate = sc.nextLine();
		try {
			date = df1.parse(inputdate);
		} catch (ParseException e) {
			date = new Date();
		}

		System.out.println("Please enter the time you would like to check(0-23):");
		int hour = sc.nextInt();
		
		String checkSlot = strDateTime(hour, date);
		for (Table i: TableList){
			if(!i.getSlots().contains(checkSlot)){
				availableTables.add(i);
				//printing available tables
				nrOfAvailableTables++;
			}
		}	
		//System.out.println("\n" + new String(new char[87]).replace("\0", "-"));
		//System.out.print(String.format("%6s %18s %21s %32s %6s", "|     ", "Table ID", "|    ", "Capacity of Tables available", "   |"));
		//System.out.println("\n" + new String(new char[87]).replace("\0", "-"));
		//for (Table i : TableList) {
		//	if (i.getAvailability()) {
				//availableTables.add(i);
				//System.out.print(String.format("%6s %18s %21s %32s %6s", "|     ", i.getTableID(), "|    ", i.getCapacity(), "|"));
				//System.out.println("\n" + new String(new char[87]).replace("\0", "-"));
				
			//}
		//}
		if (nrOfAvailableTables == 0) {
			System.out.println("There are no tables available now.");
			//System.out.print(String.format("%20s %35s %30s", "|                   ", " There are no tables available now.", "                        |"));
			//System.out.println("\n" + new String(new char[87]).replace("\0", "-"));
		}
		return availableTables;
	}

	/**
	 * @param reservation
	 */
	public int assignTable(int nrPax) {
		ArrayList<Table> availTables = getAvailableTables();
		for (Table i : availTables) {
			if (nrPax < i.getCapacity()) {
				return i.getTableID();
			}
		}
		return -1;
	}

	/**
	 * @param id
	 */
	public Reservation get(int id) {
		return ReservationList.get(id);
	}

	public void write() throws IOException {
		Path path = Paths.get("Reservation.txt");
		FileWriter fw = new FileWriter(String.valueOf(path));
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

		for (Reservation reservation : this.ReservationList) {
			try {
				fw.write(reservation.getReservationID() + ",");
				fw.write(reservation.getName() + ",");
				fw.write(reservation.getContact() + ",");
				fw.write(reservation.getNrPax() + ",");
				fw.write(reservation.getTableID() + ",");
				fw.write(format1.format(reservation.getDate())+",");
				
				fw.write(reservation.getStartTime() + "");
				fw.write(System.lineSeparator());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeTable() throws IOException {
		Path path = Paths.get("Table.txt");
		FileWriter fw = new FileWriter(String.valueOf(path));
		
		for (Table table : TableList) {
			try {
				fw.write(table.getTableID() + ",");
				fw.write(table.getCapacity() + ",");
				fw.write(table.getOccupied() + ",");
				fw.write(table.getAvailability() + ",");
				for(String item: table.getSlots()){
					fw.write(item + ",");
				}
				
			   
				fw.write(System.lineSeparator());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean invalid(int reservation_no) {
		if (reservation_no >= ReservationList.size() || reservation_no < 0) {
			return true;
		}
		return false;
	}

	public String strDateTime(int timeslot,Date date){
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String strDate = dateFormat.format(date);  
		String dateTime = strDate + timeslot;

		return dateTime;
	}

}