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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "TestQuestionAnswers")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="answerID")
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id", nullable = false, unique = true)
	private int answerID;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "correct")
	private boolean correct;
	
	@Column(name = "reason")
	private String reason;

	@Column(name = "notes")
	private String notes; // for better identification like #1 or #3b
	
	public String toString() {
		return "[ AnswerID: " + this.answerID + ", QuestionID: " + this.question.getQuestionID() + ", UserID: " + this.getUser().getUserID() + ", Correct: " + this.correct + ", Reason: " + this.reason + ", Notes: " + this.notes + "]";
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Answer))
			return false;
		
		return ((Answer) other).getAnswerID() == this.answerID;
	}
	
	public int hashCode() {
		return super.hashCode();
	}
	
}
