package io.github.profilr.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table( name = "Users" )
public class User {
	
	@Id
	@Column(name = "user_id", nullable = false, unique = true)
	private String userID;
	
	@Column(name = "email_address", nullable = false, unique = true)
	private String emailAddress;
	
	@Column(name = "given_name", nullable = false)
	private String givenName;
	
	@Column(name = "family_name", nullable = false)
	private String familyName;
	
	@Column(name = "course_admin_approved", nullable = false)
	private boolean courseAdminApproved;
	
	@ManyToMany(mappedBy = "admins", fetch = FetchType.EAGER)
	private Set<Course> administratedCourses = new HashSet<Course>();

	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private Set<Section> sectionsJoined = new HashSet<Section>();
	
	public String getFullName() {
		return getFamilyName() + ", " + getGivenName();
	}
	
	public Set<Course> getEnrolledCourses() {
		return getSectionsJoined().stream().map(s -> s.getCourse()).collect(Collectors.toSet());
	}
	
	public boolean isCourseAdmin(Course c) {
		for (Course course : this.getAdministratedCourses())
			if (c.getCourseID() == course.getCourseID())
				return true;
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("User: %s (%s)", getFullName(), getUserID());
	}
	
}
