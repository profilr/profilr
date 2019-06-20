package io.github.profilr.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseID;
	
	@Column(name = "course_name")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Section> sections;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Test> tests;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Topic> topics;
	
	@OneToMany (
			mappedBy = "course",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Enrollment> enrollments;
	
	public Course() {}

	public int getCourseID() {
		return this.courseID;
	}
	
	public void enroll(User u) {
		Enrollment e = new Enrollment();
		e.setUser(u);
		e.setCourse(this);
		if (enrollments.contains(e))
			return;
		
		enrollments.add(e);
		u.enroll(this);
	}
	
	public void unenroll(User u) {
		Enrollment e = new Enrollment();
		e.setUser(u);
		e.setCourse(this);
		if (!enrollments.contains(e))
			return;
		
		enrollments.remove(e);
		u.unenroll(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public User getOwner() {
		for (Enrollment e : enrollments) {
			if (e.getTrueRole().equals(Role.OWNER))
				return e.getUser();
		}
		return null;
	}
	
}
