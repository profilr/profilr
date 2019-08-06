package io.github.profilr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "Reasons")
@NamedQuery(name=Reason.SELECT_ALL_NQ, query="FROM Reason")
public class Reason {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reason_id", nullable = false, unique = true)
	private int reasonID;
	
	@Column(name = "text", nullable = false, unique = true)
	private String text;
	
	public static final String SELECT_ALL_NQ = "REASON.SELECT_ALL";

}
