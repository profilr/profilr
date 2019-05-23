package io.github.profilr.domain;

import java.util.List;

import lombok.Data;

@Data
public class Course {
	private int id;
	private String name;
	private List<User> admins;
	private List<Section> sections;
	private List<Test> tests;
}
