package com.lib.mgr;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Book {
	public String bookName = "";
	public int bookId = 0;
	boolean isIssued = false;
	LocalDate issueDate = null;
	LocalDate returnDate = null;
	public String section = ""; 
	boolean isQueued = false;

	Map listOfQueuedUsers = new HashMap<String, LibraryCardPerUser>();

	public Book(int bookIdCreate, String bookNameCreate) {
		bookName = bookNameCreate.toLowerCase();
		bookId = bookIdCreate;
		isIssued = false;
		isQueued = false;
	}

	public String getBookName() {
		return bookName;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookIssued(boolean doIssue) {
		if (doIssue) {
			setIssueDate(java.time.LocalDate.now());
		} else
			setIssueDate(null);
		isIssued = doIssue;

	}

//check if other method for date manipulation
	public void setIssueDate(LocalDate localDate) {
		issueDate = localDate;
		System.out.println("issueDate = " + issueDate);
		setReturnDate(localDate);
	}

	public void setReturnDate(LocalDate localDate) {
		if (null != localDate)
			returnDate = localDate.plusDays((long) Library.TIME_PERIOD_BOOK_RETURN+(long)Library.TIME_PERIOD_BOOK_RETURN_GRACE);
		else
			returnDate = null;
		System.out.println("returnDate = " + returnDate);
	}

	public boolean getIsBookIssued() {
		return isIssued;
	}

	public void setBookQueued(boolean isQueue, LibraryCardPerUser user) {
		isQueued = isQueue;
		updateQueuedUser(user);
		user.addBookToIssueList(this);

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "bookName = " + getBookName() + "id = " + getBookId() + "  isBookIssued = " + getIsBookIssued();
	}

	public void updateQueuedUser(LibraryCardPerUser user) {
		listOfQueuedUsers.put(user.getUserName(), user);
	}

	public boolean isUserQueued(LibraryCardPerUser user) {
		if (listOfQueuedUsers.get(user.getUserName()) != null)
			return true;
		else
			return false;
	}
	

	public boolean isBookInQueued(LibraryCardPerUser user) {
		if (listOfQueuedUsers.get(user.getUserName()) == null && (listOfQueuedUsers.size() > 0))
			return true;
		else
			return false;
	}
	
	public LocalDate getReturnDate() {
		return returnDate;
		
	}
	public LocalDate getIssueDate() {
		return issueDate;
		
	}
}
