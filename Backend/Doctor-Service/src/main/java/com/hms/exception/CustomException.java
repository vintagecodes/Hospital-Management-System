package com.hms.exception;

@SuppressWarnings("serial")
public class CustomException extends Exception {
	String message;
	public CustomException(String str) {
		message = str;
	}
	
	public String toString() {
		return ("An Exception Occured at Menu Service: "+message);
	}

}
