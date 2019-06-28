package io.github.profilr.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.Question;
import io.github.profilr.domain.Section;
import io.github.profilr.domain.Test;
import io.github.profilr.domain.Topic;
import io.github.profilr.domain.User;

public class HibernateManager {

	private static SessionFactory sessionFactory;
	
	public static void createSessionFactory() {
		try {
			Configuration conf = new Configuration().addAnnotatedClass(User.class)
													.addAnnotatedClass(Course.class)
													.addAnnotatedClass(Section.class)
													.addAnnotatedClass(Test.class)
													.addAnnotatedClass(Topic.class)
													.addAnnotatedClass(Question.class);			
			
			sessionFactory = conf.configure().buildSessionFactory();
			
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
