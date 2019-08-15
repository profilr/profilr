package io.github.profilr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "Answers")
@NamedQueries({
	@NamedQuery(name = Answer.GET_BY_USER_AND_TEST_NQ, query = "FROM Answer WHERE user = :user AND question.test = :test"),
	@NamedQuery(name = Answer.GET_BY_USER_AND_QUESTION_NQ, query = "FROM Answer WHERE user = :user AND question= :question")
})
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
	private int correct;

	@ManyToOne
	@JoinColumn(name = "reason_id", nullable = true)
	private Reason reason;
	
	@Column(name = "notes")
	private String notes;
	
	public static final String GET_BY_USER_AND_TEST_NQ = "ANSWER.GET_BY_USER_AND_TEST";
	public static final String GET_BY_USER_AND_QUESTION_NQ = "ANSWER.GET_BY_USER_AND_QUESTION";
	
}
