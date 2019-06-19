package io.github.profilr.domain;

import java.util.List;

import lombok.Data;

@Data
public class Course {
	private int id;
	private String name;
	private List<Section> sections;
	private List<Test> tests;
	
	public Course() {}
	
}
