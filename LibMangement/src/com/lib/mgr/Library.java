package com.lib.mgr;

import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Library {
	public static final String LIBRARY_REFERENCE_SECTION = "LIBRARY_REFERENCE_SECTION";
	public static final String LIBRARY_ISSUED_SECTION = "LIBRARY_ISSUED_SECTION";
	public static final int REFERENCE_TIME_SLOT = 1;// its in hours
	public static final int TIME_PERIOD_BOOK_RETURN = 15;// in days
	public static final int TIME_PERIOD_BOOK_RETURN_GRACE = 3;// in days
	//
	private Map<String, Book> referenceSectionBooksMap = new HashMap<>();
	private Map<String, Book> issuedSectionBooksMap = new HashMap<>();
	private Map<String, LibraryCardPerUser> mapAllUsers = new HashMap<>();

	public Library(Map<String, LibraryCardPerUser> mapUsers, Map<String, Book> refSecMap) {
		referenceSectionBooksMap = refSecMap;
		mapAllUsers = mapUsers;
	}

	public void addBookToReferenceSection(Book book) {

		referenceSectionBooksMap.put(book.bookName, book);
	}

	public void removeBookFromReferenceSection(Book book) {

		referenceSectionBooksMap.remove(book.bookName, book);
		System.out.println(" removeBookFromReferenceSection  refsec = " + referenceSectionBooksMap);
	}

	boolean isBookAvailableInRefSection(String bookName) {
		boolean result = false;
		Book book = referenceSectionBooksMap.get(bookName);// O(1)
		if (null != book)
			result = true;
		return result;
	}

	boolean isBookAvailableInIssueSection(String bookName) {
		boolean result = false;
		Book book = issuedSectionBooksMap.get(bookName);// O(1)
		if (null != book)
			result = true;
		return result;
	}


	public void addBookToIssuedSection(Book book) {

		System.out.println("addBookToIssuedSection book= " + book);
		issuedSectionBooksMap.put(book.bookName, book);
		System.out.println("issue section now = " + issuedSectionBooksMap);
	}

	public void removeBookFromIssuedSection(Book book) {
		issuedSectionBooksMap.remove(book.bookName, book);
	}


	public void addUserToLibrary(LibraryCardPerUser user) {
		if (null != mapAllUsers) {
			if (null == mapAllUsers.get(user.getUserName()))
				mapAllUsers.put(user.getUserName(), user);
			else {
				System.out.println("user already exists, show login successful");
			}
		} else {
			mapAllUsers = new HashMap<>();
			mapAllUsers.put(user.getUserName(), user);
		}
		
	}

	public void removeUserFromLibrary(LibraryCardPerUser user) {
		mapAllUsers.remove(user.getUserName());
	}


	public Map<String, Book> getReferenceSectionMap() {
		return referenceSectionBooksMap;
	}

	public Map<String, Book> getIssueSectionMap() {
		return issuedSectionBooksMap;
	}

	public Map<String, LibraryCardPerUser> getAllUserMap() {
		return mapAllUsers;
	}

	int issueBook(String bookName, LibraryCardPerUser user) {
		System.out.println("trying to issue a book for user = " + user);
		// find in ref section

		System.out.println("book in REF section = " + this.isBookAvailableInRefSection(bookName.toLowerCase()));
		// find in issue section
		System.out.println("book in read section = " + this.isBookAvailableInIssueSection(bookName.toLowerCase()));
	
		boolean isBookAvailableInRefSection = this.isBookAvailableInRefSection(bookName.toLowerCase());
		boolean isBookAvailableInIssueSection = this.isBookAvailableInIssueSection(bookName.toLowerCase());

		Book tempBook = null;
		if (isBookAvailableInRefSection) {
			tempBook = this.getReferenceSectionMap().get(bookName.toLowerCase());
		} else if (isBookAvailableInIssueSection) {
			tempBook = this.getIssueSectionMap().get(bookName.toLowerCase());
			tempBook.setBookQueued(true, user);

			System.out.println("book already issued its been queued");

			return 1;
		} else {
			System.out.println("This book " + bookName + " is not present, kindly enter correct bookname from catalog");
			return 2;
	
		}
		System.out.println("tempBook = " + tempBook);
		tempBook.setBookIssued(true);
		// manipulate library
		this.addBookToIssuedSection(tempBook);
		this.removeBookFromReferenceSection(tempBook);
		// manipulate book

		// manipulate user
		this.getAllUserMap().get(user.getUserName()).addBookToIssueList(tempBook);
		System.out.println("user after book ISSUED = " + user);
		return 0;
	}

	void readBook(String bookName, LibraryCardPerUser user) {
		System.out.println("trying to issue a book for user = " + user);
		// find in ref section
		System.out.println("book in REF section = " + this.isBookAvailableInRefSection(bookName.toLowerCase()));

		Book tempBook = this.getReferenceSectionMap().get(bookName.toLowerCase());
		System.out.println("tempBook = " + tempBook);
	}

	public void displayIssuedBookList() {
		for (Map.Entry<String, Book> entry : issuedSectionBooksMap.entrySet())
			System.out.println("bookname :: " + entry.getKey() + "    [status = issued]");

	}

	public void displayRefBookList() {
		for (Map.Entry<String, Book> entry : referenceSectionBooksMap.entrySet())
			System.out.println("bookname :: " + entry.getKey() + "    [status = available]");

	}

	public void reIssueABook(LibraryCardPerUser user, String bookName) {
		Book tempBook = null;
		System.out.println("reIssueABook bookName = "+bookName);
		if (this.isBookAvailableInIssueSection(bookName.toLowerCase())) {

			System.out.println("reIssueABook debug 1");
			tempBook = this.getIssueSectionMap().get(bookName.toLowerCase());
			long diffInDays = java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.now(),
					tempBook.getReturnDate());
			System.out.println("reIssueABook  diffInDays = "+diffInDays);
			if (tempBook.isBookInQueued(user) && (diffInDays >= 18)) {
				// assuming user coming on or after 18 days
				System.out.println("book not to be issued.it will be retured");
				removeBook(user, bookName);
			} else
				issueBook(bookName, user);

		}

	}

	public void removeBook(LibraryCardPerUser user, String bookName) {
		Book tempBook = null;
		if (this.isBookAvailableInIssueSection(bookName.toLowerCase())) {
			tempBook = this.getIssueSectionMap().get(bookName.toLowerCase());
			// library updated
			removeBookFromIssuedSection(tempBook);
			// user updated
			user.removeBookFromIssueList(tempBook);
			tempBook.setBookIssued(false);

		}

	}
	
	public boolean isBookAvailableInLibrary(String bookName) {
		return isBookAvailableInIssueSection(bookName)||isBookAvailableInRefSection(bookName);
	}
}
