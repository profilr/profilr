package io.github.profilr.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id", nullable = false, unique = true)
	private int courseID;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy="course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Section> sections;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Test> tests;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Topic> topics;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable (name = "CourseAdministrators",
				joinColumns = {@JoinColumn(name = "course_id")},
				inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private List<User> admins;
	
	public Course() {}
	
	public int getCourseID() {
		return this.courseID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}
	
}
