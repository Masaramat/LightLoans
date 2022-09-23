package com.llp.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

public class DbConnection {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/lightmfb_loans";
	
	static final String USER = "root";
	static final String PASSWORD = "SudoP@ssw0rd@1234";
	

	public DbConnection() {
		
	}
	
	public static Connection getDbConnection() {
		Connection conn = null;
		try {
			
			Class.forName(JDBC_DRIVER);
			
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			
		}catch(CommunicationsException ex) {
			ex.printStackTrace();
			}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return conn;
	}
	
	
	
	
	
}
