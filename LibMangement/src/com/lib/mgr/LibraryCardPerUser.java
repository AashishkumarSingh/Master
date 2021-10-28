package com.lib.mgr;

import java.util.ArrayList;

public class LibraryCardPerUser {
	private String userName = "";
	private int userId = 0;
	public ArrayList issuedBooks = new ArrayList<Book>();
	public ArrayList requestedBooks = new ArrayList<Book>();// queed book list for this user
	private String password = "";

	public LibraryCardPerUser(String userNameFromLogin, int userIdFromLogin, String passwordFromLogin) {
		userName = userNameFromLogin;
		userId = userIdFromLogin;
		password = passwordFromLogin;
	}

	public void addBookToIssueList(Book book) {
		issuedBooks.add(book);
		System.out.println("addBookToIssueList issuedBooks = "+issuedBooks);
	}
	public void removeBookFromIssueList(Book book) {
		issuedBooks.remove(book);
	}


	public void addBookToRequestedList(Book book) {
		requestedBooks.add(book);
	}

	String getPassword() {
		return password;
	}

	String getUserName() {
		return userName;
	}

	int getUserId() {
		return userId;
	}

	@Override
	public String toString() {
	
		return "userName = " + getUserName() + "  userid= " + getUserId() + " password = " + getPassword()
		+"  issueBookList = "+issuedBooks;
	}

	public ArrayList<Book> getIssueBookList(){
		System.out.println("getIssueBookList = "+issuedBooks);
		return issuedBooks;
	}
}
