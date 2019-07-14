package io.github.profilr.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.glassfish.jersey.internal.inject.DisposableSupplier;
import org.hibernate.SessionFactory;

public class EntityManagerFactory implements DisposableSupplier<EntityManager> {

	public SessionFactory sessionFactory = new SessionFactoryFactory().get();
	
	@Override
	public EntityManager get() {
		EntityManager e = sessionFactory.createEntityManager();
		e.getTransaction().begin();
		return e;
	}

	@Override
	public void dispose(EntityManager e) {
		if(e.isOpen()) {
			EntityTransaction t = e.getTransaction();
			if (t.isActive())
				t.commit();
			e.close();
		}
	}

}
