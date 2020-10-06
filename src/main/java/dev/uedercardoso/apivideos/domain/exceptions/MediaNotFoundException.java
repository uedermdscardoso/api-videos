package dev.uedercardoso.apivideos.domain.exceptions;

public class MediaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MediaNotFoundException() {
		
	}
	
	public MediaNotFoundException(String message) {
		super(message);
	}
}
