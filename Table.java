

import java.util.ArrayList;

public class Table {

	private int tableID;
	private int capacity;
	private boolean occupied;
	private boolean availability;
	private ArrayList<String> slots;
	
	
	//read from file
	public Table(int tableID,int capacity,boolean occupied,boolean availability,ArrayList<String>slots){
		this.tableID = tableID;
		this.capacity = capacity;
		this.occupied = false;
		this.availability = true;
		this.slots = slots;
	}
	public int getTableID() {
		return this.tableID;
	}

	/**
	 * 
	 * @param tableID
	 */
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * 
	 * @param capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean getOccupied() {
		return this.occupied;
	}

	/**
	 * 
	 * @param occupied
	 */
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public boolean getAvailability() {
		return this.availability;
	}

	/**
	 * 
	 * @param availability
	 */
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	
	public ArrayList<String> getSlots() {
		return this.slots;
	}

	/**
	 * 
	 * @param availability
	 */
	public void setTimeslot(ArrayList<String> slots) {
		this.slots = slots;
	}

}