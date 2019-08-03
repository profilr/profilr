package io.github.profilr.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table( name = "Users" )
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="userID")
public class User {
	
	@Id
	@Column(name = "user_id", nullable = false, unique = true)
	private String userID;
	
	@Column(name = "email_address", nullable = false, unique = true)
	private String emailAddress;
	
	@Column(name = "given_name", nullable = false)
	private String givenName;
	
	@Column(name = "family_name", nullable = false)
	private String familyName;
	
	@Column(name = "course_admin_approved", nullable = false)
	private boolean courseAdminApproved;
	
	@ManyToMany(mappedBy = "admins", fetch = FetchType.EAGER)
	private Set<Course> administratedCourses = new HashSet<Course>();

	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private Set<Section> sectionsJoined = new HashSet<Section>();
	
	public String getFullName() {
		return getFamilyName() + ", " + getGivenName();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Answer> getResponsesForTest(Test t, EntityManager em) {
		org.hibernate.Session s = em.unwrap(org.hibernate.Session.class);
		
		Criteria c = s.createCriteria(Answer.class);
		c.add(Restrictions.eq("user", this));
		
		// I throw them into a set because the criteria query was giving me duplicates....
		Set<Answer> responses = new HashSet<Answer>();
		responses.addAll(c.list());
		
		for (Answer a : responses)
			if (a.getQuestion().getTest().getTestID() != t.getTestID())
				responses.remove(a);
		
		return responses.stream().collect(Collectors.toList());
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Answer> getResponsesForQuestion(Question q, EntityManager em) {
		List<Answer> responses = new ArrayList<Answer>();
		
		org.hibernate.Session s = em.unwrap(org.hibernate.Session.class);
		
		Criteria c = s.createCriteria(Answer.class);
		
		c.add(Restrictions.eq("user", this));
		c.add(Restrictions.eq("question", q));
		responses.addAll((Collection<? extends Answer>)c.list());
		
		return responses;
	}
	
	public boolean enrolledInCourse(Course c) {
		Set<Course> cs = this.getEnrolledCourses();
		for (Course s : cs)
			if (s.getCourseID() == c.getCourseID())
				return true;
		return false;
	}
	
	public Set<Course> getEnrolledCourses() {
		return getSectionsJoined().stream().map(s -> s.getCourse()).collect(Collectors.toSet());
	}
	
	public boolean isCourseAdmin(Course c) {
		for (Course course : this.getAdministratedCourses())
			if (c.getCourseID() == course.getCourseID())
				return true;
		return false;
	}
	
	public Section getSectionFromCourse(Course c) {
		for (Section s : getSectionsJoined())
			if (s.getCourse().getCourseID() == c.getCourseID())
				return s;
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("User: %s (%s)", getFullName(), getUserID());
	}
	
}
