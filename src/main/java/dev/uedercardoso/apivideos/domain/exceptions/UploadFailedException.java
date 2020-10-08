package dev.uedercardoso.apivideos.domain.exceptions;

public class UploadFailedException extends RuntimeException {

	private static final long serialVersionUID = 5583001308031656514L;

	public UploadFailedException() {
		
	}
	
	public UploadFailedException(String message) {
		super(message);
	}

}
