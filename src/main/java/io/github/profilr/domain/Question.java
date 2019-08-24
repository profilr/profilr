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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Questions")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="questionID")
public class Question implements Comparable<Question> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id", nullable = false, unique = true)
	private int questionID;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "test_id")
	@EqualsAndHashCode.Exclude
	private Test test;

	@Column(name = "label")
	private String label; // for better identification like #1 or #3b
	
	@Column(name = "weight")
	private int weight;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "question_type_id")
	private QuestionType questionType;

	public int compareTo(Question o) {
		String myStringPart = this.label.replaceAll("\\d", "");
		String otherStringPart = o.label.replaceAll("\\d", "");
		
		if (myStringPart.equalsIgnoreCase(otherStringPart))
			return extractInt(this.label) - extractInt(o.label);
		else
			return myStringPart.compareTo(otherStringPart);
	}
	
	private static int extractInt(String s) {
		String num = s.replaceAll("\\D", "");
		return num.isEmpty() ? 0 : Integer.parseInt(num);
	}
	
}
