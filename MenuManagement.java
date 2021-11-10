import java.util.ArrayList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.text.DecimalFormat;

public class MenuManagement implements OPInterface {
	private Scanner sc = new Scanner(System.in);
	private ArrayList<MenuItem> Menu = new ArrayList<MenuItem>();
	private ArrayList<PromoItem> PromoMenu;
	private boolean return_code;

	public MenuManagement() throws IOException {

		Path path = Paths.get("Menu.txt");
		try (BufferedReader br = Files.newBufferedReader(path)) {
			String line = br.readLine();

			while (line != null) {
				String[] tokens = line.split(",");
				MenuItem.ItemType e = MenuItem.ItemType.valueOf(tokens[3]);
				MenuItem menuItem = new MenuItem(Integer.parseInt(tokens[0]), tokens[1], tokens[2], e, Float.parseFloat(tokens[4]));
				this.Menu.add(menuItem);
				line = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// read the menu items from the txt file and instantiate the objects accordingly
	}

	public boolean add() throws IOException {
		//int index = Menu.size() + 1;
		System.out.println("Name of Menu Item you would like to add: ");
		String name = sc.nextLine();
		System.out.println("Price of Menu Item you would like to add: ");
		float price = sc.nextFloat();
		System.out.println("Type of Menu Item you would like to add: ");
		MenuItem.list_enum_types();
		int enum_int = sc.nextInt();
		System.out.println("Description of Menu Item you would like to add: ");
		String description = sc.nextLine();

		for(int i=0; i<Menu.size(); i++){
			if(this.Menu.get(i).getName().equals(name) && this.Menu.get(i).type ==  MenuItem.ItemType.values()[enum_int]){
				System.out.println("Item is already on the menu.");
				return false;
			}
		}
		// return false if failed and print the reason why

		// add object at end of arraylist
		// instantiate object
		this.Menu.add(new MenuItem(this.Menu.size()+1, name, description, MenuItem.ItemType.values()[enum_int], price));

		// sort the items by their enum so that they are in the correct order when being printed
		this.sort();
		writeToCsv();
		return true;
	}

	/**
	 * 
	 * @param id
	 */
	public boolean remove(int id) throws IOException{
		// remove an order from the current order list
		this.Menu.remove(id);

		// also removes it from file (just override with the current list)
		writeToCsv();
		return true;
	}

	/**
	 * 
	 * @param id
	 */
	public boolean edit(int id) throws IOException {
		boolean changed = false;
		MenuItem menuItem = Menu.get(id);

		System.out.println("Which aspect of the above menu item would you like to edit?");
		System.out.println("\t(1) - Name");
		System.out.println("\t(2) - Price");
		System.out.println("\t(3) - Type");
		System.out.println("\t(4) - Description");
		System.out.println("\t(5) - Nothing (return)");

		int input = sc.nextInt();
		switch(input){
			case 1:
				System.out.println("New name: ");
				String name = sc.nextLine();
				menuItem.setName(name);
				Menu.set(id, menuItem);
				changed = true;
				break;
			case 2:
				System.out.println("New Price: ");
				float price = sc.nextFloat();
				menuItem.setPrice(price);
				Menu.set(id, menuItem);
				changed = true;
				break;
			case 3:
				System.out.println("New Type: ");
				MenuItem.list_enum_types();
				int enum_int = sc.nextInt();
				menuItem.set_menu_type(enum_int);
				Menu.set(id, menuItem);
				changed = true;
				break;
			case 4:
				System.out.println("New Description: ");
				String description = sc.nextLine();
				menuItem.setDescription(description);
				Menu.set(id, menuItem);
				changed = true;
				break;
			case 5:
				System.out.println("Nothing changed.");
				break;
			default:
				System.out.println("Invalid input!");
				break;
		}
		writeToCsv();
		return changed;
	}

	public void display() {
		// TODO - implement MenuManagement.display
		System.out.println("\n" + new String(new char[87]).replace("\0", "-"));
		System.out.println();
		for (MenuItem menuItem: Menu){
			System.out.print("   " + menuItem.getMenuIndex());
			System.out.printf("%-70s", " ~ " + menuItem.getName());
			System.out.printf("%-8s%n",
					new DecimalFormat("$###,##0.00").format(menuItem.getPrice()));
			System.out.print("   " + menuItem.get_menu_type() + "  ");
			System.out.printf("%-60s%n", "\"" + menuItem.getDescription() + "\"");
			System.out.println("\n" + new String(new char[87]).replace("\0", "-"));
			System.out.println();
		}
	}

	public void write() {
		// TODO - implement MenuManagement.write
		throw new UnsupportedOperationException();
	}

	public void sort() {
		// TODO - implement MenuManagement.sort
		int local_id=1;
		for (MenuItem.ItemType temp: MenuItem.ItemType.values())
		{
			for (int i=0; i<this.Menu.size(); i++)
			{
				// first of all, re-index them based on enum, in the second loop, re-arrange them by index (using a basic bubble sort algorithm
				if (this.Menu.get(i) !=null && this.Menu.get(i).get_menu_type() == temp)
				{
					// change the id of this item
					this.Menu.get(i).setMenuIndex(local_id);
					local_id++;
				}
			}
		}
		// now sort the items in the list "menu" by their ids
		for (int i=0; i<this.Menu.size(); i++)
		{
			for (int j=0; j<this.Menu.size()-1; j++)
			{
				if (this.Menu.get(j + 1) ==null) continue;
				if (this.Menu.get(j) ==null || this.Menu.get(j).getMenuIndex() > this.Menu.get(j + 1).getMenuIndex())
				{
					// swap items at index j+1 & j
					MenuItem temp_item = this.Menu.get(j);
					this.Menu.set(j, this.Menu.get(j + 1));
					this.Menu.set(j + 1, temp_item);
				}
			}
		}
	}

	/**
	 *
	 * @param id
	 */
	public MenuItem get(int id) {
		return Menu.get(id);
	}

	public int getMenuSize() {
		return this.Menu.size();
	}

	private void writeToCsv() throws IOException {
		Path path = Paths.get("Current_Orders.csv");
		FileWriter fw = new FileWriter(String.valueOf(path));

		for(MenuItem menuItem: this.Menu) {
			try {
				fw.write(menuItem.getMenuIndex() + ",");
				fw.write(menuItem.getName() + ",");
				fw.write(menuItem.getDescription() + ",");
				fw.write(menuItem.get_menu_type() + ",");
				fw.write(menuItem.getPrice() + "");
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

}
