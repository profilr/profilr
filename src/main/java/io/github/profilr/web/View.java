package io.github.profilr.web;

import java.util.HashMap;
import java.util.Map;

public class View extends HashMap<String, Object> implements Map<String, Object> {
	
	private static final long serialVersionUID = 1L;
	
	public View() {}
	
	public View(Session session) { 
		put("session", session);
	}
	
}
