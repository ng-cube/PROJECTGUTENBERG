import java.util.ArrayList;
import java.util.Scanner;

public class PromoItem extends MenuItem {
	ArrayList<Integer> itemIDs;
	Scanner sc = new Scanner(System.in);

	public PromoItem(int menuIndex, String name, String description, ItemType e, float price, ArrayList<Integer> itemIDs) {
		super(menuIndex, name, description, e, price);
		this.itemIDs = itemIDs;
	}

	/**
	 * @param num
	 */
	public ArrayList<Integer> additem(int num) {
		System.out.println("Please enter the ids of the items you would like to add:");
		for (int i=0; i<num; i++) {
			int id = sc.nextInt();
			itemIDs.add(id);
		}
		return itemIDs;
	}

	/**
	 * @param num
	 */
	public ArrayList<Integer> removeitem(int num) {
		System.out.println("Please enter the ids of the items you would like to remove:");
		for (int i=0; i<num; i++) {
			int id = sc.nextInt();
			itemIDs.remove(id);
		}
		return itemIDs;
	}

	public void setItemIDs(ArrayList<Integer> itemIDs) {
		this.itemIDs = itemIDs;
	}

	public ArrayList<Integer> getItemIDs() {
		return this.itemIDs;
	}
}