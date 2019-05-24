package io.github.profilr.web;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.glassfish.jersey.server.mvc.freemarker.FreemarkerDefaultConfigurationFactory;

import freemarker.template.TemplateExceptionHandler;

public class FreemarkerConfigurationFactory extends FreemarkerDefaultConfigurationFactory implements org.glassfish.jersey.server.mvc.freemarker.FreemarkerConfigurationFactory {

	@Inject
	public FreemarkerConfigurationFactory(ServletContext servletContext) {
		super(servletContext);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
	}

}
