package io.github.profilr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "section")
@Table(name = "sections")
public class Section {
	
	@Id
	@Column(name = "section_id", nullable = false, unique = true)
	private int sectionID;
	
	@Column(name = "section_name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "COURSE_ID_FK"))
	private Course course;
	
	public Section() {}
	
	public int getSectionID() {
		return this.sectionID;
	}
	
	public boolean equals(Object other) {
		if (other == this)
			return true;
		
		if (other == null)
			return false;
		
		if (other.getClass() != this.getClass())
			return false;

		return this.sectionID == ((Section)(other)).getSectionID();
	}
	
}
