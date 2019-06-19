package io.github.profilr.domain.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import io.github.profilr.domain.User;

public class UserManager {

	public static void addUser(User u) {
		Session s = HibernateManager.getSessionFactory().openSession();
		
		Transaction tx = null;
		 
		try {
			tx = s.beginTransaction();
			s.save(u);
			tx.commit();
		} catch(HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
	}
	
	public static void removeUser(User u) {
		/* TODO Remove user's record from the user table.
		 * Need to also remove the references to the user in the enrollment and role tables...
		 * Should also check if the user being removed is the owner of any sections or courses that should be removed.
		 */
	}
	
	public static User getUser(int userId) {
		// TODO Get a user from the users table
		User u = new User();
		return u;
	}
	
}
