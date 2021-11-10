import java.util.Date;

public class Reservation {

	private int reservationID;
	private int nrPax;
	private int tableID;
	private String name;
	private String contact;
	private Date date;
	private int startTime;

	public Reservation(int reservationID,String name,String contact,int nrPax,int tableID,Date date,int startTime){
		this.reservationID = reservationID;
		this.nrPax = nrPax;
		this.tableID = tableID;
		this.name = name;
		this.contact = contact;
		this.date = date;
		this.startTime = startTime;
	}

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

	public int getStartTime() {
		return this.startTime;
	}

	/**
	 * 
	 * @param startTime
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public Date getDate() {
		return this.date;
	}

	/**
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public void  printReservation(){
		System.out.println(reservationID);
		System.out.println(nrPax);
		System.out.println(tableID);
		System.out.println(name);
		System.out.println(contact);
		System.out.println(date);
		System.out.println(startTime);
	}
}