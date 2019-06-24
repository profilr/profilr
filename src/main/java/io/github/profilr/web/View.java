package io.github.profilr.web;

import java.util.HashMap;
import java.util.Map;

public class View extends HashMap<String, Object> implements Map<String, Object> {
	
	private static final long serialVersionUID = 1L;
	private Session session;
	
	public View() {}
	
	public View(Session session) { 
		this.session = session;
	}
	
	public Object get(String key) {
		if (key.equals("session"))
			return this.session;
		else
			return super.get(key);
	}
	
	@Override
	public Object put(String key, Object value) {
		if(key.equals("session")){
			Session oldSession = session;
			session = (Session) value;
			return oldSession;
		} else {
			return super.put(key, value);
		}
	}
	
}
