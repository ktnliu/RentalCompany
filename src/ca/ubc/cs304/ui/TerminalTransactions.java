package ca.ubc.cs304.ui;

import ca.ubc.cs304.Customer;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.BranchModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;
	private static final String EMPTY_STRING = "";

	private BufferedReader bufferedReader = null;
	private TerminalTransactionsDelegate delegate = null;

	public TerminalTransactions() {
	}

	/**
	 * Displays simple text interface
	 */
	public void showMainMenu(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;

		while (choice != 3) {
			System.out.println();
			System.out.println("1. Customer Login");
			System.out.println("2. Clerk Login");
			System.out.println("3. View Database");
			System.out.println("4. Quit");
			System.out.print("Please choose one of the above 4 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
						handleCustomer();
						break;
					case 2:
						handleClerk();
						break;
					case 3:
						handleDataBase();
						break;
					case 4:
						handleQuitOption();
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleDeleteOption() {
		int branchId = INVALID_INPUT;
		while (branchId == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to delete: ");
			branchId = readInteger(false);
			if (branchId != INVALID_INPUT) {
				delegate.deleteBranch(branchId);
			}
		}
	}

	private void handleInsertOption() {
		int id = INVALID_INPUT;
		while (id == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to insert: ");
			id = readInteger(false);
		}

		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the branch name you wish to insert: ");
			name = readLine().trim();
		}

		// branch address is allowed to be null so we don't need to repeatedly ask for the address
		System.out.print("Please enter the branch address you wish to insert: ");
		String address = readLine().trim();
		if (address.length() == 0) {
			address = null;
		}

		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the branch city you wish to insert: ");
			city = readLine().trim();
		}

		int phoneNumber = INVALID_INPUT;
		while (phoneNumber == INVALID_INPUT) {
			System.out.print("Please enter the branch phone number you wish to insert: ");
			phoneNumber = readInteger(true);
		}

		BranchModel model = new BranchModel(address,
				city,
				id,
				name,
				phoneNumber);
		delegate.insertBranch(model);
	}

	private void handleQuitOption() {
		System.out.println("Good Bye!");

		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("IOException!");
			}
		}

		delegate.terminalTransactionsFinished();
	}

	private void handleUpdateOption() {
		int id = INVALID_INPUT;
		while (id == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to update: ");
			id = readInteger(false);
		}

		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the branch name you wish to update: ");
			name = readLine().trim();
		}

		delegate.updateBranch(id, name);
	}

	protected int readInteger(boolean allowEmpty) {
		String line = null;
		int input = INVALID_INPUT;
		try {
			line = bufferedReader.readLine();
			input = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		} catch (NumberFormatException e) {
			if (allowEmpty && line.length() == 0) {
				input = EMPTY_INPUT;
			} else {
				System.out.println(WARNING_TAG + " Your input was not an integer");
			}
		}
		return input;
	}

	private String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
			if (result.length() == 0)
				result = EMPTY_STRING;
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}






	/**
	 *
	 *
	 * Customer Transactions
	 *
	 *
	 * */

	private void handleCustomer(){
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;

		while (choice != 4) {
			System.out.println();
			System.out.println("1. Search for vehicles");
			//System.out.println("2. Make a reservation");
			System.out.println("2. Return to main menu");
			System.out.print("Please choose one of the above 2 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
						handleCustSearch();
						break;
					case 2:
						showMainMenu(delegate);
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleCustSearch(){
		String type = null;
		String loc = null;
		Date fromDate = null;
		Timestamp fromTime = null;
		Date toDate = null;
		Timestamp toTime = null;

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice_type = INVALID_INPUT;


		while (type == null || type.length() <= 0){
			System.out.println("1. SUV");
			System.out.println("2. Economy");
			System.out.println("3. Compact");
			System.out.println("4. Standard");
			System.out.println("5. Full");
			System.out.println("6. Truck");
			System.out.print("Please enter a vehicle type: ");

			choice_type = readInteger(true);

			System.out.println(" ");

			if (choice_type != 7) {
				switch (choice_type) {
					case 1:
						type = "SUV";
						break;
					case 2:
						type = "ECONOMY";
						break;
					case 3:
						type = "COMPACT";
						break;
					case 4:
						type = "STANDARD";
						break;
					case 5:
						type = "FULL";
						break;
					case 6:
						type = "TRUCK";
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}

			// type = readLine().trim();
		}

		while (loc == null || loc.length() <= 0) {

			System.out.print("Please enter a location: ");
			loc = readLine();
		}
		System.out.println(" ");

		while (fromDate == null) {
			System.out.print("Please enter a Start Date: ");
			fromDate = Date.valueOf(readLine());
		}
		System.out.println(" ");

		while (fromTime == null) {
			System.out.print("Please enter a Start time: ");
			fromTime = Timestamp.valueOf(readLine());
		}
		System.out.println(" ");

		while (toDate == null) {
			System.out.print("Please enter an End Date: ");
			toDate = Date.valueOf(readLine());
		}
		System.out.println(" ");

		while (toTime == null) {
			System.out.print("Please enter an End Time: ");
			toTime = Timestamp.valueOf(readLine());
		}
		System.out.println(" ");

		if (delegate.customerVehiclesCount(type, loc) == 0) // result count == 0
			handleCustomer();
		else
			handleViewYN(type, loc, fromDate, fromTime, toDate, toTime);
	}

	private void handleViewYN(String type, String loc, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime) {

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int viewYN = INVALID_INPUT;

		while (viewYN != 3) {
			System.out.println("1. Yes");
			System.out.println("2. No, return to menu");
			System.out.print("View these vehicles? ");

			viewYN = readInteger(true);

			System.out.println(" ");

			if (viewYN != 7) {
				switch (viewYN) {
					case 1:
						handleCustView(type, loc, fromDate, fromTime, toDate, toTime);
						handleResoYN(type, loc, fromDate, fromTime, toDate, toTime);
						break;

					case 2:
						handleCustomer();
						break;

					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleCustView(String type, String loc, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime){
		delegate.customerVehiclesView(type, loc);
	}

	private void handleResoYN(String type, String loc, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime){
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int resoYN = INVALID_INPUT;

		while (resoYN != 3) {
			System.out.println("1. Yes");
			System.out.println("2. No, return to menu");
			System.out.print("Would you like to reserve one of these vehicles? ");

			resoYN = readInteger(false);

			System.out.println(" ");

			if (resoYN != 7) {
				switch (resoYN) {
					case 1:
						System.out.println("Processing...");
						System.out.println(" ");
						// make reservation!
						String d_license = handleLogin().getDLicense();
						handleReso(d_license, type, fromDate, fromTime, toDate, toTime);
						handleResoConf(d_license, type, fromDate, fromTime, toDate, toTime);
						break;

					case 2:
						handleCustomer(); //returns to menu
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}


	private Customer handleLogin(){
		//please enter your phone number
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String phone = null;
		String name = null;
		String address = null;
		String dlicense = null;

		while (phone == null || phone.length() <= 0){
			System.out.println();
			System.out.println("Please enter your phone number:");
			phone = readLine();
		}
		while (name == null || name.length() <= 0){
			System.out.println();
			System.out.println("Please enter your first name:");
			name = readLine().trim();
		}
		while (address == null || address.length() <= 0){
			System.out.println();
			System.out.println("Please enter your address:");
			address = readLine().trim();
		}
		while (dlicense == null || dlicense.length() <= 0){
			System.out.println();
			System.out.println("Please enter your driver's license number:");
			dlicense = readLine().trim();
		}

		return delegate.customerLogin(phone, name, address, dlicense);
	}

	private void handleReso(String dLicense, String typeName, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime){
		delegate.makeNewReservation(dLicense, typeName, fromDate, fromTime, toDate, toTime);
	}

	private void handleResoConf(String dLicense, String type, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime){
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int confYN = INVALID_INPUT;

		while (confYN != 3) {
			System.out.println(" ");
			System.out.println("1. Yes");
			System.out.println("2. No, return to menu");
			System.out.print("Confirm reservation? ");

			confYN = readInteger(true);

			System.out.println(" ");

			if (confYN != 7) {
				switch (confYN) {
					case 1:
						delegate.confirmReso(dLicense, type, fromDate, fromTime, toDate, toTime);
						System.out.println("Your reservation details: ");
						System.out.println("Vehicle Type: " + type);
						System.out.println("Start Date: " + fromTime);
						System.out.println("End Date: " + toTime);
						System.out.println(" ");
						handleReturntoMenu();
						break;

					case 2:
						handleCustomer();
						break;

					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleReturntoMenu(){
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int retMM = INVALID_INPUT;

		while (retMM != 2) {
			System.out.println("1. Yes");
			System.out.println("2. No, logout and exit");
			System.out.print("Return to main menu? ");

			retMM = readInteger(true);

			System.out.println(" ");

			if (retMM != 7) {
				switch (retMM) {
					case 1:
						showMainMenu(delegate);
						break;

					case 2:
						handleQuitOption();
						break;

					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}






	/**
	 *
	 *
	 * Clerk Transactions
	 *
	 *
	 * */

	private void handleClerk(){
		//todo

		/**1. Rent a vehicle
		* 	a. Is there a reservation? Y/N
		 * 		Yes: complete rental transaction, return receipt (rentID)
		 * 		No:	reuse custSearch, View, Reso?, then return receipt (rentID)
		 *
		 * 2. Return a vehicle
		 * 	a. Enter rentID
		 * 	b. Receipt: Display resoConfNum, date of return, calculation of total cost,
		 *
		 * 3. Generate Reports
		 * 	a. All Daily Rentals
		 * 	b. Daily Rentals (Branch)
		 * 		- enter branch name
		 * 	c. All Daily Returns
		 * 	d. Daily Returns (Branch)
		 * 		- enter branch name */

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;

		while (choice != 4) {
			System.out.println();
			System.out.println("1. Rent a vehicle");
			System.out.println("2. Return a vehicle");
			System.out.println("3. Generate Reports");
			System.out.println("4. Quit");
			System.out.print("Please choose one of the above 4 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
						handleClerkRent();
						break;
					case 2:
						handleClerkReturn();
						break;
					case 3:
						handleClerkReports();
						break;
					case 4:
						handleQuitOption();
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleClerkRent(){
		//
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;

		while (choice != 4) {
			System.out.println();
			System.out.println("1. Yes");
			System.out.println("2. No");
			System.out.println("3. Return to menu");
			System.out.print("Does the customer have a reservation? ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
						handleYesResoRent();
						break;
					case 2:
						handleSearchRent();
						break;
					case 3:
						showMainMenu(delegate);
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleYesResoRent(){
		//todo
		Integer confNo = null;
		String loc = null;
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		while (confNo == null){
			System.out.println();
			System.out.println("Please enter your reservation's confirmation number:");
			confNo = readInteger(false);
		}
		while (loc == null){
			System.out.println();
			System.out.println("Please enter your reservation's city:");
			loc = readLine().trim();
		}

		LinkedList info = delegate.getResoInfo(confNo);
		handleResoRent((String) info.get(0), loc, (Date) info.get(1), (Timestamp) info.get(2), (Date) info.get(3), (Timestamp) info.get(4));

	}

	private void handleResoRent(String type, String loc, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime){
		//todo

		// rent
		// get vLicense, odometer, and confNo
		LinkedList<String> list = handleGetOneCar(type, loc, fromTime, toTime);
		//System.out.println("got a car");
		String vLicense = list.get(0);
		int odometer = Integer.parseInt(list.get(1));
		String d_license = handleLogin().getDLicense();
		//System.out.println("logged in");
		int confNo = handleClerkReso(d_license, type, fromDate, fromTime, toDate, toTime);

		LinkedList list2 = handleCreditCard();
		//System.out.println("got credit card info");
		String cardName = (String) list2.get(0);
		Integer cardNo = (Integer) list2.get(1);
		Date expDate = (Date) list2.get(2);
		handleRent(confNo, vLicense, d_license, fromDate, fromTime, toDate, toTime, odometer, cardName, cardNo, expDate);

		handleReceipt(confNo, fromDate, toDate, type, loc);
	}

	private void handleSearchRent(){
		String type = null;
		String loc = null;
		Date fromDate = null;
		Timestamp fromTime = null;
		Date toDate = null;
		Timestamp toTime = null;

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice_type = INVALID_INPUT;


		while (type == null || type.length() <= 0){
			System.out.println("1. SUV");
			System.out.println("2. Economy");
			System.out.println("3. Compact");
			System.out.println("4. Standard");
			System.out.println("5. Full");
			System.out.println("6. Truck");
			System.out.print("Please enter a vehicle type: ");

			choice_type = readInteger(true);

			System.out.println(" ");

			if (choice_type != 7) {
				switch (choice_type) {
					case 1:
						type = "SUV";
						break;
					case 2:
						type = "ECONOMY";
						break;
					case 3:
						type = "COMPACT";
						break;
					case 4:
						type = "STANDARD";
						break;
					case 5:
						type = "FULL";
						break;
					case 6:
						type = "TRUCK";
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}

		while (loc == null || loc.length() <= 0) {

			System.out.print("Please enter a location: ");
			loc = readLine().trim();
		}
		System.out.println(" ");

		while (fromDate == null) {
			System.out.print("Please enter a Start Date: ");
			fromDate = Date.valueOf(readLine().trim());
		}
		System.out.println(" ");

		while (fromTime == null) {
			System.out.print("Please enter a Start time: ");
			fromTime = Timestamp.valueOf(readLine().trim());
		}
		System.out.println(" ");

		while (toDate == null) {
			System.out.print("Please enter an End Date: ");
			toDate = Date.valueOf(readLine().trim());
		}
		System.out.println(" ");

		while (toTime == null) {
			System.out.print("Please enter an End Time: ");
			toTime = Timestamp.valueOf(readLine().trim());
		}
		System.out.println(" ");

		if (delegate.customerVehiclesCount(type, loc) == 0) // result count == 0
			handleClerk();
		else
			handleClerkViewYN(type, loc, fromDate, fromTime, toDate, toTime);
	}

	private void handleClerkViewYN(String type, String loc, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime) {

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int viewYN = INVALID_INPUT;

		while (viewYN != 3) {
			System.out.println("1. Yes");
			System.out.println("2. No, return to menu");
			System.out.print("View these vehicles? ");

			viewYN = readInteger(true);

			System.out.println(" ");

			if (viewYN != 7) {
				switch (viewYN) {
					case 1:
						handleClerkView(type, loc, fromDate, fromTime, toDate, toTime);
						handleRentYN(type, loc, fromDate, fromTime, toDate, toTime);
						break;
					case 2:
						handleClerk();
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleClerkView(String type, String loc, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime){
		delegate.customerVehiclesView(type, loc);
	}

	private void handleRentYN(String type, String loc, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime){
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int rentYN = INVALID_INPUT;

		while (rentYN != 3) {
			System.out.println("1. Yes");
			System.out.println("2. No, return to menu");
			System.out.print("Would you like to rent one of these vehicles? ");

			rentYN = readInteger(false);

			System.out.println(" ");

			if (rentYN != 7) {
				switch (rentYN) {
					case 1:
						System.out.println("Processing...");
						System.out.println(" ");

						// rent
						// get vLicense, odometer, and confNo
						LinkedList<String> list = handleGetOneCar(type, loc, fromTime, toTime);
						//System.out.println("got a car");
						String vLicense = list.get(0);
						int odometer = Integer.parseInt(list.get(1));
						String d_license = handleLogin().getDLicense();
						//System.out.println("logged in");
						int confNo = handleClerkReso(d_license, type, fromDate, fromTime, toDate, toTime);

						LinkedList list2 = handleCreditCard();
						//System.out.println("got credit card info");
						String cardName = (String) list2.get(0);
						Integer cardNo = (Integer) list2.get(1);
						Date expDate = (Date) list2.get(2);
						handleRent(confNo, vLicense, d_license, fromDate, fromTime, toDate, toTime, odometer, cardName, cardNo, expDate);

						handleReceipt(confNo, fromDate, toDate, type, loc);
						break;

					case 2:
						handleClerk(); //returns to menu
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private LinkedList<String> handleGetOneCar(String type, String loc, Timestamp fromTime, Timestamp toTime){
		return delegate.getOneCar(type, loc, fromTime, toTime);
	}

	private int handleClerkReso(String dLicense, String typeName, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime){
		//
		handleReso(dLicense, typeName, fromDate, fromTime, toDate, toTime);
		delegate.confirmReso(dLicense, typeName, fromDate, fromTime, toDate, toTime);
		return delegate.clerkReso();
	}

	private LinkedList handleCreditCard(){
		String cardName = null;
		Integer cardNo = null;
		Date expDate = null;
		LinkedList list = new LinkedList();

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		while (cardName == null || cardName.length() <= 0){
			System.out.println();
			System.out.println("Please enter the name on your credit card:");
			cardName = readLine().trim();
		}
		while (cardNo == null){
			System.out.println();
			System.out.println("Please enter your card number:");
			cardNo = Integer.parseInt(readLine());
		}
		while (expDate == null){
			System.out.println();
			System.out.println("Please enter your card's expiry date:");
			expDate = Date.valueOf(readLine().trim());
		}
		System.out.println();
		list.add(cardName);
		list.add(cardNo);
		list.add(expDate);

		return list;
	}

	private void handleRent(int confNo, String vLicense, String dLicense, Date fromDate, Timestamp fromTime, Date toDate, Timestamp toTime,
							int odometer, String cardName, int cardNo, Date expDate){
		delegate.makeNewRental(confNo, vLicense, dLicense, fromDate, fromTime, toDate, toTime, odometer, cardName, cardNo, expDate);
	}

	private void handleReceipt(int confNo, Date fromDate, Date toDate, String type, String loc){
		System.out.println("Thanks for renting with us!");
		System.out.println("Your rental receipt: ");
		System.out.println("Confirmation number: " + confNo);
		System.out.println("Rental start date: " + fromDate);
		System.out.println("Rental end date: " + toDate);
		System.out.println("Car type: " + type);
		System.out.println("Location: " + loc);
		System.out.println(" ");

		handleReturntoMenu();

	}

	private void handleClerkReturn(){
		//
	}

	private void handleClerkReports(){
		//
	}






	/**
	 *
	 *
	 * Databse
	 *
	 *
	 * */

	private void handleDataBase(){
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;

		while (choice != 5) {
			System.out.println();
			System.out.println("1. View Tables");
			System.out.println("2. Add data");
			System.out.println("3. Delete data");
			System.out.println("4. Update data");
			System.out.println("5. Return to main menu");
			System.out.println("6. Quit");
			System.out.print("Please choose one of the above 6 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
						handleViewTables();
						break;
					case 2:
						//handleAddData();
						break;
					case 3:
						//handleDeleteData();
						break;
					case 4:
						//handleUpdateData();
						break;
					case 5:
						showMainMenu(delegate);
						break;
					case 6:
						handleQuitOption();
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleViewTables(){
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String name = null;

		while (name == null || name.length() <= 0) {
			System.out.print("Please enter a table name or 'ALL': ");
			name = readLine();
		}
		System.out.println(" ");
		delegate.viewTables(name);
	}
}