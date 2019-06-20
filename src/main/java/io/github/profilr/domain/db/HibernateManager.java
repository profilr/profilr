package io.github.profilr.domain.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateManager {

	private static SessionFactory sessionFactory;
	
	public static void createSessionFactory() {
		try {
			// TODO Uncomment this whenever hibernate annotations are finished in all the classes.
			
			/*
			Configuration cfg = new Configuration();
			cfg.addClass(User.class);
			cfg.addClass(Course.class);
			cfg.addClass(Enrollment.class);
			cfg.addClass(Section.class);
			cfg.addClass(Test.class);
			cfg.addClass(Topic.class);
			*/
			
			sessionFactory = new Configuration().configure().buildSessionFactory();
			
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
