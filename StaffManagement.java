import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class StaffManagement implements OPInterface {
	private Scanner sc = new Scanner(System.in);
	private ArrayList<Staff> StaffList = new ArrayList<>();

	public StaffManagement() throws IOException {

		Path path = Paths.get("Staff.txt");

		try (BufferedReader br = Files.newBufferedReader(path)) {
			String line = br.readLine();

			while (line != null) {
				String[] tokens = line.split(",");
				Staff staff = new Staff(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3]);
				StaffList.add(staff);

				line = br.readLine();
			}
		}
	}

	public boolean add() throws IOException {
		int index = StaffList.size() + 1;
		System.out.println("Name of staff: ");
		String name = sc.nextLine();
		System.out.println("Job title of staff: ");
		String jobTitle = sc.nextLine();
		System.out.println("Gender of staff: ");
		String gender = sc.nextLine();

		Staff staff = new Staff(index, name, jobTitle, gender);
		StaffList.add(staff);

		writeToCsv();

		return true;
	}

	/**
	 * 
	 * @param id
	 */
	public boolean remove(int id) throws IOException {
		StaffList.remove(id);
		writeToCsv();

		return true;
	}

	/**
	 * 
	 * @param id
	 */
	public boolean edit(int id) throws IOException {
		boolean changed = false;
		Staff staff = StaffList.get(id);

		System.out.println("Which aspect of the above staff would you like to edit?");
		System.out.println("\t(1) - Name");
		System.out.println("\t(2) - Job Title");
		System.out.println("\t(3) - Nothing (return)");

		int input = sc.nextInt();
		switch(input) {
			case 1:
				System.out.println("New name: ");
				String name = sc.nextLine();
				staff.setName(name);
				StaffList.set(id, staff);
				changed = true;
				break;

			case 2:
				System.out.println("New job title: ");
				String jobTitle = sc.nextLine();
				staff.setJobTitle(jobTitle);
				StaffList.set(id, staff);
				changed = true;
				break;

			case 3:
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
		for(Staff staff: StaffList) {
			System.out.println("Staff ID: " + staff.getStaffID());
			System.out.println("Name: " + staff.getName());
			System.out.println("Job title: " + staff.getJobTitle());
			System.out.println("Gender: " + staff.getGender());
			System.out.println();
		}
	}

	public void sort() {}

	/**
	 * 
	 * @param id
	 */
	public Staff get(int id) {
		return StaffList.get(id);
	}

	public void displayInterface() {
		System.out.println("\t(1) - ADD a new staff");
		System.out.println("\t(2) - EDIT a current staff");
		System.out.println("\t(3) - DELETE a current staff");
	}

	public void writeToCsv() throws IOException {
		Path path = Paths.get("Staff.txt");
		FileWriter fw = new FileWriter(String.valueOf(path));

		for(Staff staff: StaffList) {
			try {
				fw.write(staff.getStaffID() + ",");
				fw.write(staff.getName() + ",");
				fw.write(staff.getJobTitle() + ",");
				fw.write(staff.getGender() + ",");
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