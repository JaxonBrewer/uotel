package cs5530;

import java.sql.*;

public class TH {
	public TH() {}

	public static int createTH(String login, String category, String name, String address, String url, String price, String yearBuilt, String phone, Statement stmt) {
		String sql = "insert into TH (login, category, name, address, url, price, yearBuilt, phoneNum) " + 
			"values ('" + login + 
			"','" + category + 
			"','" + name + 
			"','" + address + 
			"','" + url + 
			"'," + price + 
			"," + yearBuilt + 
			",'" + phone + "')";
		ResultSet rs = null;	
		int fid = -1;
			

		System.out.println("executing query: " + sql);
		try {
			stmt.executeUpdate(sql);
			
			sql = "select LAST_INSERT_ID() AS ID";
	
			System.out.println("getting fid");
			rs = stmt.executeQuery(sql);
			rs.next();
			fid = rs.getInt("ID");

			
		} catch (Exception e) {
			System.out.println("Cannot execute query:" + e.getMessage());
			
		}
		finally {
			try { 
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (Exception e) {
				System.out.println("Cannot close resultset");	
			}	
		}
		return fid;
	}
}
