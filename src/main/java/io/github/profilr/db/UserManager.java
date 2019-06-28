package io.github.profilr.db;

import javax.persistence.EntityManager;

import io.github.profilr.domain.User;

public class UserManager {

	public static void addUser(User u) {
		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(u);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	public static void removeUser(User u) {
		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		entityManager.remove(u);
		entityManager.close();
	}
	
	public static User getUser(String userId) {
		EntityManager entityManager = HibernateManager.getSessionFactory().createEntityManager();
		User u = entityManager.find(User.class, userId);
		entityManager.close();
		
		return u;
	}
	
}
