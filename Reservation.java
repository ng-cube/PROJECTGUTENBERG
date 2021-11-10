public class Reservation {

	private int reservationID;
	private int nrPax;
	private int tableID;
	private String name;
	private String contact;
	private date startTime;

	public int getReservationID() {
		return this.reservationID;
	}

	/**
	 * 
	 * @param reservationID
	 */
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}

	public int getNrPax() {
		return this.nrPax;
	}

	/**
	 * 
	 * @param nrPax
	 */
	public void setNrPax(int nrPax) {
		this.nrPax = nrPax;
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

	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return this.contact;
	}

	/**
	 * 
	 * @param contact
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	public date getStartTime() {
		return this.startTime;
	}

	/**
	 * 
	 * @param startTime
	 */
	public void setStartTime(date startTime) {
		this.startTime = startTime;
	}

}