package io.github.profilr.db;

import io.github.profilr.domain.User;

public class DAOTEST {

	public static void main(String[] args) {
		User user = new User();
		user.setUserID("00000");
		user.setGivenName("Arjun");
		user.setFamilyName("Vikram");
		user.setEmailAddress("arjvik@gmail.com");

		HibernateManager.createSessionFactory();
		
		UserManager.addUser(user);
		
		System.out.println("Completed");
		
	}

}
