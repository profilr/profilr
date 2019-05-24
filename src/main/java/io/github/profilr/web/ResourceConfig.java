package io.github.profilr.web;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

@ApplicationPath("")
public class ResourceConfig extends org.glassfish.jersey.server.ResourceConfig {
	
	public ResourceConfig(){
		packages("io.github.profilr.web");
		register(FreemarkerMvcFeature.class);
		property("jersey.config.server.mvc.templateBasePath.freemarker", "/WEB-INF/freemarker");
		property("jersey.config.servlet.filter.staticContentRegex", "(/(image|js|css|resources|debug)/?.*)|(/.*.(html|jsp))|(/favicon.ico)|(/robots.txt)");
	}
	
}
