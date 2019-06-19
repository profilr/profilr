package io.github.profilr.domain;

import java.util.List;

import lombok.Data;

@Data
public class User {
	
	private int id;
	private int studentID;
	private List<Course> courses;
	
	public User() {}
	
}
