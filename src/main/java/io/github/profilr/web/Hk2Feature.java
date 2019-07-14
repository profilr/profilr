package io.github.profilr.web;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import io.github.profilr.db.EntityManagerFactory;

@Provider
public class Hk2Feature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		context.register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(Session.class).to(Session.class).in(RequestScoped.class);
				bindFactory(EntityManagerFactory.class, Singleton.class).to(EntityManager.class).in(RequestScoped.class);
			}
		});
		return true;
	}
}