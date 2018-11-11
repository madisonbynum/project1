package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		
		try {
			String url = "jdbc:postgresql://classdb.crya4suqmbnx.us-east-2.rds.amazonaws.com:5432/projectone";
			String username = "madison";
					//System.getenv("banking_app_login");
			String password = "12795";
					//System.getenv("banking_app_pass");
			return DriverManager.getConnection(url, username , password);
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
}
}
