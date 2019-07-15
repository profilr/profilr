package io.github.profilr.web;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;

@ApplicationPath("")
public class ResourceConfig extends org.glassfish.jersey.server.ResourceConfig {
	
	public ResourceConfig(){
		packages("io.github.profilr.web");
		register(FreemarkerMvcFeature.class);
		property(FreemarkerMvcFeature.CACHE_TEMPLATES, false);
		property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/freemarker");
		property(FreemarkerMvcFeature.TEMPLATE_OBJECT_FACTORY, FreemarkerConfigurationFactory.class.getName());
		property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "(/(styles)/.*)|(/favicon\\.ico)|(/robots\\.txt)");
	}
	
}
