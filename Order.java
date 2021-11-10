import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Order {

	private int orderID;
	private int staffID;
	private int tableID;
	private ArrayList<Integer> itemList;
	private Date date;
	private int hour;
	private boolean membership;
	private boolean paid;
	private float cost;
	private static Scanner sc = new Scanner(System.in);

	public Order(int order, int staff, int table, Date orderdate, int orderhour, boolean member, boolean payment){
		orderID = order;
		staffID = staff;
		tableID = table;
		date = orderdate;
		hour = orderhour;
		membership = member;
		paid = payment;
	}

	public boolean getMembership(){
		return this.membership;
	}

	public void setMembership(boolean member){
		this.membership = member;
	}

	public void setPaid(boolean pay){
		this.paid = pay;
	}

	public boolean getPaid(){
		return this.paid;
	}

	public Date getDate(){
		return this.date;
	}

	public int getTime(){
		return this.hour;
	}

	public float getPrice(){
		return this.cost;
	}

	public int getOrderID() {
		return this.orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getStaffID() {
		return this.staffID;
	}

	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}

	public int getTableID() {
		return this.tableID;
	}

	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	public ArrayList<Integer> getItemList() {
		return itemList;
	}

	public void printOrder(){
		System.out.print("   Order ID: " + getOrderID());
		System.out.printf("   Staff ID: " + getStaffID());
		System.out.println("   Table ID: " + getTableID());
		for (int j=0; j<itemList.size(); j++) {
			System.out.println("   Order Item ID: " + itemList.get(j));
		}
		System.out.println();
	}

	public boolean addItems(MenuManagement MenuMgr) {
		System.out.println("Please enter the number of items to be added");
		int num_items = sc.nextInt();
		int item_id = -1;

		for(int i=0; i<num_items; i++){
			System.out.println("Please enter the ID of "+i+" item to be added");
			item_id = sc.nextInt();
			if(item_id < 0 || item_id >= MenuMgr.getMenuSize()){
				System.out.println("Please enter a valid item id.");
				i--;
			}
			itemList.add(item_id);
		}
		
		return true;
	}

	public boolean removeItems(MenuManagement MenuMgr) {
		System.out.println("Please enter the number of items to be removed");
		int num_items = sc.nextInt();
		int item_id = -1;

		for(int i=0; i<num_items; i++){
			System.out.println("Please enter the ID of "+i+" item to be removed");
			item_id = sc.nextInt();
			if(itemList.size()<=0){
				System.out.println("There are no items on the order.");
				return false;
			}
			if(item_id < 0 || item_id >= MenuMgr.getMenuSize()){
				System.out.println("Please enter a valid item id.");
				i--;
			}
			itemList.add(item_id);
		}
		return true;
	}
}