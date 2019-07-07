package io.github.profilr.db;

import javax.persistence.EntityManager;

import io.github.profilr.domain.User;

@SuppressWarnings("unused")
public class DAOTEST {

	public static void main(String[] args) {
		testInsert();
//		testUpdate();
//		testRemove();
	}

	private static void testInsert() {
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

	private static void testUpdate() {
		HibernateManager.createSessionFactory();

		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		entityManager.getTransaction().begin();
		User u = entityManager.find(User.class, "00000");
		u.setEmailAddress("arjvik2@gmail.com");
		entityManager.getTransaction().commit();
		entityManager.close();
		
		System.out.println("Completed");
	}
	
	private static void testRemove() {
		HibernateManager.createSessionFactory();
		
		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(User.class, "00000"));
		entityManager.getTransaction().commit();
		entityManager.close();

		
		System.out.println("Completed");
	}

}
