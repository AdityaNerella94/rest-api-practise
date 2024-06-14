package com.appsdeveloperblog.app.ws.ui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import com.appsdeveloperblog.app.ws.userservice.UserService;
import com.appsdeveloperblog.app.ws.userservice.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController //it accepts the http request
@RequestMapping("users") //to map the request from frontend
public class UserController {
	
	Map<String, UserRest> users;
	
	@Autowired
	UserService userService;
	
	@GetMapping(path="/{userId}", 
			    produces = {
			    		   MediaType.APPLICATION_XML_VALUE, 
			    		   MediaType.APPLICATION_JSON_VALUE
			    		   })
	//This is for path variable. it is required to extract value from the path. This is also used to reach to particular method.
	public ResponseEntity<UserRest> getUser(@PathVariable String userId) //use this if you want to send the response status
	//status by your own. for that you need to use the ResponseEntity class
	{	
		//the below two lines are used just to implement the exception handling concept in our project.
		//String firstname = null;
		//int firstNameLength = firstname.length();
		
		//the below line is used to throw a custom exception.
		//if(true) throw new UserServiceException("A User Service Exception is thrown");
		
		if(users.containsKey(userId))
		{
			return new ResponseEntity<>(users.get(userId), HttpStatus.OK); //we are returning response body along with status so we used response entity
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping//This is for query param. These are not required to find the method. we will extract values in method params
	public String getUser(@RequestParam(value="page", required=false) Integer page, 
			//@RequestParam(value="page", required=false) int page, This will throw error as we didn't initialize primitive 
			//data type. primitives should be set to some value as we can't set NULL to them. So used Integer page as param.
			              @RequestParam(value="limit", defaultValue="50") int limit,
			              @RequestParam(value="sort", required=false) String sort
			//@RequestParam(value="sort", required=false) String sort1. this not primitive type. so NULL can be stored in it. 
			//if we try to access it without any value then only it will throw error.so we need to handle it with if condition in code.
			              )
	//we can make field as optional from client by two ways 
	//1.even if client doesn't give the value we can make defaultValue in the backend
	//2.by making the field as required=false. be careful if you use int and String in params.
	{		
		
		return "get users was called with page number is :"+page+" and limit is :"+limit+" and sorted :"+sort;
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	//@Valid is used to validate the fields in the request body for this particular class. these validations can be in model class at field level.
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails)
	{
	    UserRest returnValue = userService.createUser(userDetails);
		
		return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
		
	}

	@PutMapping(path="/{userId}", 
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails )
	{
		UserRest storedUserDetails = users.get(userId);
		storedUserDetails.setFirstName(userDetails.getFirstName());
		storedUserDetails.setLastName(userDetails.getLastName()); 
		
		users.put(userId, storedUserDetails);
		
		return storedUserDetails;
	}

	@DeleteMapping(path="/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id)
	{
		users.remove(id);
		return ResponseEntity.noContent().build();

	}

}
