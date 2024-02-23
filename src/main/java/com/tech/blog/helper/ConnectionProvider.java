package com.tech.blog.helper;
//This is git
import java.sql.*;
import java.sql.DriverManager;

public class ConnectionProvider {
	private static Connection con;

	public static Connection getConnection() {
		try {
			if (con == null) {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TechBlog", "root", "Drc@1234");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
