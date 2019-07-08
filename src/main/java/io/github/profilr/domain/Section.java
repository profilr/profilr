package io.github.profilr.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.ForeignKey;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import java.util.List;
import java.util.ArrayList;

import lombok.Data;

@Data
@Entity
@Table(name = "Sections")
public class Section {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "section_id", nullable = false, unique = true)
	private int sectionID;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "Course_ID_FK"))
	private Course course;
	
	@ManyToMany (cascade = CascadeType.ALL)
	@JoinTable (name = "SectionUsers",
				joinColumns = { @JoinColumn (name = "section_id") },
				inverseJoinColumns = {@JoinColumn (name = "user_id")})
	private List<User> users = new ArrayList<User>();
	
	public Course getCourse() {
		return this.course;
	}
	
}
