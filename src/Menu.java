public class Menu {
	private String name = "";
	private String mainDish = "";
	private String sideDish = "";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSideDish() {
		return sideDish;
	}
	public void setSideDish(String sideDish) {
		this.sideDish = sideDish;
	}
	

	public String getMainDish() {
		return mainDish;
	}
	public void setMainDish(String mainDish) {
		this.mainDish = mainDish;
	}
	
	public String toString()
	{
		return name+"\n"+mainDish+"\n"+sideDish+"\n";
	}
	
}
