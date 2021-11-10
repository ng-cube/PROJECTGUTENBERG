import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BookingManagement implements OPInterface {
	private Scanner sc = new Scanner(System.in);
	private ArrayList<Reservation> ReservationList;
	private ArrayList<Table> TableList;
	//constructor
	public BookingManagement(ArrayList<Table> TableList){
		this.ReservationList = new ArrayList<Reservation>();
		this.TableList = TableList;
		Path path = Paths.get("Reservation.txt");
		try (BufferedReader br = Files.newBufferedReader(path)) {
			String line = br.readLine();

			while (line != null) {
				String[] tokens = line.split(",");
				Date date;
				DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
				try {
					date = df1.parse(tokens[3]);
				} catch (ParseException e) {
					date = new Date();
				}
				Reservation reservation = new Reservation(Integer.parseInt(tokens[0]), tokens[1], tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), date, Integer.parseInt(tokens[6]));
				ReservationList.add(reservation);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void displayInterface(){
		int user_choice = 0;
		int reservation_no;
		while (true)
		{
			System.out.println("Welcome to booking management.");
			System.out.println("(1) - Add a reservation");
			System.out.println("(2) - Display all reservations");
			System.out.println("(3) - Edit a reservation");
			System.out.println("(4) - Display single reservations");
			System.out.println("(5) - Remove a reservation");
			System.out.println("(6) - Quit");
			user_choice = sc.nextInt();
			
			switch (user_choice)
			{
				case 1:
					boolean added = false;
					// Add
					try{
						added = add();
					}catch(IOException e){
						System.out.println("Unable to write to file.");
					}
					if(!added){
						System.out.println("The order was not created. Please refer to error message above.");
					}
					else{
						System.out.println("Order created successfully.");
					}
					break;
					
				case 2:
					// VIEW a current order
					display();
					break;
					
				case 3:
					// Edit an order
					System.out.println("Please enter the ID of the reservation you would like to modify:");
					reservation_no = sc.nextInt();
					if(invalid(reservation_no)){
						System.out.println("Invalid ID. Please try again.");
						break;
					}
					boolean edited = false;
					try{
						edited = edit(reservation_no);
					}catch(IOException e){
						System.out.println("Unable to write to file.");
					}
					
					if(edited){
						System.out.println("The operation was successful.");
					}else{
						System.out.println("The operation was unsuccessful, please refer to the error above.");
					}

					break;
					
				case 4:
					System.out.println("Please enter the ID of the reservation you would like to check:");
					reservation_no = sc.nextInt();
					if(invalid(reservation_no)){
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
					try{
						removed = remove(reservation_no);
					}catch(IOException e){
						System.out.println("Unable to write to file.");
					}
					if(!removed){
						System.out.println("The order was not created. Please refer to error message above.");
					}
					else{
						System.out.println("Order created successfully.");
					}
					break;
					
				case 6:
					return;
					
				default:
					System.out.println("Invalid input, please select one of the options above.");
					break;
			}
		}
	}


	public boolean add() throws IOException{
		Date date;
		DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
		
		System.out.println("Please enter the number of pax");
		int nrPax = sc.nextInt();
		
		int tableID;
		tableID = assignTable(nrPax);
		if(tableID == -1){
			System.out.println("Table is not assigned successfully");
			return false;
		}

		System.out.println("Please enter the name of the customer");
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
		this.ReservationList.add(new Reservation(this.ReservationList.size()+1, name, contact, nrPax, tableID, date, hour));
		
		
		//create object
		write();
		System.out.println("New reservation created successfully.");
		return true;
	}

	/**
	 * 
	 * @param id
	 */
	public boolean remove(int id) throws IOException{
		if (id >= 0 && id <= ReservationList.size()){
			ReservationList.remove(id);
			write();
			return true;
		}else{
			return false;
		} 
	}

	/**
	 * 
	 * @param id
	 */
	public boolean edit(int id) throws IOException {
		boolean updated = false;
		Reservation reservation = ReservationList.get(id);
		System.out.println("What would you like to edit:");
		System.out.println("(1) - Number of Pax");
		System.out.println("(2) - Table ID");
		System.out.println("(3) - Name");
		System.out.println("(4) - Contact Number");
		System.out.println("(5) - Quit");
		
		int user_choice = sc.nextInt();
			
		switch (user_choice)
		{
			case 1:
				System.out.println("Updated number of pax: ");
				int nrPax = sc.nextInt();
				reservation.setNrPax(nrPax);
				ReservationList.set(id, reservation);
				updated = true;
				break;
									
			case 2:
				System.out.println("Change to Table ID: ");
				int TableID = sc.nextInt();
				reservation.setTableID(TableID);
				ReservationList.set(id, reservation);
				updated = true;
				break;
				
			case 3:
				System.out.println("Please enter the new customer name: ");
				String name = sc.nextLine();
				reservation.setName(name);
				ReservationList.set(id, reservation);
				updated = true;
				break;
				
			case 4:
				System.out.println("Please enter the new contact number: ");
				String contactnr = sc.nextLine();
				reservation.setContact(contactnr);
				ReservationList.set(id, reservation);
				updated = true;
				break;
				
			case 5:

				break;
				
			default:
				System.out.println("Invalid input, please enter a valid option.");
				break;
		}
		write();
		return updated;
	}

	
	public void display() {
		for(Reservation item: ReservationList){
			item.printReservation();
		}
		return;
	}


	public void sort() {
		return;
	}

	public int removeExpiredReservation() {
		int removed = 0;
		Date date = new Date();

		for(Reservation item: ReservationList){
			int before = item.getDate().compareTo(date);
			if(before < 0){
				ReservationList.remove(item);
				removed ++;
			}
			else if(before == 0){
				Calendar rightNow = Calendar.getInstance(); 
				int hour = rightNow.get(Calendar.HOUR_OF_DAY);

				if (hour > item.getStartTime()){
					ReservationList.remove(item);
					removed ++;
				}
			}
		}

		return removed;
	}

	public ArrayList <Table> getAvailableTables() {
		int nrOfAvailableTables=0;
		ArrayList <Table> availableTables = new ArrayList<>();
		System.out.println("\n" + new String(new char[87]).replace("\0", "-"));
		System.out.print(String.format("%6s %18s %21s %32s %6s", "|     ", "Table ID", "|    ", "Capacity of Tables available", "   |"));
		System.out.println("\n" + new String(new char[87]).replace("\0", "-"));
		for (Table i:TableList){
			if (i.getAvailability()==true) {
				availableTables.add(i);
				System.out.print(String.format("%6s %18s %21s %32s %6s", "|     ", i.getTableID(), "|    ", i.getCapacity(), "|"));
				System.out.println("\n" + new String(new char[87]).replace("\0", "-"));
				nrOfAvailableTables++;
			}
		}
		if (nrOfAvailableTables==0){
			System.out.print(String.format("%20s %35s %30s", "|                   "," There are no tables available now.", "                        |"));
			System.out.println("\n" + new String(new char[87]).replace("\0", "-"));
		}
		return availableTables;
	}

	/**
	 * 
	 * @param reservation
	 */
	public int assignTable(int nrPax) {
		ArrayList <Table> availTables = getAvailableTables();
		for(Table i:availTables){
			if(nrPax<i.getCapacity()){
				return i.getTableID();
			}
		}
		return -1; 
	}

	/**
	 * 
	 * @param id
	 */
	public Reservation get(int id) {
		return ReservationList.get(id);
	}

	
	public void write() throws IOException {
		Path path = Paths.get("Reservation.csv");
		FileWriter fw = new FileWriter(String.valueOf(path));

		for(Reservation reservation: this.ReservationList) {
			try {
				fw.write(reservation.getReservationID() + ",");
				fw.write(reservation.getName() + ",");
				fw.write(reservation.getContact() + ",");
				fw.write(reservation.getNrPax() + ",");
				fw.write(reservation.getTableID() + ",");
				fw.write(reservation.getDate() + ",");
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

	public boolean invalid(int order_no){
		if(order_no >= ReservationList.size() || order_no < 0){
			return false;
		}

		return true;
	}

}
