package io.github.profilr.web;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;

import io.github.profilr.db.HibernateManager;

@ApplicationPath("")
public class ResourceConfig extends org.glassfish.jersey.server.ResourceConfig {
	
	public ResourceConfig(){
		HibernateManager.createSessionFactory();
		
		packages("io.github.profilr.web.webresources");
		register(FreemarkerMvcFeature.class);
		property(FreemarkerMvcFeature.CACHE_TEMPLATES, false);
		property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/freemarker");
		property(FreemarkerMvcFeature.TEMPLATE_OBJECT_FACTORY, FreemarkerConfigurationFactory.class.getName());
		property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "(/(image|js|css|resources|debug)/?.*)|(/.*.(html|jsp))|(/favicon.ico)|(/robots.txt)");
	}
	
}
