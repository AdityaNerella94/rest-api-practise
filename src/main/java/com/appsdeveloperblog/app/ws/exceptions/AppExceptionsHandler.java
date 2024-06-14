package com.appsdeveloperblog.app.ws.exceptions;

 
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;// used for custom response like what ever we want to send back to frontend.
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;// class which handles exceptions raised by spring MVC.

import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessage;

@ControllerAdvice // to annotation make this exception class listen all the exceptions that happen in the application. this is like centralized way of handling exceptions
public class AppExceptionsHandler extends ResponseEntityExceptionHandler  {
	    
	    @ExceptionHandler(value = {Exception.class})// These are the methods which handle the exceptions. annotations says this method will handle particular exception.
	    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {	    	
	    	    	
	    	String errorMessageDescription = ex.getLocalizedMessage();
	    	
	    	if(errorMessageDescription == null) errorMessageDescription = ex.toString();
	    	
	    	ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
	    	
	    	return new ResponseEntity<>(
	    			errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    //to handle a specific type of exception. so if null pointer occurs the above Exception is not at all called.
	    //the below mewthod handles 2 exceptions...null pointer and the custom exception
	    //if we want we can write many exceptions in the same method also.
	    @ExceptionHandler(value = { NullPointerException.class, UserServiceException.class})
	    public ResponseEntity<Object> handleSpecificExceptions(Exception ex, WebRequest request) {
	        
	    	String errorMessageDescription = ex.getLocalizedMessage();
	    	
	    	if(errorMessageDescription == null) errorMessageDescription = ex.toString();
	    	
	    	ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
	    	
	    	return new ResponseEntity<>(
	    			errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	    	
	    }

	    
	    
	  
	    
	
}
