package com.appsdeveloperblog.app.ws.shared;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service // it be annotated with @Service as we are autowiring it in serviceImpl class
public class Utils {
	
	public String generateUserId() {
		return UUID.randomUUID().toString();
	}

}
