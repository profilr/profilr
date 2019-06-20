package io.github.profilr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.github.profilr.domain.db.Role;

@Entity(name = "Enrollment")
@Table(name = "enrollments")
public class Enrollment {

	@Id
	@GeneratedValue
	private int enrollmentID;
	
	@ManyToOne
	@MapsId("user_id")
	private User user;
	
	@Column(name = "role")
	private int role;
	
	@OneToOne
	@JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "COURSE_ID_FK"))
	private Section section;
	
	@ManyToOne
	@MapsId("course_id")
	private Course course;
	
	public Enrollment() {}
	
	public Enrollment(User u, Course c) {
		this.user = u;
		this.course = c;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public Role getRole() {
		return Role.getRole(role);
	}
	
	public Course getCourse() {
		return this.course;
	}
	
	public boolean equals(Object o) {
		if (this == o) return true;
		
		if (o == null || getClass() != o.getClass())
			return false;
		
		Enrollment that = (Enrollment) o;
		return this.user.equals(that.user) && this.course.equals(that.course);
	}
	
}
