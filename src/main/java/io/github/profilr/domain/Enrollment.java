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

import lombok.Data;

@Data
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
	
	public Role getTrueRole() {
		return Role.getRole(getRole());
	}
	
}
