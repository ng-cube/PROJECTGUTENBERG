import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.text.ParseException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.nio.file.Files;

public class RRPSS {
	static protected MenuManagement menu = new MenuManagement();
	static protected ArrayList<Table> TableList = new ArrayList<Table>();
	static protected BookingManagement booking = new BookingManagement(TableList);
	static protected StaffManagement staff = new StaffManagement();
	static protected OrdersManagement order = new OrdersManagement(menu, staff, TableList);

	public static void main(String[] args) throws ParseException, IOException {

		Scanner sc = new Scanner(System.in);
		int user_choice = 0;

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

		while (true)
		{
			System.out.println("\t(1) - Access the Menu interface");
			System.out.println("\t(2) - Access the Order interface");
			System.out.println("\t(3) - Access the Booking interface");
			System.out.println("\t(4) - Access the Staff interface");
			System.out.println("\t(5) - Exit");
			user_choice = sc.nextInt();
			
			switch (user_choice)
			{
				case 1:
					//Access the Menu interface					
					menu.displayInterface();
					break;
										
				case 2:
					// Access the Order interface
					order.displayInterface();
					break;
					
				case 3:
					// Access the Booking interface
					booking.displayInterface();
					break;
					
				case 4:
					staff.displayInterface();
					break;
					
				case 5:
					System.out.println("Thank you for using the system!");
					sc.close();
					return;
					
				default:
					System.out.println("Invalid input, please enter a valid option.");
					break;
			}
		}
	}
}
