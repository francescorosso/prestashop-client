package it.frared.prestashop;

public class PrestashopServiceException extends RuntimeException {

	public PrestashopServiceException(String message) {
		super(message);
	}

	public PrestashopServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}