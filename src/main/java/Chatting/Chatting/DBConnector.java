package Chatting.Chatting;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
	static Connection con;

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/chatting", "root", "root");
			return con;
		} catch (Exception e) {
			System.out.println("Exception Occured in Connection cless, Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return con;
	}
}
