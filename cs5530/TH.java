package cs5530;

import java.sql.*;
import java.util.ArrayList;

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

	public static Pair<String[], Integer[]> searchTH(int minPrice, int maxPrice, ArrayList<String> addresses, ArrayList<String> keywords, ArrayList<String> categories, int sort, Statement stmt) {
		String sql = "SELECT distinct TH.*, T1.word, T2.scoreAvg, T3.trustScoreAvg FROM TH " +
			"LEFT JOIN (" +
			"SELECT HasKeywords.hid, Keywords.word " +
			"FROM HasKeywords, Keywords " + 
			"WHERE HasKeywords.wid = Keywords.wid " +
			") AS T1 " +
			"ON TH.hid = T1.hid " +
			"LEFT JOIN (" +
			"SELECT hid, AVG(score) as scoreAvg " +
			"FROM Feedback " +
			"GROUP BY hid " +
			") AS T2 " +
			"ON TH.hid = T2.hid " +
			"LEFT JOIN (" +
			"SELECT hid, AVG(score) as trustScoreAvg " +
			"FROM Feedback, Trust " +
			"WHERE Feedback.login = Trust.login2 " +
			"AND Trust.isTrusted = true " +
			"GROUP BY hid " +
			") AS T3 " +
			"ON TH.hid = T3.hid " +
			"WHERE price > " + minPrice;

		if (maxPrice >= 0) {
			sql += " AND price <= " + maxPrice;
		}

		if (addresses != null && !addresses.isEmpty()) {
			sql += " AND (address LIKE '%" + addresses.get(0) + "%'";
			for (int i = 0; i < addresses.size(); i++) {
				sql += " OR address LIKE '%" +  addresses.get(i) + "%'";
			}
			sql += ")";
		}

		if (keywords != null && !keywords.isEmpty()) {
			sql += " AND (address LIKE '%" + keywords.get(0) + "%'";
			for (int i = 0; i < keywords.size(); i++) {
				sql += " OR address LIKE '%" +  keywords.get(i) + "%'";
			}
			sql += ")";
		}

		if (categories != null && !categories.isEmpty()) {
			sql += " AND (address LIKE '%" + categories.get(0) + "%'";
			for (int i = 0; i < categories.size(); i++) {
				sql += " OR address LIKE '%" +  categories.get(i) + "%'";
			}
			sql += ")";
		}

		//price
		if (sort == 1) {
			sql += " ORDER BY price";
		}
		//score
		else if (sort == 2) {
			sql += " ORDER BY scoreAvg DESC";
		}
		//trustedScore
		else if (sort == 3) {
			sql += " ORDER BY trustScoreAvg DESC";
		}

		ResultSet rs = null;	
		ArrayList<String> THs = new ArrayList<String>();
		ArrayList<Integer> hids = new ArrayList<Integer>();

		System.out.println("executing query: " + sql);
		try {
			rs = stmt.executeQuery(sql);			
		
			while (rs.next()) {
				THs.add("Name: " + rs.getString("name") + " | " +
						"Category: " + rs.getString("category") + " | " +
						"Address: " + rs.getString("address") + "\n" +
						"   Price: " + rs.getString("price") + " | " +
						"Year Built: " + rs.getString("yearBuilt") + " | " +
						"Phone: " + rs.getString("phoneNum") + "\n");
				hids.add(rs.getInt("hid"));
			}
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
		String[] result = {};
		Integer[] resultIds = {};
		return new Pair<String[], Integer[]>(THs.toArray(result), hids.toArray(resultIds));

	}
}
