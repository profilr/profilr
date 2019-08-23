package io.github.profilr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "QuestionTypes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="questionTypeID")
public class QuestionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_type_id", nullable = false, unique = true)
	private int questionTypeID;

	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
}
