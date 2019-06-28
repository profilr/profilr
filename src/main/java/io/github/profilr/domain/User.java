package io.github.profilr.domain;

import java.util.*; 
import java.util.stream.*; 

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	@ManyToMany (mappedBy = "admins")
	private List<Course> administratedCourses = new ArrayList<Course>();

	@ManyToMany (mappedBy = "users")
	private List<Section> sectionsJoined = new ArrayList<Section>();
	
	public String getFullName() {
		return getFamilyName() + getGivenName();
	}
	
	public List<Course> getAdministratedCourses() {
		return this.administratedCourses;
	}
	
	public List<Course> getEnrolledCourses() {
		return (sectionsJoined.stream().map(s -> s.getCourse()).collect(Collectors.toList()));
	}
	
	
}
