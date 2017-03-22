package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;

public class UotelInterface {

	private static Connector con = null;
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	// Menu Lists 
	private static String[] loginItems = {
		"Login", 
		"Register"
	};

	private static String[] mainMenuItems = {
		"Browse Listings",
		"View Cart",
		"Create New Listing",
		"Record Stay",
		"Trust User",
		"Cacluate Degree of Separation",
		"Statitics",
		"Awards"
	};

	private static String[] browseMenuItems = {
		"Reserve",
		"Give Feedback",
		"View Feedback",
		"Favorite"
	};

	private static String[] feedbackMenuItems = {
		"Sort",
		"Rate"
	};

	private static String[] statisticsMenuItems = {
	
	};

	private static String[] awardMenuItems ={
	
	};



	/*
	 * Displays a menu with the header printed at top, and a each item in the list
	 * on a new line. Returns the number of the item the user choose, or -1 if choice
	 * was invalid. 
	 */
	public static int displayMenu(String[] items, String header) {
		System.out.println("\n\n\n    " + header);

		for (int i = 0; i < items.length; i++) {
			System.out.println("" + (i+1) + ": " + items[i]);
		}

		System.out.print("Enter choice (blank to cancel): ");
		
		try {
			String input = in.readLine();
			int selection = -1;
			try {
				selection = Integer.parseInt(input);
			} catch (Exception e) { }
			return selection;
		} catch (Exception e) {
			System.out.println("Input Error");
			return -1;
		}
	}     

	public static void browseTH() {
			
	}

	public static void viewCart() {
	
	}

	public static void createTH() {
	
	}

	public static void recordStay() {
	
	}

	public static void trustUser() {
	
	}

	public static void calcDegreeSeparation() {
	
	}

	public static void statistics() {
	
	}

	public static void award() {
	
	}

	public static void closeInterface() {
		if (con != null) {
			try {
				con.closeConnection();
				System.out.println ("Database connection terminated");
			}

			catch (Exception e) { /* ignore close errors */ }
		}
	}
	
	public static void main(String[] args) {	
		String login = "";
        boolean loggedIn = false;
        int c = 0;
    	try
    	{
			con = new Connector();
            System.out.println ("Database connection established"); 

            while (!loggedIn) {
				c = displayMenu(loginItems, "Welcome to Uotel");
				 
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
				else if (c == -1) {
					closeInterface();	
				}

            }
            
            while (true) {
				c = displayMenu(mainMenuItems, "Hello " + login + ", -- Uotel");
				
				// Browse
				if (c == 1) {
					browseTH();	
				}

				// Cart
				else if (c == 2) {
					viewCart();
				}

				// Create
				else if (c == 3) {
					createTH();	
				}

				// Record Stay
				else if (c == 4) {
					recordStay();	
				}
				
				// Trust
				else if (c == 5) {
					trustUser();	
				}

				// Degree of Separation
				else if (c == 6) {
					calcDegreeSeparation();	
				}

				// Statistics
				else if (c == 7) {
					statistics();	
				}
				
				// Award
				else if (c == 8) {
					award();	
				}

				else if (c == -1) {
					closeInterface();
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
