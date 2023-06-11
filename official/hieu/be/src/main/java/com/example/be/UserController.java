package com.example.be;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.GsonBuilder;


@RestController
@CrossOrigin
public class UserController {

	UserDAO userDAO = new UserDAO();
	
	User currentUser = null;
	
	@PostMapping("/login")
	public User login(HttpEntity<String> httpEntity) throws IOException {
	    String body = httpEntity.getBody();

	    User loginUser = new GsonBuilder().setLenient().create().fromJson(body, User.class);
	    
	    System.out.println(loginUser.toString() + " is login.");
	    
	    loginUser = userDAO.login(loginUser.getUsername(), loginUser.getPassword());

	    if(loginUser.getFullname().equals("unknown")) currentUser = loginUser;
	    
	    return loginUser;
	}
	
	@PostMapping("/sign")
	public User signUp(HttpEntity<String> httpEntity) throws IOException {
	    String body = httpEntity.getBody();

	    User registerUser = new GsonBuilder().setLenient().create().fromJson(body, User.class);
	    
	    System.out.println(registerUser.toString() + " is sign up.");
	    
	    boolean isSuccessfull = userDAO.signUp(registerUser.getUsername(), registerUser.getPassword(), registerUser.getFullname());
    	registerUser.setPassword("");
    	
	    if(isSuccessfull) {
	    	registerUser.setPosition("user");
	    	currentUser = registerUser;
	    }
	    else {
	    	registerUser.setFullname("unknown");
	    }
	    
	    return registerUser;
	}
	
	@GetMapping("/user-login")
	public User signUp() throws IOException {
	    
	    return currentUser == null ? new User() : currentUser;
	}
	
	@GetMapping("/logout")
	public User logOut() throws IOException {
		currentUser = null;
		return new User();
	}
}
