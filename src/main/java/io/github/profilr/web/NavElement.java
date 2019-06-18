package io.github.profilr.web;

import java.util.HashMap;

public class NavElement extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public NavElement(String name, String displayName, String uri) {
		this.put("name", name);
		this.put("displayName", displayName);
		this.put("uri", uri);
	}
	
	public String getName() {
		return (String)this.get("name");
	}
	
	public String getDisplayName() {
		return (String)this.get("displayName");
	}
	
	public NavElement setDisplayName(String displayName) {
		this.put("displayName", displayName);
		return this;
	}
	
	public NavElement setUri(String uri) {
		this.put("uri", uri);
		return this;
	}
	
}
