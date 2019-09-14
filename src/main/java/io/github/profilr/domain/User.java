package io.github.profilr.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table( name = "Users" )
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="userID")
@NamedQuery(name = User.SELECT_BY_EMAIL_ADDRESS_NQ, query = "FROM User WHERE emailAddress = :emailAddress")
public class User implements Comparable<User> {
	
	@Id
	@Column(name = "user_id", nullable = false, unique = true)
	private String userID;
	
	@Column(name = "email_address", nullable = false, unique = true)
	private String emailAddress;
	
	@Column(name = "given_name", nullable = false)
	private String givenName;
	
	@Column(name = "family_name", nullable = false)
	private String familyName;
	
	@Column(name = "can_create_course", nullable = false)
	@Getter(AccessLevel.PRIVATE)
	private boolean canCreateCourse;
	
	@ManyToMany(mappedBy = "admins", fetch = FetchType.EAGER)
	@OrderBy
	private SortedSet<Course> administratedCourses = new TreeSet<Course>();

	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	@OrderBy
	private SortedSet<Section> sectionsJoined = new TreeSet<Section>();
	
	public String getFullName() {
		return getFamilyName() + ", " + getGivenName();
	}
	
	public List<Answer> getAnswersForTest(Test t, EntityManager em) {
		return em.createNamedQuery(Answer.GET_BY_USER_AND_TEST_NQ, Answer.class)
				 .setParameter("user", this)
				 .setParameter("test", t)
				 .getResultList();
	}
	
	public Optional<Answer> getAnswersForQuestion(Question q, EntityManager em) {
		return em.createNamedQuery(Answer.GET_BY_USER_AND_QUESTION_NQ, Answer.class)
				 .setParameter("user", this)
				 .setParameter("question", q)
				 .getResultStream()
				 .findFirst();
	}
	
	public Optional<TestResponse> getResponsesForTest(Test t, EntityManager em) {
		return em.createNamedQuery(TestResponse.GET_BY_USER_AND_TEST_NQ, TestResponse.class)
				.setParameter("user", this)
				.setParameter("test", t)
				.getResultStream()
				.findFirst();
	}
	
	public boolean enrolledInCourse(Course c) {
		Set<Course> cs = this.getEnrolledCourses();
		for (Course s : cs)
			if (s.getCourseID() == c.getCourseID())
				return true;
		return false;
	}
	
	public Set<Course> getEnrolledCourses() {
		return getSectionsJoined().stream()
								  .map(s -> s.getCourse())
								  .collect(Collectors.toSet());
	}
	
	public boolean isCourseAdmin(Course c) {
		if (c == null)
			return false;
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
	
	public boolean canCreateCourse() {
		return isCanCreateCourse();
	}
	
	@Override
	public String toString() {
		return String.format("User: %s (%s)", getFullName(), getUserID());
	}
	
	@Override
	public int compareTo(User u) {
		return getFullName().compareTo(u.getFullName());
	}
	
	public static final String SELECT_BY_EMAIL_ADDRESS_NQ = "USER.SELECT_BY_EMAIL_ADDRESS";
	
}
