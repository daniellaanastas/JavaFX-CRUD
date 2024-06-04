package models;

import java.util.HashMap;

import main.SharedData;

public class Address extends BaseTable {
	public final static String TABLE_NAME = "address";
	private Integer id;
	private Integer building;
	private String street;
	private String city;
	private String country;

	public Address() {
		id = null;
		building = null;
		System.out.println(id);
	}

	public Address(int id, int building, String street, String city, String country) {
		super();
		this.id = id;
		this.building = building;
		this.street = street;
		this.city = city;
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBuilding() {
		return building;
	}

	public void setBuilding(int building) {
		this.building = building;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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
	public String toString() {
		return "Address [id=" + id + ", building=" + building + ", street=" + street + ", city=" + city
				+ ", country=" + country + "]";
	}

	@Override
	public HashMap<String, String> getTable() {
		HashMap<String, String> table = new HashMap<>();
		table.put("id", id != null ? String.valueOf(id) : null);
		table.put("buidling", building != null ? String.valueOf(building) : null);
		table.put("street", street);
		table.put("city", city);
		table.put("country", country);

		return table;
	}

	@Override
	public boolean loadTable(HashMap<String, String> tb) {
		if (tb.get("id") != null) {
			id = Integer.parseInt(tb.get("id"));
		} else {
			id = null;
		}

		if (tb.get("buidling") != null) {
			building = Integer.parseInt(tb.get("buidling"));
		} else {
			building = null;
		}
		street = tb.get("street");
		city = tb.get("city");
		country = tb.get("country");
		return true;
	}

	@Override
	public String getTableName() {
		return Address.TABLE_NAME;
	}

	@Override
	public BaseTable[] loadFromRepo() {
		BaseTable[] table = SharedData.getInstance().getRepo().getAddresses(this);
		return table;
	}

	@Override
	public String[] getPrimaryKey() {
		return new String[] { "id" };
	}

}
