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
    	 System.out.println("1. Login");
    	 System.out.println("2. Register");
    	 System.out.println("3. Exit");
    	 System.out.println("please enter your choice:");
	}
	
	public static void displayMainMenu(String login) {
		System.out.println("\n\n\n        Logged in as " + login);
		System.out.println("1. ");
   	 	System.out.println("2. :");
   	 	System.out.println("3. :");
   	 	System.out.println("please enter your choice:");		
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
				 // login
				 if (c == 1) {
					System.out.print("Username: ");
					String username = in.readLine();
					System.out.print("Password: ");
					String password = in.readLine();

					loggedIn = Users.loginUser(username, password, con.stmt);
					if (loggedIn) {
						System.out.println("Login Successful");
						login = username;
					}
					else {
						System.out.println("Login Failed.");
					}
				 }
				 // register
				 else if (c == 2) {
					System.out.print("Enter Username: ");
					String username = in.readLine();
					System.out.print("Enter Password: ");
					String password = in.readLine();
					System.out.print("Enter Name: ");
					String name = in.readLine();
					System.out.print("Enter Address: ");
					String address = in.readLine();
					System.out.print("Enter Phone: ");
					String phone = in.readLine();
					
					loggedIn = Users.registerUser(username, password, name, address, phone, con.stmt);	
					if (loggedIn) {
						login = username;
					}
					else {
						System.out.println("Registration Failed");
					}
				 }
				 else if (c == 3) {
					 System.exit(0);
				 }
				 
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
