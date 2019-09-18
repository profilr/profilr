package io.github.profilr.web.exceptions;

public class CharacterLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final int maxLength;
	
	public CharacterLimitExceededException(int maxLength) {
		this.maxLength = maxLength;
	}

}
