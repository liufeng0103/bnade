package com.bnade.wow.client;

public class ClientException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ClientException() {
		super();
	}

	public ClientException(Throwable cause) {
		super(cause);
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientException(String message) {
		super(message);
	}
	
}
