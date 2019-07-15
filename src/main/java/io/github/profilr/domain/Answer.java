package io.github.profilr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TestQuestionAnswers")
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id", nullable = false, unique = true)
	private int answerID;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Test question;

	@Column(name = "correct")
	private boolean correct;
	
	@Column(name = "reason")
	private String reason;

	@Column(name = "notes")
	private String notes; // for better identification like #1 or #3b
	
	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;
	
}
