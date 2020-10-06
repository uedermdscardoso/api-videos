package dev.uedercardoso.apivideos.domain.exceptions;

public class MediaIsEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MediaIsEmptyException() {
		
	}
	
	public MediaIsEmptyException(String message) {
		super(message);
	}
}
