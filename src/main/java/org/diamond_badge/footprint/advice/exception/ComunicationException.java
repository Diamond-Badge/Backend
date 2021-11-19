package org.diamond_badge.footprint.advice.exception;

public class ComunicationException extends RuntimeException {
	public ComunicationException() {
		super();
	}

	public ComunicationException(String message) {
		super(message);
	}

	public ComunicationException(String message, Throwable cause) {
		super(message, cause);
	}
}
