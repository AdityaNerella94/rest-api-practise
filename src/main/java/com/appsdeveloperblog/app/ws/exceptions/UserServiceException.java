package com.appsdeveloperblog.app.ws.exceptions;

public class UserServiceException extends RuntimeException{
	
	/**
	 * 
	 */
	//This like creating a custome/user-defined exception.
	private static final long serialVersionUID = 1348771109171435607L;

	public UserServiceException(String message)
	{
		super(message);
	}

}
