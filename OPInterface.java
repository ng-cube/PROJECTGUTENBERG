import java.io.IOException;

public interface OPInterface {

	boolean add();

	/**
	 * 
	 * @param id
	 */
	boolean remove(int id);

	/**
	 * 
	 * @param id
	 */
	boolean edit(int id);

	void display();

	void write() throws IOException;

	void sort();

}