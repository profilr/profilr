package io.github.profilr.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Responses")
@NamedQuery(name = TestResponse.GET_BY_USER_AND_TEST_NQ, query = "FROM TestResponse WHERE user = :user AND test = :test")
public class TestResponse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "response_id", nullable = false, unique = true)
	private int responseID;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "test_id")
	private Test test;
	
	@Column(name = "text")
	private String text;
	
	@Column(name="ts_created")
	private Instant tsCreated;
	
	@Column(name="ts_updated")
	private Instant tsUpdated;
	
	public static final String GET_BY_USER_AND_TEST_NQ = "RESPONSE.GET_BY_USER_AND_TEST";
	
}
