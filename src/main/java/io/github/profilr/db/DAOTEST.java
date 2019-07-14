package io.github.profilr.db;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import io.github.profilr.domain.Course;
import io.github.profilr.domain.User;

public class DAOTEST {

	private static EntityManagerFactory emf;
	private static EntityManager entityManager;
	
	private static void initEntityManager() {
		emf = new EntityManagerFactory();
		entityManager = emf.get();
	}
	
	private static void deinitEntityManager() {
		emf.dispose(entityManager);
	}
	
	public static void main(String[] args) {
		
		   //////////////////////////////
		  //   Init entity manager  ////
		 /**/ initEntityManager(); ////
 		//////////////////////////////
		
		// Pick any testing method
		
		int i = 0;
		
		switch (i) {
		case 0: testInsertUser(); break;
		case 1: testUpdateUser(); break;
		case 2: testRemoveUser(); break;
		case 3: testViewCourses(); break;
		case 4: testCreateCourse(); break;
		case 5: testRemoveCourse(); break;
		}
		 
		   ////////////////////////////////
		  ////  Deinit entity manager ////
		 /**/ deinitEntityManager(); ////
 		////////////////////////////////

		System.out.println("Completed");


	}

	private static void testInsertUser() {
		User user = new User();
		user.setUserID("00000");
		user.setGivenName("Arjun");
		user.setFamilyName("Vikram");
		user.setEmailAddress("arjvik@gmail.com");

		entityManager.persist(user);
	}

	private static void testUpdateUser() {
		User u = entityManager.find(User.class, "00000");
		u.setEmailAddress("arjvik2@gmail.com");
	}
	
	private static void testRemoveUser() {
		entityManager.remove(entityManager.find(User.class, "00000"));
	}
	
	private static void testViewCourses() {
		User user = entityManager.find(User.class, "00000");
		System.out.println(user.getAdministratedCourses());
	}

	private static void testCreateCourse() {
		Course c = new Course();
		c.setName("TestCourse");
		c.setAdmins(new ArrayList<User>());
		c.getAdmins().add(entityManager.find(User.class, "00000"));
		
		entityManager.persist(c);
	}
	
	private static void testRemoveCourse() {
		User u = entityManager.find(User.class, "00000");
		
		Course c = u.getAdministratedCourses().get(0);
		
		entityManager.remove(c);
	}
	
}
