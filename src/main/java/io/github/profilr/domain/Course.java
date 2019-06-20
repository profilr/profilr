package io.github.profilr.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
public class Course {
	
	@Id
	@GeneratedValue
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
		Enrollment e = new Enrollment(u, this);
		if (enrollments.contains(e))
			return;
		
		enrollments.add(e);
		u.enroll(this);
	}
	
	public void unenroll(User u) {
		Enrollment e = new Enrollment(u, this);
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
			if (e.getRole().equals(Role.OWNER))
				return e.getUser();
		}
		return null;
	}
	
	public boolean equals(Object other) {
		if (other == this)
			return true;
		
		if (other == null)
			return false;
		
		if (other.getClass() != this.getClass())
			return false;

		return this.courseID == ((Course)(other)).getCourseID();
	}
	
}
