package io.github.profilr.domain;

import lombok.Data;

@Data
public class Topic {

	// TODO Annotations
	
	private int topicID;
	private String name;
	private Course course;
	
	public Topic() {}
	
}
