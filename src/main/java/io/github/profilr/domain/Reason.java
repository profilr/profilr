package io.github.profilr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import static lombok.AccessLevel.PRIVATE;;

@Data
@AllArgsConstructor(access = PRIVATE)
public class Reason {
	
	private static int counter = 1;
	private Reason(String reason) {
		this(counter++, reason);
	}

	private int value;
	private String text;
	
	public static final Reason[] REASONS = {
			new Reason("Calculation error"),
			new Reason("Significant digits or rounding"),
			new Reason("Silly mistake"),
			new Reason("Misunderstood question"),
			new Reason("Didn't understand topic"),
			new Reason("Didn't understand vocabulary"),
			new Reason("Forgot +C (oof)"),
			new Reason("Formatted answer incorrectly"),
			new Reason("Bubbling error"),
			new Reason("Other"),
	};
	
}
