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
@Table(name = "questions")
public class Question {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id", nullable = false, unique = true)
	private int questionID;
	
	@ManyToOne
	@JoinColumn(name = "test_id", foreignKey = @ForeignKey(name = "TEST_ID_FK"))
	private Test parentTest;
	

	@Column(name = "label")
	private String label; // for better identification like #1 or #3b
	
	@ManyToOne
	@JoinColumn(name = "topic_id", foreignKey = @ForeignKey(name = "TOPIC_ID_FK"))
	private Topic topic;
	
}
