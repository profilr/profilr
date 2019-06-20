package io.github.profilr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.github.profilr.domain.db.Role;
import lombok.Data;

@Data

@Entity( name = "user" )
@Table( name = "USERS" )
public class User {
	
	@Id
	@Column(name = "user_id", nullable = false, unique = true)
	private String userID;
	
	@Column(name = "email_address")
	private String emailAddress;
	
	@Column(name = "given_name")
	private String givenName;
	
	@Column(name = "family_name")
	private String familyName;
	
	@OneToMany (
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Enrollment> enrollments = new ArrayList<Enrollment>();
	
	public User() {}
	
	public User setUserID(String id) {
		this.userID = id;
		return this;
	}
	
	public User setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}
	
	public User setGivenName(String givenName) {
		this.givenName = givenName;
		return this;
	}
	
	public User setFamilyName(String familyName) {
		this.familyName = familyName;
		return this;
	}
	
	public String getUserID() {
		return this.userID;
	}
	
	public String getFullName() {
		return this.familyName + this.givenName;
	}
	
	public void enroll(Course c) {
		Enrollment e = new Enrollment(this, c);
		if (enrollments.contains(e))
			return;
		
		enrollments.add(e);
		c.enroll(this);
	}
	
	public void unenroll(Course c) {
		Enrollment e = new Enrollment(this, c);
		if (!enrollments.contains(e))
			return;
		
		enrollments.remove(e);
		c.unenroll(this);
	}
	
	public List<Course> getEnrolledCourses() {
		List<Course> courses = new ArrayList<Course>();
		
		for (Enrollment e : enrollments)
			courses.add(e.getCourse());
			
		return courses;
	}
	
	public Role getRole(Course c) {
		for (Enrollment e : enrollments)
			if (e.getCourse().equals(c))
				return e.getRole();
		
		return null;
	}
	
	public boolean equals(Object other) {
		if (other == this)
			return true;
		
		if (other == null)
			return false;
		
		if (other.getClass() != this.getClass())
			return false;

		return this.userID == ((User)(other)).getUserID();
	}
	
}
