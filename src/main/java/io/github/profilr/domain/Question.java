package io.github.profilr.domain;

import java.util.Map;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TestQuestions")
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id", nullable = false, unique = true)
	private int questionID;
	
	@ManyToOne
	@JoinColumn(name = "test_id")
	private Test test;

	@Column(name = "label")
	private String label; // for better identification like #1 or #3b

	@Column(name = "text")
	private String text;
	
	@Column(name = "weight")
	private int weight;
	
	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;
	
	public Map<String, Object> getView() {
		Map<String, Object> v = new HashMap<String, Object>();
		v.put("questionId", this.questionID);
		v.put("label", this.label);
		v.put("text", this.text);
		v.put("weight", this.weight);
		v.put("topic", this.topic.getView());
		return v;
	}
	
}
