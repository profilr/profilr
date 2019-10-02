package io.github.profilr.web.exceptions;

import lombok.Getter;

public class CharacterLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private final int maxLength;
	
	public CharacterLimitExceededException(int maxLength) {
		this.maxLength = maxLength;
	}

}
