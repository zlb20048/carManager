package com.cars.simple.util;

/**
 * 
 * @author shifeng
 *
 */
public final class Sqlite3TableColumn {

	private String columnName = null;
	
	private Sqlite3TableColumnType columnType = null;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Sqlite3TableColumnType getColumnType() {
		return columnType;
	}

	public void setColumnType(Sqlite3TableColumnType columnType) {
		this.columnType = columnType;
	}
}
