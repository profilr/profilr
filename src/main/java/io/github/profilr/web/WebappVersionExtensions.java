package io.github.profilr.web;

import javax.servlet.ServletContext;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WebappVersionExtensions {

	private static final String VERSION_SEPERATOR = "##";
	private static final String PATH_SEPERATOR = "/";
	
	public static final String getWebappVersion(ServletContext context) {
		String path = context.getRealPath("/");
		if (path.contains(VERSION_SEPERATOR)) {
			// Remove everything before the last "##" in the path (returns "(UNKNOWN)" if not found)
			path = path.substring(path.lastIndexOf(VERSION_SEPERATOR) + VERSION_SEPERATOR.length());
			
			// Remove everything after the first "/" in the path (only if exists)
			if (path.contains(PATH_SEPERATOR))
				path = path.substring(0, path.indexOf(PATH_SEPERATOR));
			
			// What's left is the webapp version code
			return path;
		} else {
			// The context path doesn't have a version available
			return "(Version Unknown)";
		}
	}
	
}
