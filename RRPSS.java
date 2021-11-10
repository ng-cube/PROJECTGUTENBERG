import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.text.ParseException;

public class RRPSS {
	MenuManagement menu = new MenuManagement();
	ArrayList<Table> TableList;
	BookingManagement booking = new BookingManagement(TableList);
	StaffManagement staff = new StaffManagement();
	OrdersManagement order = new OrdersManagement(menu, staff, TableList);

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
