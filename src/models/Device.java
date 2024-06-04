package models;

import java.util.HashMap;


import main.SharedData;

public class Device extends BaseTable {
	public final static String TABLE_NAME = "device";
	private Integer no;
	private String name;
	private Double price;
	private Double weight;
	private String made;

	public Device() {
		no = null;
		price = null;
		weight = null;
	}

	public Device(int no, String name, double price, double weight, String made) {
		super();
		this.no = no;
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.made = made;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
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
		table.put("no", no != null ? String.valueOf(no) : null);
		table.put("name", name);
		table.put("price", price != null ? String.valueOf(price) : null);
		table.put("weight", weight != null ? String.valueOf(weight) : null);
		table.put("made", made);

		return table;
	}

	@Override
	public boolean loadTable(HashMap<String, String> tb) {
		if (tb.get("no") != null) {
			no = Integer.parseInt(tb.get("no"));
		} else {
			no = null;
		}
		name = tb.get("name");
		if (tb.get("price") != null) {
			price = Double.parseDouble(tb.get("price"));
		} else {
			price = null;
		}
		if (tb.get("weight") != null) {
			weight = Double.parseDouble(tb.get("weight"));
		} else {
			weight = null;
		}
		made = tb.get("made");
		return true;
	}

	@Override
	public String toString() {
		return "Device [no=" + no + ", name=" + name + ", price=" + price + ", weight=" + weight + ", made=" + made
				+ "]";
	}

	@Override
	public String getTableName() {
		return Device.TABLE_NAME;
	}
	

	@Override
	public BaseTable[] loadFromRepo() {
		BaseTable[] table = SharedData.getInstance().getRepo().getDevices(this);
		return table;
	}
	
	@Override
	public String[] getPrimaryKey() {
		return new String[] { "no" };
	}

}
