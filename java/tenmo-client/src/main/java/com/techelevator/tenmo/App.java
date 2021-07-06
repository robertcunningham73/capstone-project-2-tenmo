package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.view.ConsoleService;
import org.apiguardian.api.API;

import java.math.BigDecimal;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "(Not implemented) Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "(Not implemented) View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;

    ////////
	private String AUTH_TOKEN;
	////////

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				System.out.println("Sorry, this feature is not implemented.");
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				System.out.println("Sorry, this feature is not implemented.");
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		try {
			AuthenticatedAccountBalanceService balanceService =
					new AuthenticatedAccountBalanceService(API_BASE_URL);
			balanceService.AUTH_TOKEN = AUTH_TOKEN;
			balanceService.displayBalance(currentUser);
		} catch (Exception e) {
			System.out.println("Something went wrong. Sorry.");
		}


	}

	private void viewTransferHistory() {
		TransferPrintOutService transferPrintOutService = new TransferPrintOutService(API_BASE_URL);
		transferPrintOutService.AUTH_TOKEN = AUTH_TOKEN;
		int userId = currentUser.getUser().getId();

		int status = transferPrintOutService.printTransferPrintOuts(userId);
		if (status == -1) {
			return;
		}

		int transferId = console.getUserInputInteger("Enter transfer ID to view details (0 to cancel.)");
		if (transferId==0) { return; }
		if (transferId < 0) {
			System.out.println("Error in input.");
			return;}

		TransferDetailsService detailsService =
				new TransferDetailsService(API_BASE_URL);

		detailsService.AUTH_TOKEN=AUTH_TOKEN;

		try {
			detailsService.printTransferDetails(transferId);
		} catch (Exception ex)
		{
			System.out.println("Sorry, something bad happened.");
			return;
		}

	}

	/*private void viewPendingRequests() {
		// Optional
		// TODO Auto-generated method stub
		
	}*/

	private void sendBucks() {
		// Use case 4
		// First display named user ids to allow picking of id to send to
		// Then build a transfer send and send it to the server
		int numberUsers = -1;
		NamedUserIdService nameService = new NamedUserIdService(API_BASE_URL);
		nameService.AUTH_TOKEN = AUTH_TOKEN;
		numberUsers = nameService.printUserIds();
		if (numberUsers == -1 || numberUsers == 0) {
			System.out.println("No other users.");
			return;
		}

		System.out.println(System.lineSeparator());

		int toUser = console.getUserInputInteger("Enter ID of user you are sending to. (0 to cancel.)");

		if (toUser==0) { return; }

		String transferAmountString = console.getUserInput("Enter amount");
		BigDecimal transferAmount = null;
		try {
			transferAmount = new BigDecimal(transferAmountString);
		} catch (Exception ex) {
			System.out.println("Bad input.");
			return;
		}

		int fromUser = currentUser.getUser().getId();

		TransferSendService transferService = new TransferSendService(API_BASE_URL);
		transferService.AUTH_TOKEN = AUTH_TOKEN;
		TransferSend successfulTransfer = new TransferSend();

		try {
			successfulTransfer = transferService.addTransfer(fromUser, toUser, transferAmount);
		} catch (Exception ex) {
			System.out.println("Something went wrong.");
			return;
		}
		if ((successfulTransfer == null) ||(successfulTransfer.getTransferId() == -1)) {
			System.out.println("Something went wrong.");
			return;
		}
		System.out.println("Congratulations! You have just sent transfer: " + successfulTransfer.getTransferId());
	}


	/*private void requestBucks() {
		// Optional
		// TODO Auto-generated method stub
		
	}*/
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
				AUTH_TOKEN = currentUser.getToken();
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
