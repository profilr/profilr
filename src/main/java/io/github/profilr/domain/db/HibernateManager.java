package io.github.profilr.domain.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/* TODO Create 'Manager' classes for Test, Topic, and Course with some static functions for getting the corresponding objects
 * like in SectionManager and UserManager.
 */
public class HibernateManager {

	private static SessionFactory sessionFactory;
	
	public static void createSessionFactory() {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
