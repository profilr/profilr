package io.github.profilr.web;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.glassfish.jersey.server.mvc.freemarker.FreemarkerDefaultConfigurationFactory;

import freemarker.core.HTMLOutputFormat;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerConfigurationFactory extends FreemarkerDefaultConfigurationFactory implements org.glassfish.jersey.server.mvc.freemarker.FreemarkerConfigurationFactory {

	@Inject
	public FreemarkerConfigurationFactory(ServletContext servletContext) {
		super(servletContext);
		configuration.setOutputFormat(HTMLOutputFormat.INSTANCE);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER); // TODO: should be RETHROW_HANDLER in production
		configuration.setTemplateUpdateDelayMilliseconds(5000); // TODO: should be 3600000 - one hour
	}

}
