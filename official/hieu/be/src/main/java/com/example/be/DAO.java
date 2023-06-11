package com.example.be;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	protected static Connection con = null;
	private String dbUrl = "jdbc:mysql://localhost:3306/book";
	private String dbUsername = "root";
	private String dbPassword = "20012002";
	
	public DAO() {
		con = getConnection();
	}
	
	private Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
}
