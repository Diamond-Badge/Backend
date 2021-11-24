package org.diamond_badge.footprint.advice.exception;

public class NotExpiredTokenYetException extends RuntimeException {
	public NotExpiredTokenYetException() {
	}

	public NotExpiredTokenYetException(String message) {
		super(message);
	}
}
