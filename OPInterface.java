public interface OPInterface {

	boolean add() throws IOException;

	/**
	 * 
	 * @param id
	 */
	boolean remove(int id) throws IOException;

	/**
	 * 
	 * @param id
	 */
	boolean edit(int id) throws IOException;

	void display();

	void write();

	void sort();

}
