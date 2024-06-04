package models;

import java.util.HashMap;



import main.SharedData;

public class Car extends BaseTable {
	public final static String TABLE_NAME = "car";
	private String name; // primary key
	private String model;
	private Integer year;
	private String made;

	public Car() {
		year = null;
	}

	public Car(String name, String model, int year, String made) {
		super();
		this.name = name;
		this.model = model;
		this.year = year;
		this.made = made;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMade() {
		return made;
	}

	public void setMade(String made) {
		this.made = made;
	}

	@Override
	public HashMap<String, String> getTable() {
		HashMap<String, String> table = new HashMap<>();
		table.put("name", name);
		table.put("model", model);
		table.put("year", year != null ? String.valueOf(year) : null);
		table.put("made", made);

		return table;
	}

	@Override
	public boolean loadTable(HashMap<String, String> tb) {
		name = tb.get("name");
		model = tb.get("model");
		if (tb.get("year") != null) {
			year = Integer.parseInt(tb.get("year"));
		} else {
			year = null;
		}
		made = tb.get("made");
		return true;
	}

	@Override
	public String toString() {
		return "Car [name=" + name + ", model=" + model + ", year=" + year + ", made=" + made + "]";
	}

	@Override
	public String getTableName() {
		return Car.TABLE_NAME;
	}
	
	@Override
	public BaseTable[] loadFromRepo() {
		BaseTable[] table = SharedData.getInstance().getRepo().getCars(this);
		return table;
	}
	
	@Override
	public String[] getPrimaryKey() {
		return new String[] { "name" };
	}

}
