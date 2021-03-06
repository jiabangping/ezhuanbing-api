package com.ezhuanbing.api.exception;

public class HttpStatus405Exception extends Exception{

	private static final long serialVersionUID = 1L;
	private ErrorMessage errorMessage;
	
	public HttpStatus405Exception(String messageTitle,String errorCode,
			String errorMessage,String errorDocument){
		this.errorMessage = new ErrorMessage(messageTitle, errorCode, 
				errorMessage, errorDocument);
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
}
