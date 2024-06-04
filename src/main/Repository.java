package main;

import java.sql.Connection;
import javafx.scene.control.Alert;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import models.Address;
import models.BaseTable;
import models.Car;
import models.CarPart;
import models.Customer;
import models.Device;
import models.Manufacture;
import models.Orders;




public class Repository {
	
	
	private final static String DATABASE = "cdb";
	private final static String URL = "jdbc:mysql://localhost:3306/";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "";

	public Repository() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public boolean openConnection() {
		try {
			Connection connection = DriverManager.getConnection(String.format("%s%s", URL, DATABASE), USERNAME,
					PASSWORD);
			SharedData.getInstance().setConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean closeConnection() {
		Connection connection = SharedData.getInstance().getConnection();
		if (connection == null) {
			return true;
		}
		try {
			connection.close();
			SharedData.getInstance().setConnection(null);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private String buildConditions(HashMap<String, String> tableMap) {
		StringBuilder sb = new StringBuilder("");
		boolean condition = false;
		for (String key : tableMap.keySet()) {
			String value = tableMap.get(key);

			if (value != null) {
				if (condition == false) {
					condition = true;
					sb.append(" WHERE ");
				} else {
					sb.append(" AND ");
				}
				sb.append(key + " = '" + value + "'");
			}
		}

		sb.append(";");
		return sb.toString();
	}

	private String buildSepConditions(HashMap<String, String> tableMap, String separator) {
	    StringBuilder sb = new StringBuilder("");
	    boolean condition = false;

	    for (String key : tableMap.keySet()) {
	        String value = tableMap.get(key);

	        if (value != null && !value.isEmpty()) {
	            if (condition) {
	                sb.append(String.format(" %s ", separator));
	            }
	            condition = true;
	            sb.append(key).append(" = '").append(value).append("'");
	        }
	    }

	    return sb.toString();
	}



	
	
	public HashMap<String, String>[] getData(BaseTable where) {
		Connection connection = SharedData.getInstance().getConnection();
		if (connection == null) {
			return null;
		}

		String conditions = buildConditions(
				where != null ? where.getTable() : new HashMap<String, String>());

		Statement stmt;
		
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		String query = String.format("SELECT * FROM %s %s", where.getTableName(), conditions);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		ResultSetMetaData resultMeta;
		try {
			resultMeta = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		ArrayList<HashMap<String, String>> tableArr = new ArrayList<>();

		try {
			while (rs.next()) {
				HashMap<String, String> tableMap = new HashMap<>();
				System.out.println(resultMeta.getColumnCount());
				for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
					System.out.println(i);
					String columnName = resultMeta.getColumnName(i);
					tableMap.put(columnName, rs.getString(i));
				}

				tableArr.add(tableMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		HashMap<String, String>[] tableMap = new HashMap[tableArr.size()];
		return tableArr.toArray(tableMap);
	}

	public Address[] getAddresses(Address where) {
		HashMap<String, String>[] array = getData(where);
		ArrayList<Address> table = new ArrayList<>();
		for (HashMap<String, String> arr : array) {
			Address addObj = new Address();
			addObj.loadTable(arr);
			table.add(addObj);
		}

		Address[] add = new Address[table.size()];
		return table.toArray(add);
	}

	public Car[] getCars(Car where) {
		HashMap<String, String>[] array = getData(where);
		ArrayList<Car> table = new ArrayList<>();
		for (HashMap<String, String> arr : array) {
			Car carObj = new Car();
			carObj.loadTable(arr);
			table.add(carObj);
		}

		Car[] car = new Car[table.size()];
		return table.toArray(car);
	}

	public CarPart[] getCarParts(CarPart where) {
		HashMap<String, String>[] array = getData(where);
		ArrayList<CarPart> table = new ArrayList<>();
		for (HashMap<String, String> arr : array) {
			CarPart cpObj = new CarPart();
			cpObj.loadTable(arr);
			table.add(cpObj);
		}

		CarPart[] carPart = new CarPart[table.size()];
		return table.toArray(carPart);
	}

	public Customer[] getCustomers(Customer where) {
		HashMap<String, String>[] array = getData(where);
		ArrayList<Customer> table = new ArrayList<>();
		for (HashMap<String, String> arr : array) {
			Customer custObj = new Customer();
			custObj.loadTable(arr);
			table.add(custObj);
		}

		Customer[] customer = new Customer[table.size()];
		return table.toArray(customer);
	}

	public Device[] getDevices(Device where) {
		HashMap<String, String>[] array = getData(where);
		ArrayList<Device> table = new ArrayList<>();
		for (HashMap<String, String> arr : array) {
			Device devObj = new Device();
			devObj.loadTable(arr);
			table.add(devObj);
		}

		Device[] device = new Device[table.size()];
		return table.toArray(device);
	}

	public Manufacture[] getManufactures(Manufacture where) {
		HashMap<String, String>[] array = getData(where);
		ArrayList<Manufacture> table = new ArrayList<>();
		for (HashMap<String, String> arr : array) {
			Manufacture manuObj = new Manufacture();
			manuObj.loadTable(arr);
			table.add(manuObj);
		}

		Manufacture[] manu = new Manufacture[table.size()];
		return table.toArray(manu);
	}

	public Orders[] getOrders(Orders where) {
		HashMap<String, String>[] array = getData(where);
		ArrayList<Orders> table = new ArrayList<>();
		for (HashMap<String, String> arr : array) {
			Orders orderObj = new Orders();
			orderObj.loadTable(arr);
			table.add(orderObj);
		}

		Orders[] order = new Orders[table.size()];
		return table.toArray(order);
	}

	public void insertInto(String tableName, BaseTable table) throws PrimaryKeyViolationException {
	    Connection connection = SharedData.getInstance().getConnection();
	    if (connection == null) {
	        return;
	    }

	    StringBuilder column = new StringBuilder();
	    StringBuilder values = new StringBuilder();
	    HashMap<String, String> tableMap = table.getTable();
	    for (String key : tableMap.keySet()) {
	        column.append(key + ",");
	        values.append("'" + tableMap.get(key) + "',");
	    }
	    column.deleteCharAt(column.length() - 1);
	    values.deleteCharAt(values.length() - 1);

	    Statement stmt = null;
	    try {
	        stmt = connection.createStatement();
	        String query = String.format("INSERT INTO %s (%s) VALUES (%s);", table.getTableName(), column.toString(),
	                values.toString());
	        stmt.executeUpdate(query);
	        

	    } catch (SQLException e) {
	        if (e.getSQLState().equals("23000") && e.getErrorCode() == 1062) {
	            System.out.println("Primary key violation: " + e.getMessage());
	            throw new PrimaryKeyViolationException("Primary key violation: The provided key already exists.");
	        } else {
	            e.printStackTrace();
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Insert Fail");
	            alert.setHeaderText(null);
	            alert.setContentText("Error inserting row: " + e.getMessage());
	            alert.showAndWait();
	        }
	    } finally {
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	}



	public void deleteFrom(String tableName, BaseTable table) {
		Connection connection = SharedData.getInstance().getConnection();
		if (connection == null) {
			return;
		}

		String conditions = buildConditions(table.getTable());

		System.out.println("Delete Condition: " + conditions);
		Statement stmt;
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		String query = String.format("DELETE FROM %s %s", table.getTableName(), conditions);
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	
	public void update(String tableName, BaseTable table) throws PrimaryKeyViolationException, SQLException {
	    Connection connection = SharedData.getInstance().getConnection();
	    if (connection == null) {
	        System.err.println("Connection is null.");
	        return;
	    }

	    HashMap<String, String> tableMap = table.getTable();
	    String[] primaryKey = table.getPrimaryKey();
	    HashMap<String, String> originalValues = new HashMap<>();

	    for (String key : primaryKey) {
	        String value = tableMap.get(key);
	        if (value != null) {
	            originalValues.put(key, value); // store the original values for the WHERE clause
	        } else {
	            System.err.println("Primary key value is null or not found: " + key);
	            return;
	        }
	    }

	    HashMap<String, String> tableMapCopy = new HashMap<>(tableMap); 
	    String setConditions = buildSepConditions(tableMapCopy, ","); 

	    String whereConditions = "";

	    if (!originalValues.isEmpty()) {
	        whereConditions = " WHERE " + buildSepConditions(originalValues, " AND ");
	    }

	    Statement stmt = null;
	    try {
	    	
	        stmt = connection.createStatement();
	        String query = String.format("UPDATE %s SET %s%s;", tableName, setConditions, whereConditions);

	        // Debugging information
	        System.out.println("Generated SQL Query: " + query);

	        int updatedRows = stmt.executeUpdate(query);
	        System.out.println("Updated Rows: " + updatedRows);

	        if (updatedRows == 0) {
	            throw new PrimaryKeyViolationException("Primary key violation: No rows updated.");
	        }


	    } catch (SQLException e) {
	    	
	        connection.rollback();
	        e.printStackTrace();
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("Error Code: " + e.getErrorCode());

	        if ("23000".equals(e.getSQLState()) && 1062 == e.getErrorCode()) {
	            throw new PrimaryKeyViolationException("Primary key violation: The provided key already exists. " + e.getMessage());
	        } else {
	            throw new PrimaryKeyViolationException("Error updating row: " + e.getMessage());
	        }
	    } finally {
	        try {
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	}



	}


