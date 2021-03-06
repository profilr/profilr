package io.github.profilr.domain;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table( name = "Tests" )
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="testID")
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "test_id", nullable = false, unique = true)
	private int testID;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
	@OrderBy
	private SortedSet<Question> questions = new TreeSet<Question>();
	
	@Column(name="published")
	private boolean published;
	
	public Optional<TestResponse> getResponsesForUser(User u, EntityManager em) {	
		return em.createNamedQuery(TestResponse.GET_BY_USER_AND_TEST_NQ, TestResponse.class)
					.setParameter("user", u)
					.setParameter("test", this)
					.getResultStream()
					.findFirst();
	}
}
