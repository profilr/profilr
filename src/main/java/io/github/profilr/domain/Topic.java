package io.github.profilr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Topics")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topic_id", nullable = false, unique = true)
	private int topicID;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "COURSE_ID_FK"))
	private Course course;
	
}
