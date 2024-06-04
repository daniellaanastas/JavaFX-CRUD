package models;

import java.util.HashMap;
import java.util.List;

public abstract class BaseTable {
	public abstract boolean loadTable(HashMap<String, String> table);
	public abstract HashMap<String, String> getTable();
	public abstract String getTableName();
	public abstract BaseTable[] loadFromRepo();
	public abstract String[] getPrimaryKey();
	
	 public boolean isPrimaryKey(String columnName) {
	        String[] primaryKeyColumns = getPrimaryKey();
	        for (String primaryKeyColumn : primaryKeyColumns) {
	            if (primaryKeyColumn.equals(columnName)) {
	                return true;
	            }
	        }
	        return false;
	    }
}
