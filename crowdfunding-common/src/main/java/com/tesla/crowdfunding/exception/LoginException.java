package com.tesla.crowdfunding.exception;

//业务层事物回滚默认只对runtimeexception有效
public class LoginException extends RuntimeException {

	public LoginException() {
	}

	public LoginException(String message) {
		super(message);
	}

}
