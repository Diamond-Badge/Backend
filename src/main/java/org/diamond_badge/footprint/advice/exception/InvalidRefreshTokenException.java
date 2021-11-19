package org.diamond_badge.footprint.advice.exception;

public class InvalidRefreshTokenException extends RuntimeException{
	public InvalidRefreshTokenException() {
	}

	public InvalidRefreshTokenException(String message) {
		super(message);
	}
}
