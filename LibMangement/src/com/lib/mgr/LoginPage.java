package com.lib.mgr;

import java.util.Scanner;
import java.util.*;

class LoginPage {
	private String loginUserName = "";
	private String loginPassworrd = "";
	private int loginUserId = 0;
	boolean isFirstSession = true;

	public String getUserName() {
		return loginUserName;
	}

	public String getPassword() {
		return loginPassworrd;
	}

	public int getUserId() {
		return loginUserId;
	}

	Scanner myObj = new Scanner(System.in);

	public void startLogin() {
		List<String> list = new ArrayList<String>();

		System.out.println("Login------------SignUp");

		do {
			System.out.println("please enter an option");
			System.out.println("1.login");
			System.out.println("2.signup");
			String inputFromUser = myObj.nextLine();
			String input = inputFromUser.toLowerCase();
			if (input.equals("login") || input.equals("1")) {

				System.out.println("enter username:");
				loginUserName = myObj.nextLine();
				System.out.println("enter password");
				loginPassworrd = myObj.nextLine();
				System.out.println("enter userid(only numbers)");
				loginUserId = Integer.parseInt(myObj.nextLine());
				if (isFirstSession)
					System.out.println("user not found,please sign up");
			} else if (input.equals("signup") || input.equals("2")) {
				isFirstSession = false;
				System.out.println("enter username:");
				loginUserName = myObj.nextLine();
				System.out.println("enter password");
				loginPassworrd = myObj.nextLine();
				System.out.println("enter userid");
				loginUserId = Integer.parseInt(myObj.nextLine());

			} else {
				System.out.println("please enter a valid option");
			}
		} while (isFirstSession);
	}

	public String[] getBookName(Library mainLib, LibraryCardPerUser user) {
		System.out.println("Please select an option");
		System.out.println("1.do you want to issue a book ?");
		System.out.println("2.do you want to read a book?");
		System.out.println("3.do you want to re-issue a book?");

		String userInput = myObj.nextLine();
		int input = Integer.parseInt(userInput);
		String action = "";
		String bookName = "";
		String[] result = new String[3];
		result[2] = "";
		if (input == 1) {

			System.out.println("Please select or search book to be issued from below catalog");
			mainLib.displayIssuedBookList();
			mainLib.displayRefBookList();
			bookName = myObj.nextLine();
			if (!mainLib.isBookAvailableInLibrary(bookName))
				result[2] = "failure";
			else {
				result[2] = "success";
				System.out.println("issue process2 success");
			}

			action = "issue";
			result[0] = action;
			result[1] = bookName;

		} else if (input == 2) {
			action = "read";
			System.out.println("Please select or search book to be issued from below catalog");
			mainLib.displayRefBookList();
			bookName = myObj.nextLine();
			if (!mainLib.isBookAvailableInLibrary(bookName))
				result[2] = "failure";
			else {
				result[2] = "success";
				System.out.println("book issued for particular time slot");
			}
			result[0] = action;
			result[1] = bookName;
		} else if (input == 3) {
			action = "re-issue";
			System.out.println("debug1 = getIssueBookList " + user.getIssueBookList());
			Book tempBook = user.getIssueBookList().get(0);
			System.out.println("this " + tempBook.getBookName() + " will be re-isuued");
			result[0] = action;
			result[1] = tempBook.getBookName();
			result[2] = "success";
		} else
			System.out.println("please enter a valid imput");
		if (result[2].equals("failure"))
			System.out.println("Not a valid selection,please enter a valid imput");
		return result;
	}

	public void logout() {
		System.out.println("user has been logged out");

	}
}