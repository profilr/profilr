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
	
	@OneToMany(mappedBy="course", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Section> sections;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Test> tests;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Topic> topics;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable (name = "CourseAdministrators",
				joinColumns = {@JoinColumn(name = "course_id")},
				inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private List<User> admins;
	
	public boolean equals(Object other) {
		if (!(other instanceof Course))
			return false;
		
		return ((Course) other).getCourseID() == this.courseID;
	}
	
	public String toString() {
		return Integer.toString(this.courseID);
	}
	
}
