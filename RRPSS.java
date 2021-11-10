import java.util.ArrayList;
import java.util.Scanner;

public class RRPSS {

	static protected MenuManagement menu = new MenuManagement();
	static protected BookingManagement booking = new BookingManagement();
	static protected StaffManagement staff = new StaffManagement();
	static protected ArrayList<Table> TableList;
	static protected OrdersManagement order = new OrdersManagement(menu, staff, TableList);
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws ParseException, IOException {
		Scanner sc = new Scanner(System.in);
		int user_choice = 0;

		while (true)
		{
			System.out.printf("%nWelcome to the RRPSS main interface, it is currently %s; please select an option:%n", RRPSS._ts_to_date_string(RRPSS.current_timestamp));
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
					return;
					
				default:
					System.out.println("Invalid input, please enter a valid option.");
					break;
			}
		}

		System.out.println("Thank you for using the system!");
	}

}
