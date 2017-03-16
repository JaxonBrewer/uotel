package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;

public class UotelInterface {

	/**
	 * @param args
	 */
	public static void displayLoginMenu() {
		 System.out.println("        Welcome to the Uotel System     ");
    	 System.out.println("1. Login:");
    	 System.out.println("2. Register:");
    	 System.out.println("3. Exit:");
    	 System.out.println("pleasse enter your choice:");
	}
	
	public static void displayMainMenu(String login) {
		System.out.println("\n\n\n        Logged in as" + login);
		System.out.println("1. Login:");
   	 	System.out.println("2. Register:");
   	 	System.out.println("3. Exit:");
   	 	System.out.println("pleasse enter your choice:");		
	}

	public static String loginUser(String login, String passwd) {
		System.out.println("Username: ");

		System.out.println("Password: ");
		return "";
	}
	
	public static String registerUser(String login, String passwd, String name, String address, String phone) {
		System.out.println("Create Username: ");
		return "";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connector con = null;
		String choice;
        String sql = null;
		String login = "";
        boolean loggedIn = false;
        int c = 0;
    	try
    	{
		 	 con = new Connector();
             System.out.println ("Database connection established");
         
             BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
             
             while (!loggedIn) {
            	 displayLoginMenu();
            	 while ((choice = in.readLine()) == null && choice.length() == 0);
            	 try {
            		 c = Integer.parseInt(choice);
            	 } catch (Exception e) {
            		 continue;
            	 }

				 if (c == 1) {
					login = loginUser();
				 }
				 else if (c == 2) {
					login = registerUser();
				 }
				 loggedIn = (login != "");
             }
             
             while (true) {
            	 displayMainMenu(login);
            	 while ((choice = in.readLine()) == null && choice.length() == 0);
            	 try {
            		 c = Integer.parseInt(choice);
            	 } catch (Exception e) {
            		 continue;
            	 }             
			 }
		 }
         catch (Exception e) {
        	 e.printStackTrace();
        	 System.err.println ("Either connection error or query execution error!");
         }
         finally {
        	 if (con != null) {
        		 try {
        			 con.closeConnection();
        			 System.out.println ("Database connection terminated");
        		 }
        	 
        		 catch (Exception e) { /* ignore close errors */ }
        	 }	 
         }
	}
}
