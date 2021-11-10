public class MenuItem {
	public enum ItemType {
		MAIN,
		DESSERT,
		STARTER,
		DRINK
	}
	private float price;
	private String description;
	private String name;
	private int menuIndex;
	public ItemType type = ItemType.MAIN;

	public MenuItem(int index, String name, String description, ItemType e, float price){
		this.name = name;
		this.price = price;
		this.type = e;
		this.description = description;
		this.menuIndex = index;
	}

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

	public ItemType getType() {
		return this.type;
	}

	public ItemType get_menu_type()
	{
		return this.type;
	}

	public void set_menu_type(int enum_int) {
		this.type = MenuItem.ItemType.values()[enum_int];
	}

	public static void list_enum_types()
	{
		//System.out.println("trying to print enums");
		// actually print the string (1) - ENUM...
		int temp_int=0;
		for (MenuItem.ItemType temp: MenuItem.ItemType.values())
		{
			System.out.printf("\t(%d) - %s \n", temp_int, temp.toString());
			temp_int++;
		}
	}

}
