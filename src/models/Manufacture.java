package models;

import java.util.HashMap;


import main.SharedData;

public class Manufacture extends BaseTable {
	public final static String TABLE_NAME = "manufacture";
	private String name; // primary key
	private String type;
	private String city;
	private String country;

	public Manufacture() {
	}

	public Manufacture(String name, String type, String city, String country) {
		super();
		this.name = name;
		this.type = type;
		this.city = city;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public HashMap<String, String> getTable() {
		HashMap<String, String> table = new HashMap<>();
		table.put("name", name);
		table.put("type", type);
		table.put("city", city);
		table.put("country", country);

		return table;
	}

	@Override
	public boolean loadTable(HashMap<String, String> tb) {
		name = tb.get("name");
		type = tb.get("type");
		city = tb.get("city");
		country = tb.get("country");
		return true;
	}

	@Override
	public String toString() {
		return "Manufacture [name=" + name + ", type=" + type + ", city=" + city + ", country=" + country + "]";
	}

	@Override
	public String getTableName() {
		return Manufacture.TABLE_NAME;
	}

	@Override
	public BaseTable[] loadFromRepo() {
		BaseTable[] table = SharedData.getInstance().getRepo().getManufactures(this);
		return table;
	}
	
	@Override
	public String[] getPrimaryKey() {
		return new String[] { "name" };
	}

}
