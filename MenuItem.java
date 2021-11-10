public class MenuItem {

	private float price;
	private String description;
	private String name;
	private int menuIndex;

	public float getPrice() {
		return this.price;
	}

	/**
	 * 
	 * @param price
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
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

	public int getMenuIndex() {
		return this.menuIndex;
	}

	/**
	 * 
	 * @param menuIndex
	 */
	public void setMenuIndex(int menuIndex) {
		this.menuIndex = menuIndex;
	}

}