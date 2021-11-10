public class Order {

	private int orderID;
	private int staffID;
	private int tableID;
	private ArrayList<Integer> itemList;
	private int dateTime;
	private boolean membership;
	private boolean paid;

	public int getOrderID() {
		return this.orderID;
	}

	/**
	 * 
	 * @param orderID
	 */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getStaffID() {
		return this.staffID;
	}

	/**
	 * 
	 * @param staffID
	 */
	public void setStaffID(int staffID) {
		this.staffID = staffID;
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

	public ArrayList<Integer> getItemList() {

	}

	/**
	 * 
	 * @param id
	 */
	public boolean addItem(int id) {
		// TODO - implement Order.addItem
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 */
	public boolean removeItem(int id) {
		// TODO - implement Order.removeItem
		throw new UnsupportedOperationException();
	}

}