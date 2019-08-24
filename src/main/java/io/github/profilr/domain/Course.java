package io.github.profilr.domain;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "Courses")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="courseID")
public class Course implements Comparable<Course> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id", nullable = false, unique = true)
	private int courseID;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy="course", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Section> sections;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Test> tests;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Topic> topics;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<QuestionType> questionTypes;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable (name = "CourseAdministrators",
				joinColumns = {@JoinColumn(name = "course_id")},
				inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private List<User> admins;
	
	public List<User> getEnrolledStudents() {
		return getSections().stream()
							.flatMap(s -> s.getUsers()
										   .stream())
							.collect(Collectors.toList());
	}
	
	@Override
	public int compareTo(Course c) {
		return this.getName().compareTo(c.getName());
	}
	
	public String toString() {
		return Integer.toString(this.courseID);
	}
	
}
