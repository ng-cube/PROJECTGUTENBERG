import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.DecimalFormat;
import java.nio.file.Path;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Files;

public class OrdersManagement implements OPInterface {
	private ArrayList<Order> OrderList;
	private ArrayList<Table> TableList;
	private StaffManagement StaffMgr;
	private MenuManagement MenuMgr;
	private static Scanner sc = new Scanner(System.in);


	public OrdersManagement(MenuManagement menuMgr, StaffManagement staffMgr, ArrayList<Table> tables){
		OrderList = new ArrayList<Order>();
		TableList = tables;
		StaffMgr = staffMgr;
		MenuMgr = menuMgr;
		
	}

	public void readtable() throws IOException{
		Path path = Paths.get("Table.txt");
		try (BufferedReader br = Files.newBufferedReader(path)) {
			String line = br.readLine();
	
			while (line != null) {
				String[] tokens = line.split(",");
				Table table = new Table(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Boolean.parseBoolean(tokens[2]), Boolean.parseBoolean(tokens[3]));
				TableList.add(table);
				line = br.readLine();
			}
		}
		catch(IOException e){
			System.out.println("Corrupted File.");
		}
	}

	public void writetable() throws IOException{
		Path path = Paths.get("Table.txt");
		FileWriter fw = new FileWriter(String.valueOf(path));

		for(Table table:TableList) {
			try {
				fw.write(table.getTableID() + ",");
				fw.write(table.getCapacity() + ",");
				fw.write(table.getOccupied() + ",");
				fw.write(table.getAvailability() + ",");
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

	public boolean read(){
		Path path = Paths.get("Orders.txt");
		try (BufferedReader br = Files.newBufferedReader(path)) {
			String line = br.readLine();

			while (line != null) {
				String[] tokens = line.split(",");
				int n = tokens.length;
				int j = 7;
				Date date;
				DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
				
				ArrayList<Integer> itemIDs = new ArrayList<>();

				while (j < n) {
					itemIDs.add(Integer.parseInt(tokens[j]));
					j++;
				}
				
				try {
					date = df1.parse(tokens[3]);
				} catch (ParseException f) {
					date = new Date();
				}

				Order order = new Order(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), date, Integer.parseInt(tokens[4]), Boolean.parseBoolean(tokens[5]), Boolean.parseBoolean(tokens[6]));
				// OrderList.size(), staffID, tableID, date, hour, membership, paid
				order.addItems(itemIDs);
				OrderList.add(order);
				line = br.readLine();
				if(Boolean.parseBoolean(tokens[6]) == false){
					TableList.get(Integer.parseInt(tokens[2])).setAvailability(false);
				}
			}
		}
		catch(IOException e){
			System.out.println("Invalid File.");
			return false;
		}

		return true;
	}
	
	public boolean add() throws IOException{
		boolean membership;
		Date date = new Date();
		System.out.println("Please enter your staff ID:");
		int staffID = sc.nextInt();
		if(staffID < 0 || staffID > StaffMgr.getStaffSize()){
			System.out.println("The ID you've entered is invalid.");
			return false;
		}
		
		System.out.println("Please enter the table ID:");
		int tableID = sc.nextInt();
		if(!TableList.get(tableID).getAvailability()){
			System.out.println("The table is occupied. Please try again.");
			return false;
		}
		TableList.get(tableID).setAvailability(false);
		
		System.out.println("Please enter the hour of reservation(24hr format):");
		int hour = sc.nextInt();

		System.out.println("Please enter membership:");
		System.out.println("\t(1) - Member");
		System.out.println("\t(2) - Non-member");
		int member = sc.nextInt();
		if(member == 1){
			membership = true;
		}
		else if(member == 2){
			membership = false;
		}
		else{
			System.out.println("Invalid choice. Please try again.");
			return false;
		}

		Order order = new Order(OrderList.size()+1, staffID, tableID, date, hour, membership, false);
		order.addItems(MenuMgr);
		OrderList.add(order);
		write();

		return true;
	}

	public boolean remove(int id) throws IOException{
		try{
			OrderList.remove(id-1);
			write();
			return true;
		}
		catch(IndexOutOfBoundsException ie){
			System.out.println("The chosen order does not exist.");
			return false;
		}
	}

	public boolean edit(int id) throws IOException {
		id = id - 1;
		Order order = get(id);
		System.out.println("Add/remove items from this order:");
		System.out.println("\t(1) - Add items to order");
		System.out.println("\t(2) - Remove items to order");
		System.out.println("\t(3) - Quit");
		int user_choice = sc.nextInt();
		boolean success;

		if(user_choice == 1){
			success = order.addItems(MenuMgr);
		}
		else if(user_choice == 2){
			success = order.removeItems(MenuMgr);
		}
		else if(user_choice == 3){
			return true;
		}
		else{
			System.out.println("Invalid choice.");
			return false;
		}
		
		write();
		return success;	
	}

	public void display() {
		for(Order order: OrderList){
			order.printOrder();
		}
	}

	public void write() throws IOException {
		Path path = Paths.get("Orders.txt");
		FileWriter fw = new FileWriter(String.valueOf(path));
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

		for(Order order:OrderList) {
			try {
				fw.write(order.getOrderID() + ",");
				fw.write(order.getTableID() + ",");
				fw.write(order.getStaffID() + ",");
				fw.write(format1.format(order.getDate()) + ",");
				fw.write(String.valueOf(order.getTime()) + ",");
				fw.write(String.valueOf(order.getMembership()) + ",");
				fw.write(String.valueOf((order.getPaid())) + ",");
				for (int j=0; j<order.getItemList().size(); j++) {
					fw.write(order.getItemList().get(j) + ",");
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

	public void sort() {
		return;
	}

	public Order get(int id) {
		return OrderList.get(id-1);
	}

	public void printInvoice(int id) {
		Order order = get(id);
		order.setPaid(true);
		TableList.get(id).setOccupied(false);
		System.out.println(new String(new char[41]).replace("\0", " ") + "Cafe" + new String(new char[36]).replace("\0", " "));
		System.out.println(new String(new char[26]).replace("\0", " ") + new String(new char[36]).replace("\0", "*"));
		System.out.println(new String(new char[40]).replace("\0", " ") + "Address" + new String(new char[36]).replace("\0", ""));
		System.out.println(new String(new char[26]).replace("\0", " ") + new String(new char[36]).replace("\0", "-"));
		System.out.println(new String(new char[37]).replace("\0", " ") + "Order Invoice");
		System.out.println(new String(new char[26]).replace("\0", " ") + new String(new char[36]).replace("\0", "-"));

		System.out.printf("%n%-47s", "Order ID: " + order.getOrderID());
		System.out.printf("%-20s", "Staff ID: " + order.getStaffID());
		System.out.printf("%20s%n", "Table ID: " + order.getTableID());

		System.out.printf("%-47s", "Order Date/Time: "+ order.getDate());
		System.out.println();
		float total_cost = 0;

		for(Integer item : order.getItemList()) {
			System.out.printf("%5s%-5s: ", "", ("(" + item + ")") );
			MenuItem menuitem = MenuMgr.get(item);
			System.out.printf("%-50s", menuitem.getName());
			System.out.printf("%20s\n",  new DecimalFormat("$###,##0.00").format(menuitem.getPrice()));
			total_cost += menuitem.getPrice();
		}

		System.out.println("\n" + new String(new char[87]).replace("\0", "-"));

		System.out.printf("%87s%n", "Subtotal: " +  new DecimalFormat("$###,##0.00").format(total_cost));

		System.out.printf("%n%87s%n", "+10% Service Charge");
		System.out.printf("%87s%n", "+7% Goods & Service Tax");
		total_cost *= 1.17;

		if(order.getMembership()) {
			System.out.printf("%87s%n", "-10% membership discount");
			total_cost *= 0.9;
		}

		System.out.print(new String(new char[87]).replace("\0", "-"));
		System.out.printf("%n%89s%n", "Total Payable: " + new DecimalFormat("$###,##0.00").format(total_cost)+" \n");
		System.out.print(new String(new char[28]).replace("\0", "*"));
		System.out.print(" Thank you for dining with us! ");
		System.out.println(new String(new char[28]).replace("\0", "*"));
	}

	public void printReport(Date startDate, Date endDate, int menuSize) {
		long start_ts = this._convert_times_to_ts(startDate);
		long end_ts = this._convert_times_to_ts(endDate);
		float total_cost_of_day = 0;
		float total_cost = 0;
		DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		System.out.println(new String(new char[87]).replace("\0", "-"));
		System.out.println("|" + new String(new char[18]).replace("\0", "") + "SalesRevenue Report from " + startDate + " to " + endDate + new String(new char[17]).replace("\0", "") + "|");
		System.out.println(new String(new char[87]).replace("\0", "-"));
		for (long i=start_ts; i<=end_ts; i+=(1000*60*60*24)){
			total_cost_of_day = 0;
			Date currentDate = new Date(i);
			String date = df.format(currentDate);
			System.out.printf("| %-83s |\n", date);
			int[] counter = new int[MenuMgr.getMenuSize()];
			
			for (int j=0; j<OrderList.size(); j++){
				if (this._convert_times_to_ts(get(j+1).getDate())==i) {
					Order order = get(j+1);
					for(Integer item : order.getItemList())
					{
						counter[item-1] ++;
						MenuItem menuitem = MenuMgr.get(item);
						total_cost_of_day += menuitem.getPrice();
						total_cost += menuitem.getPrice();
					}
				}
			}

			for(int k=0; k<counter.length; k++){
				if (counter[k] != 0){
					MenuItem menuitem = MenuMgr.get(k+1);
					System.out.printf("| %-81s %s |", menuitem.getName(), counter[k]);
					System.out.println();
				}
				
			}

			System.out.printf("|%86s\n", new DecimalFormat("$###,##0.00").format(total_cost_of_day) +" |");
			System.out.println(new String(new char[87]).replace("\0", "-"));
		}

		System.out.print("| Total Revenue from " + df.format(startDate) + " to " + df.format(endDate));
		System.out.printf("%42s\n",new DecimalFormat("$###,##0.00").format(total_cost) + " |");
		System.out.println(new String(new char[87]).replace("\0", "-"));
	}
	
	private long _convert_times_to_ts(Date parsedDate) {
		return parsedDate.getTime();
	}

	private Date _convert_string_to_date(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//("yyyy-MM-dd hh:mm:ss.SSS");
		return dateFormat.parse(date);
	}

	public void displayInterface() throws ParseException {
		int user_choice = 0;
		int order_no;

		try{
			readtable();
			
		}catch(IOException e){
			
			System.out.println("Unable to read table.");
		}
		
		if(!read()){
			System.out.println("Corrupted file.");
		}

		while (true)
		{
			System.out.println("Welcome to the order interface, please select an option:");
			System.out.println("\t(1) - CREATE a new order");
			System.out.println("\t(2) - Remove an order");
			System.out.println("\t(3) - VIEW all orders");
			System.out.println("\t(4) - EDIT an order");
			System.out.println("\t(5) - Check an order");			
			System.out.println("\t(6) - Print an invoice");
			System.out.println("\t(7) - Print a report");
			System.out.println("\t(8) - Exit");
			user_choice = sc.nextInt();
			
			switch (user_choice)
			{
				case 1:
					// CREATE a new order
					boolean added = false;
					try{
						added = add();
					}catch(IOException e){
						System.out.println("Cannot write to file.");
					}

					if(!added){
						System.out.println("The order was not created. Please refer to error message above.");
					}
					else{
						System.out.println("Order created successfully.");
					}
					break;
					
				case 2:
					System.out.println("Please enter the ID of the order you would like to remove:");
					order_no = sc.nextInt();

					boolean removed = false;
					try{
						removed = remove(order_no);
					}catch(IOException e){
						System.out.println("Cannot write to file");
					}

					if(!removed){
						System.out.println("The order was not removed. Please refer to error message above.");
					}
					else{
						System.out.println("Order removed successfully.");
					}
					break;

				case 3:
					// VIEW all orders
					display();
					break;
					
				case 4:
					// Edit an order
					System.out.println("Please enter the ID of the order you would like to modify:");
					order_no = sc.nextInt();
					if(invalid(order_no)){
						System.out.println("Invalid ID. Please try again.");
						break;
					}

					boolean edited = false;
					try{
						edited = edit(order_no);
					}catch(IOException e){
						System.out.println("Cannot write to file.");
					}

					if(edited){
						System.out.println("The operation was successful.");
					}else{
						System.out.println("The operation was unsuccessful, please refer to the error above.");
					}

					break;
					
				case 5:
					System.out.println("Please enter the ID of the order you would like to check:");
					order_no = sc.nextInt();
					if(invalid(order_no)){
						System.out.println("Invalid ID, please try again later.");
						break;
					}
					Order order = get(order_no);
					order.printOrder();

					break;

				case 6:
					// Generate the invoice
					System.out.println("Please enter the ID of the order the invoice is for:");
					order_no = sc.nextInt();
					if(invalid(order_no)){
						System.out.println("Invalid ID, please try again later.");
						break;
					}
					printInvoice(order_no);
					break;
					
				case 7:
					sc.nextLine();
					System.out.println("Please enter the starting date (yyyy-MM-dd) for this report: ");
					String start = sc.nextLine(); //yyyy-MM-dd
					Date start_date = _convert_string_to_date(start);
					System.out.println("Please enter the ending date (yyyy-MM-dd) for this report: ");
					String end = sc.nextLine(); //yyyy-MM-dd
					Date end_date = _convert_string_to_date(end);
					printReport(start_date, end_date, MenuMgr.getMenuSize());
					break;

				case 8:
					try{
						writetable();
					}catch(IOException e){
						System.out.println("Unable to write to table");
					}
					return;

				default:
					System.out.println("Invalid input, please select one of the options above.");
					break;
			}
		}
	}

	public boolean invalid(int order_id){
		if (order_id < 1 || order_id > OrderList.size()){
			return true;
		}
		return false;
	}
}
