package com.lib.mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainLauncher {

	public static void main(String[] args) {
		LoginPage loginPage = new LoginPage();
		Book book1 = new Book(1, "Maths");
		Book book2 = new Book(2, "English");
		Book book3 = new Book(3, "Science");
		Book book4 = new Book(4, "History");

		Map<String, Book> referenceSectionBooksMap = new HashMap<>();
		Map<String, Book> readingSectionBooksMap = new HashMap<>();
		ArrayList<Book> referenceList = new ArrayList<>();// ashish to check if add in constructor

		referenceList.add(book1);
		referenceList.add(book2);
		referenceSectionBooksMap.put(book1.bookName, book1);
		referenceSectionBooksMap.put(book2.bookName, book2);
		ArrayList<Book> readingList = new ArrayList<>();
		readingList.add(book3);
		readingList.add(book4);
		referenceSectionBooksMap.put(book3.bookName, book3);
		referenceSectionBooksMap.put(book4.bookName, book4);

		Library mainLibrary = new Library(null, referenceSectionBooksMap);
		do {
			loginPage.startLogin();
			LibraryCardPerUser userOne = null;
			if ((mainLibrary.getAllUserMap() != null)
					&& mainLibrary.getAllUserMap().get(loginPage.getUserName()) != null) {

				// user already exists and is trying to login,skip creating new user
				System.out.println("user already exists!!!!!!");
				userOne = mainLibrary.getAllUserMap().get(loginPage.getUserName());
			} else {
				System.out.println("Create new user");
				userOne = new LibraryCardPerUser(loginPage.getUserName(), loginPage.getUserId(),
						loginPage.getPassword());
				mainLibrary.addUserToLibrary(userOne);
			}

			String[] issueDetails = { "", "", "failure" };

			while (issueDetails[2].equals("failure")) {
				issueDetails = loginPage.getBookName(mainLibrary, userOne);
			}

			if ("issue".equals(issueDetails[0])) {

				int i = mainLibrary.issueBook(issueDetails[1], userOne);

				if (i == 0)
					System.out.println("book has been issued");
				else if (i == 2) {
					continue;
				} else
					System.out.println("book is in queue");

				loginPage.logout();

			} else if ("read".equals(issueDetails[0])) {
				mainLibrary.readBook(issueDetails[1], userOne);
				System.out.println("book has been given for a particular time slot");
				loginPage.logout();
			} else if ("re-issue".equals(issueDetails[0])) {
				mainLibrary.reIssueABook(userOne, issueDetails[1]);
				System.out.println("book has been re-issued");
				loginPage.logout();
			}

		} while (true);

	}

}
