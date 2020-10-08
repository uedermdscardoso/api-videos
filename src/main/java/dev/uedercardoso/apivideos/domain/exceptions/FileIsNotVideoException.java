package dev.uedercardoso.apivideos.domain.exceptions;

public class FileIsNotVideoException extends RuntimeException {
	
	private static final long serialVersionUID = 6935668941925408341L;

	public FileIsNotVideoException() {
		
	}
	
	public FileIsNotVideoException(String message) {
		super(message);
	}
	
}
