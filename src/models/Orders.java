package models;

import java.util.HashMap;


import main.SharedData;

public class Orders extends BaseTable {
	public final static String TABLE_NAME = "orders";
	private Integer id;
	private Integer date;
	private Integer customer;
	private String car;

	public Orders() {
		id = null;
		date = null;
		customer = null;
	}

	public Orders(int id, int date, int customer, String car) {
		super();
		this.id = id;
		this.date = date;
		this.customer = customer;
		this.car = car;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getCustomer() {
		return customer;
	}

	public void setCustomer(int customer) {
		this.customer = customer;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	@Override
	public HashMap<String, String> getTable() {
		HashMap<String, String> table = new HashMap<>();
		table.put("id", id != null ? String.valueOf(id) : null);
		table.put("date", date != null ? String.valueOf(date) : null);
		table.put("customer", customer != null ? String.valueOf(customer) : null);
		table.put("car", car);

		return table;
	}

	@Override
	public boolean loadTable(HashMap<String, String> tb) {
		if (tb.get("id") != null) {
			id = Integer.parseInt(tb.get("id"));
		} else {
			id = null;
		}
		if (tb.get("date") != null) {
			date = Integer.parseInt(tb.get("date"));
		} else {
			date = null;
		}
		if (tb.get("customer") != null) {
			customer = Integer.parseInt(tb.get("customer"));
		} else {
			customer = null;
		}
		car = tb.get("car");
		return true;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", date=" + date + ", customer=" + customer + ", car=" + car + "]";
	}

	@Override
	public String getTableName() {
		return Orders.TABLE_NAME;
	}

	@Override
	public BaseTable[] loadFromRepo() {
		BaseTable[] table = SharedData.getInstance().getRepo().getOrders(this);
		return table;
	}
	
	@Override
	public String[] getPrimaryKey() {
		return new String[] { "id" };
	}

}
