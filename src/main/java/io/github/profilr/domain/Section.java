package io.github.profilr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "Sections")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="sectionID")
@NamedQuery(name = Section.SELECT_VIA_JOIN_CODE_NQ, query = "FROM Section where joinCode = :joinCode")
public class Section {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "section_id", nullable = false, unique = true)
	private int sectionID;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
	@Column(name = "join_code")
	private String joinCode;
	
	@ManyToMany (cascade = CascadeType.MERGE)
	@JoinTable (name = "SectionUsers",
				joinColumns = { @JoinColumn (name = "section_id") },
				inverseJoinColumns = {@JoinColumn (name = "user_id")})
	private List<User> users = new ArrayList<User>();

	public static final String SELECT_VIA_JOIN_CODE_NQ = "Section.SELECT_VIA_JOIN_CODE_NQ";
	
}
