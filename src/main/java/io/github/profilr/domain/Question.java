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

	/**
	 * <p>
	 * Compares this question with the provided question for order.  Returns a
	 * negative integer, zero, or a positive integer if this question goes before,
	 * is the same as, or goes after the provided question, respectively (in
	 * accordance with the standard {@link Comparable#compareTo(Object)} contract).
	 * </p>
	 * 
	 * <p>
	 * Questions are ordered <strong>not by lexicographic order,</strong> but in the order
	 * in which they would appear on a real test. For instance, <i>10</i> would clearly
	 * appear after <i>2</i> on a test, but this is not true lexicographically. In addition,
	 * sub-questions (both <i>1a</i>, <i>1b</i>, <i>1c</i>, <i>...</i> as well as
	 * <i>1.1</i>, <i>1.3</i>, <i>1.3</i>, <i>...</i> are valid ways to represent sub-questions)
	 * are placed in their proper positions. Sub-questions are placed below their parent questions.
	 * </p>
	 * 
	 * <p>
	 * If two questions' labels are identical, then their question IDs are compared. This is guaranteed
	 * to be unique unless both questions are in fact the same entity, in which case it makes sense to return 0.
	 * </p>
	 * 
	 * @implNote This method is implemented by splitting the question labels
	 * into segments by non-word characters or the boundary between a letter
	 * and number (implemented via RegEx lookarounds). Once the labels
	 * are split into segments, each pair of segments is compared until a pair
	 * that differs is found. The two segments are compared numerically if both
	 * are valid numbers, or else they are compared lexicographically. If no
	 * pair differs, and the end of one array is reached, the shorter array
	 * is chosen as the earlier question. If both arrays are the same size (all
	 * segments will be equal if it reaches here), then question IDs are compared.
	 * 
	 */
	@Override
	public int compareTo(Question that) {
		// Split both questions' labels via the regex below.
		String[] aSplit = this.getLabel().split(COMPARE_SPLIT_REGEX),
				 bSplit = that.getLabel().split(COMPARE_SPLIT_REGEX);
		// Loop over the smaller of both arrays' length
		for (int i = 0; i < Math.min(aSplit.length, bSplit.length); i++) {
			// A and B are the two current segments
			String a = aSplit[i],
				   b = bSplit[i];
			if (a.equals(b))
				// The two segments are the same, continue to the next pair
				continue;
			try {
				// Try to convert both to integers
				int n = Integer.parseInt(a),
					m = Integer.parseInt(b);
				// Compare the numbers and return
				return n - m;
			} catch (NumberFormatException e) {
				// Can not convert both to integers
				// Compare as strings and return
				return a.compareTo(b);
			}
		}
		// The smaller array is the earlier question
		int r = aSplit.length - bSplit.length;
		// If both arrays are the same size, compare question IDs.
		return r != 0 ? r : this.getQuestionID() - that.getQuestionID();
	}
	
	/**
	 * Split on either a non-word character (anything but a letter or digit)
	 * or the boundary between a letter and number (or vice versa) using lookarounds
	 */
	private static final String COMPARE_SPLIT_REGEX = "\\W+|(?<=[a-zA-Z])(?=\\d)|(?<=\\d)(?=[a-zA-Z])";
	
}
