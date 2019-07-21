package io.github.profilr.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table( name = "Tests" )
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "test_id", nullable = false, unique = true)
	private int testID;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question> questions;
	
}
