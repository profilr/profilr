package io.github.profilr.domain;

import java.util.List;

import lombok.Data;

@Data
public class Section {
	private int id;
	private Course course;
	private User instructor;
	private List<User> students;
}
