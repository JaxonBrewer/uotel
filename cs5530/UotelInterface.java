package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;
import java.util.ArrayList;

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
		"*View Cart",
		"Create New Listing",
		"*Record Stay",
		"Trust User",
		"*Cacluate Degree of Separation",
		"*Statitics",
		"*Awards"
	};

	private static String[] browseMenuItems = {
		"*Reserve",
		"*Give Feedback",
		"*View Feedback",
		"*Favorite"
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
	public static int displayMenu(String[] items, String message) {
		System.out.println("\n\n\n--------------- Uotel ---------------\n");

		System.out.println(message + "\n");

		for (int i = 0; i < items.length; i++) {
			System.out.println("" + (i+1) + ": " + items[i]);
		}

		System.out.print("\nEnter choice (blank to cancel): ");
		
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

	public static String[] getInput(String[] items) {
		String[] inputs = new  String[items.length];

		for (int i = 0; i < items.length; i++) {
			System.out.print(items[i] + ": ");
			try {
				inputs[i] = in.readLine();
			} catch(Exception e) {
				System.out.println("Invalid input, exitting");
				closeInterface();
			}
		}

		return inputs;
	}

	public static void browseTH() {
		String[] searchItems = {"Change price range", "Add address", "Add category", "Add keyword", "Change Sort", "Search"};
		String[] pricePrompts = {"Min", "Max"};
		String[] addressPrompts = {"Address"};
		String[] keywordPrompts = {"Keyword"};
		String[] categoryPrompts = {"Category"};
		String[] sortPrompts = {"Sort by \n1) price, \n2) Feedback score, \n3) Trusted Feedback Score \nEnter Choice"};
	   
		int minPrice = 0;
		int maxPrice = -1;
		ArrayList<String> addresses = new ArrayList<String>();
		ArrayList<String> categories = new ArrayList<String>();
		ArrayList<String> keywords = new ArrayList<String>();
		int sort = 0;

	
		int	selection = -1; 
		boolean search = false;

		while (!search) {
			String infoStr = "Search on: ";
			if (minPrice > 0) {
				infoStr += "\nMin Price "	+ minPrice;
			}
			if (maxPrice >= 0) {
				infoStr += "\nMax Price "	+ maxPrice;
			}
			if (!addresses.isEmpty()) {
				for (String address : addresses) {
					infoStr += 	"\nAddress: " + address;
				}
			}
			if (!categories.isEmpty()) {
				for (String category : categories) {
					infoStr += 	"\nCategory: " + category;
				}
			}
			if (!keywords.isEmpty()) {
				for (String keyword : keywords) {
					infoStr += 	"\nKeyword: " + keyword;
				}
			}
			selection = displayMenu(searchItems, infoStr);
			// Price
			if (selection == 1) {
				String[] inputs = getInput(pricePrompts);
				if (inputs[0].isEmpty()) {
					minPrice = 0;	
				} else {
					minPrice = Integer.parseInt(inputs[0]);
				}
				if (inputs[1].isEmpty()) {
					maxPrice = -1;
				} else {
					maxPrice  = Integer.parseInt(inputs[1]);
				}				
			}
			// address
			else if (selection == 2) {
				String[] inputs  = getInput(addressPrompts);
				addresses.add(inputs[0]);
			}
			// category
			else if (selection == 3) {
				String[] inputs  = getInput(keywordPrompts);
				categories.add(inputs[0]);
			}
			// keyword
			else if (selection == 4) {
				String[] inputs  = getInput(categoryPrompts);
				keywords.add(inputs[0]);
			}
			// sort 
			else if (selection == 5) {
				String [] inputs = getInput(sortPrompts);
				sort = Integer.parseInt(inputs[0]);
			}
			// search 
			else if (selection == 6) {
				Pair<String[], Integer[]> results = TH.searchTH(minPrice, maxPrice, addresses, keywords, categories, sort, con.stmt);
				while (selection > 0) {
					selection = displayMenu(results.x, "Search Results");

					if (selection > 0) {
						displayMenu(browseMenuItems, "You selected TH with hid " + results.y[selection-1]);
					}
				}
			}
			// exit
			else if (selection < 0) {
				return;
			}
		}		
	}

	public static void viewCart() {
		System.out.print("Not Implemented");
	}

	public static void createTH(String login) {
		String[] prompts = {"Listing Name", "Category", "Address", "Price", "Year Built", "Phone"};
		String[] inputs = getInput(prompts);


		int hid = TH.createTH(login, inputs[1], inputs[0], inputs[2], "", inputs[3], inputs[4], inputs[5], con.stmt);
		if (hid >= 0) {
			updateTH(hid, login);
		} else {
			System.out.println("Failed to add Listing");
		}
	
	}

	public static void recordStay() {
		System.out.print("Not Implemented");
	}

	public static void trustUser(String login) {
		String[] prompts = {"a) trust, b) not trust", "for Username"};
		String[] inputs = getInput(prompts);

		boolean trust = false;
		trust = (inputs[0].equals("a"));
		
		if (Users.setTrust(login, inputs[1], trust, con.stmt)) {
			System.out.println("Success!");
		} else {
			System.out.println("Fail");
		}
	}

	public static void calcDegreeSeparation() {
		System.out.print("Not Implemented");
	}

	public static void statistics() {
		System.out.print("Not Implemented");
	}

	public static void award() {
		System.out.print("Not Implemented");
	}

	public static void updateTH(int hid, String login) {
		String[] menuItems = {"Change name"};
		System.out.print("Not Implemented");
	}

	public static void closeInterface() {
		if (con != null) {
			try {
				con.closeConnection();
				System.out.println ("Database connection terminated");
			}

			catch (Exception e) { /* ignore close errors */ }
		}
		System.exit(0);
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
					String[] prompts = {"Username", "Password"};
					String[] inputs = getInput(prompts);

					loggedIn = Users.loginUser(inputs[0], inputs[1], con.stmt);
					if (loggedIn) {
						System.out.println("Login Successful");
						login = inputs[0];
					}
					else {
						System.out.println("Login Failed.");
					}
				}

				// register
				else if (c == 2) {
					String[] prompts = {"Username", "Password", "Name", "Address", "Phone"};
					String[] inputs = getInput(prompts);

					loggedIn = Users.registerUser(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], con.stmt);	
					if (loggedIn) {
						login = inputs[0];
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
				c = displayMenu(mainMenuItems, "Hello " + login + "\n* = unimplemented");
				
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
					createTH(login);	
				}

				// Record Stay
				else if (c == 4) {
					recordStay();	
				}
				
				// Trust
				else if (c == 5) {
					trustUser(login);	
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
 
