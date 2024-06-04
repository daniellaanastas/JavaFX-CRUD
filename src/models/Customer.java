package models;

import java.util.HashMap;



import main.SharedData;

public class Customer extends BaseTable {
	public final static String TABLE_NAME = "customer";
	private Integer id;
	private String fName;
	private String lName;
	private Integer address;
	private String job;

	public Customer() {
		id = null;
		address = null;
	}

	public Customer(int id, String fName, String lName, int address, String job) {
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.address = address;
		this.job = job;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Override
	public HashMap<String, String> getTable() {
		HashMap<String, String> table = new HashMap<>();
		table.put("id", id != null ? String.valueOf(id) : null);
		table.put("f_name", fName);
		table.put("l_name", lName);
		table.put("address", address != null ? String.valueOf(address) : null);
		table.put("job", job);

		return table;
	}

	@Override
	public boolean loadTable(HashMap<String, String> tb) {
		if (tb.get("id") != null) {
			id = Integer.parseInt(tb.get("id"));
		} else {
			id = null;
		}

		fName = tb.get("f_name");
		lName = tb.get("l_name");
		if (tb.get("address") != null) {
			address = Integer.parseInt(tb.get("address"));
		} else {
			address = null;
		}
		job = tb.get("job");
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", fName=" + fName + ", lName=" + lName + ", address=" + address + ", job="
				+ job + "]";
	}

	@Override
	public String getTableName() {
		return Customer.TABLE_NAME;
	}
	

	@Override
	public BaseTable[] loadFromRepo() {
		BaseTable[] table = SharedData.getInstance().getRepo().getCustomers(this);
		return table;
	}
	
	@Override
	public String[] getPrimaryKey() {
		return new String[] { "id" };
	}

}
