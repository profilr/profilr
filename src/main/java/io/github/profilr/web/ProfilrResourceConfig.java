package io.github.profilr.web;

import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;

public class ProfilrResourceConfig extends org.glassfish.jersey.server.ResourceConfig {
	
	public ProfilrResourceConfig(){
		property(FreemarkerMvcFeature.CACHE_TEMPLATES, false);
		property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/WebContent/");
		property(FreemarkerMvcFeature.TEMPLATE_OBJECT_FACTORY, FreemarkerConfigurationFactory.class.getName());
		property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "(/(image|js|css|resources|debug)/?.*)|(/.*.(html|jsp))|(/favicon.ico)|(/robots.txt)");
		register(FreemarkerMvcFeature.class);
		
		packages("io.github.profilr.web");
	}
	
}
