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
		if (WebResource.DEBUG_MODE_ENABLED) {
			// Show freemarker errors in HTML, and only cache templates for 5 minutes
			// It is very important that this is not used in production
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
			configuration.setTemplateUpdateDelayMilliseconds(5000); // 5 minutes
		} else {
			// Rethrow all freemarker errors, and cache templates for at least one hour
			// This is safe for production use
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			configuration.setTemplateUpdateDelayMilliseconds(3600000); // one hour
		}
	}

}
