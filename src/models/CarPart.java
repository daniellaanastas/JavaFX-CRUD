package models;

import java.util.HashMap;



import main.SharedData;

public class CarPart extends BaseTable {
	public final static String TABLE_NAME = "car_part";
	private String car;
	private Integer part; 

	public CarPart() {
		part = null;
	}

	public CarPart(String car, int part) {
		super();
		this.car = car;
		this.part = part;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}



	@Override
	public HashMap<String, String> getTable() {
		HashMap<String, String> table = new HashMap<>();
		table.put("car", car);
		table.put("part", part != null ? String.valueOf(part) : null);

		return table;
	}

	@Override
	public boolean loadTable(HashMap<String, String> tb) {
		car = tb.get("car");
		if (tb.get("part") != null) {
			part = Integer.parseInt(tb.get("part"));
		} else {
			part = null;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Car_Part [car=" + car + ", part=" + part + "]";
	}

	@Override
	public String getTableName() {
		return CarPart.TABLE_NAME;
	}
	

	@Override
	public BaseTable[] loadFromRepo() {
		BaseTable[] table = SharedData.getInstance().getRepo().getCarParts(this);
		return table;
	}
	
	@Override
	public String[] getPrimaryKey() {
		return new String[] { "car","part"};
	}
	

}
