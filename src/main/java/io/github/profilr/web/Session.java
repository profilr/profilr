package io.github.profilr.web;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.github.profilr.domain.User;

public class Session implements Map<String, Object> {

	private HttpServletRequest request;

	/**
	 * For dependency injection purposes only.
	 * Do not remove this constructor!
	 * HK2 fails to inject a proxy of this class 
	 * into an ExceptionMapper unless there is
	 * an additional no-arg constructor
	 */
	protected Session() {
		// do nothing
	}
	
	@Inject
	public Session(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * 
	 * A virtual session is one which does not store any properties,
	 * and thus has no reason to be stored. Calling the {@code request.getSession()}
	 * method when a session is virtual will cause it to be created and persisted,
	 * so we avoid doing so unless something needs to be stored in the current session.
	 * 
	 * @return true if there is no session for the current request,
	 * 		   false if there is already a saved session
	 */
	public boolean isVirtual() {
		return request.getSession(false) == null;
	}
	
	/**
	 * Creates a new saved session if the current session is virtual (does not exist)
	 */
	public void createSessionIfNotSaved() {
		request.getSession(true);
	}
	
	/**
	 * @return a {@code HttpSession} object representing the current
	 * 			session, null if it is virtual (does not exist)
	 */
	private HttpSession getSavedSession() {
		return request.getSession(false);
	}
	
	@Override
	public int size() {
		if (isVirtual())
			return 0;
		int i = 0;
		Enumeration<?> e = getSavedSession().getAttributeNames();
		while(e.hasMoreElements()) {
			e.nextElement();
			i++;
		}
		return i;
	}

	@Override
	public boolean isEmpty() {
		return isVirtual() || !getSavedSession().getAttributeNames().hasMoreElements();
	}

	@Override
	public boolean containsKey(Object key) {
		return !isVirtual() && getSavedSession().getAttribute((String) key) != null;
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(Object key) {
		return isVirtual() ? null : getSavedSession().getAttribute((String) key);
	}

	@Override
	public Object put(String key, Object value) {
		createSessionIfNotSaved();
		if(value==null)
			throw new NullPointerException();
		getSavedSession().setAttribute(key, value);
		return null;
	}

	@Override
	public Object remove(Object key) {
		if (isVirtual())
			return null;
		Object value = getSavedSession().getAttribute((String) key);
		getSavedSession().removeAttribute((String) key);
		return value;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		for(Entry<? extends String, ? extends Object> e : m.entrySet())
			put(e.getKey(), e.getValue());
	}

	@Override
	public void clear() {
		if (isVirtual())
			return;
		Enumeration<?> e = getSavedSession().getAttributeNames();
		while(e.hasMoreElements())
			remove(e.nextElement());
	}

	@Override
	public Set<String> keySet() {
		if (isVirtual())
			return new HashSet<>();
		Set<String> s = new HashSet<>();
		Enumeration<?> e = getSavedSession().getAttributeNames();
		while(e.hasMoreElements())
			s.add((String) e.nextElement());
		return Collections.unmodifiableSet(s);
	}

	@Override
	public Collection<Object> values() {
		if (isVirtual())
			return new HashSet<>();
		Set<Object> s = new HashSet<>();
		Enumeration<?> e = getSavedSession().getAttributeNames();
		while(e.hasMoreElements())
			s.add(getSavedSession().getAttribute((String) e.nextElement()));
		return Collections.unmodifiableSet(s);
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		if (isVirtual())
			return new HashSet<>();
		Set<Entry<String,Object>> s = new HashSet<>();
		Enumeration<?> e = getSavedSession().getAttributeNames();
		while(e.hasMoreElements()) {
			String key = (String) e.nextElement();
			s.add(new AbstractMap.SimpleImmutableEntry<String,Object>(key, getSavedSession().getAttribute(key)));
		}
		return Collections.unmodifiableSet(s);
	}
	
	/** same as <code>((User) get("user"))</code> convenience method because we kept doing this */
	public User getUser() {
		return (User) get("user");
	}

}