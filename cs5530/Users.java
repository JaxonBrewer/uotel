package cs5530;

import java.sql.*;

public class Users {
	public Users() {}

	public static boolean loginUser(String login, String passwd, Statement stmt) {
		String sql = "select * from Users " +
			"where login='" + login + "' " +
			"and password='" + passwd + "'";

		boolean userExists = false;
		ResultSet rs = null;

		System.out.println("executing query: " + sql);
		try {
			rs = stmt.executeQuery(sql);
			
			userExists = rs.next();
		}
		catch(Exception e) {
			System.out.println("Cannot execute login query");
		}
		finally {
			try {
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch (Exception e) {
				System.out.println("Cannot close resultset");
			}
		}
		return userExists;
	}

	public static boolean registerUser(String login, String passwd, String name, String address, String phone, Statement stmt) {
		String sql = "insert into Users " + 
			"values ('" + login + "','" + passwd + "','" + name + "','" + address + "','" + phone + "',false)";


		System.out.println("executing query: " + sql);
		try {
			stmt.executeUpdate(sql);
			return true;
		}
		catch (Exception e) {
			System.out.println("Cannot execute register query");
		}
		finally {
		}
		return false;
	}

	public static boolean setTrust(String login1, String login2, boolean isTrusted, Statement stmt) {
	
		String sql = "replace into Trust " + 
			"values ('" + login1 + "','" + login2 + "'," + String.valueOf(isTrusted)  + ")";


		System.out.println("executing query: " + sql);
		try {
			stmt.executeUpdate(sql);
			return true;
		}
		catch (Exception e) {
			System.out.println("Cannot execute register query");
		}
		finally {
		}
		return false;
	}
}
