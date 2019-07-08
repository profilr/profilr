package io.github.profilr.db;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.User;

@SuppressWarnings("unused")
public class DAOTEST {

	public static void main(String[] args) {
//		testInsertUser();
//		testUpdateUser();
		testRemoveUser();
//		testViewCourses();
//		testCreateCourse();
//		testRemoveCourse();
	}

	private static void testInsertUser() {
		User user = new User();
		user.setUserID("00000");
		user.setGivenName("Arjun");
		user.setFamilyName("Vikram");
		user.setEmailAddress("arjvik@gmail.com");

		HibernateManager.createSessionFactory();
		
		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		entityManager.close();

		
		System.out.println("Completed");
	}

	private static void testUpdateUser() {
		HibernateManager.createSessionFactory();

		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		entityManager.getTransaction().begin();
		User u = entityManager.find(User.class, "00000");
		u.setEmailAddress("arjvik2@gmail.com");
		entityManager.getTransaction().commit();
		entityManager.close();
		
		System.out.println("Completed");
	}
	
	private static void testRemoveUser() {
		HibernateManager.createSessionFactory();
		
		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(User.class, "00000"));
		entityManager.getTransaction().commit();
		entityManager.close();

		
		System.out.println("Completed");
	}
	
	private static void testViewCourses() {
		HibernateManager.createSessionFactory();
		
		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, "00000");
		System.out.println(user.getAdministratedCourses());
		entityManager.getTransaction().commit();
		entityManager.close();

		
		System.out.println("Completed");
	}

	private static void testCreateCourse() {
		HibernateManager.createSessionFactory();
		
		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		entityManager.getTransaction().begin();
		
		Course c = new Course();
		c.setName("TestCourse");
		c.setAdmins(new ArrayList<User>());
		c.getAdmins().add(entityManager.find(User.class, "00000"));
		
		entityManager.persist(c);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		System.out.println("Completed");
	}
	
	private static void testRemoveCourse() {
		HibernateManager.createSessionFactory();
		
		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		entityManager.getTransaction().begin();
		
		User u = entityManager.find(User.class, "00000");
		
		Course c = u.getAdministratedCourses().get(0);
		
		entityManager.remove(c);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		System.out.println("Completed");
	}
	
}
