package com.bridgelabz.fundoo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	int code;
	String msg;
	 public UserException(String msg)
	 {
		super(msg);
	 }
	 public UserException(int code, String msg)
	 {
		 super(msg);
		 this.code =code;
	 }
}
