package com.cst438.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.RoleRepository;
import com.cst438.domain.User;
import com.cst438.domain.UserDTO;
import com.cst438.service.PasswordEncoder;

@RestController
@CrossOrigin
public class RoleController {
	
	@Autowired
	RoleRepository roleRepository;
	
	PasswordEncoder encoder = new PasswordEncoder();
	
	
	/*
	 * Add user
	 */
	@PostMapping("/users")
	@Transactional
	public int addUser(@RequestBody UserDTO udto) {
		System.out.println("/users POST called.");
		User user = roleRepository.findByAlias(udto.alias());
		
		if(user != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists.");
		}else {
			user = new User();
			user.setAlias(udto.alias());
			
			String newPassword = encoder.encodedPassword(udto.password());
			user.setPassword(newPassword);
			user.setRole(udto.role());
			
			roleRepository.save(user);
		}

		return user.getUser_id();
	}
	
	/*
	 * Get all users
	 */
	@GetMapping("/users")
	public User[] getUsers() {
		System.out.println("/users called.");
		User[] users = roleRepository.getAllAliases();
		
		for(User user : users) {
			System.out.println(user.toString());
		}
		
		return users;
	}
	
	/*
	 * Get specific user
	 */
	@GetMapping("/user/{alias}")
	public User getUser(@PathVariable("alias") String username) {
		User user = roleRepository.findByAlias(username);
		
		if(user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user exists");
		}
		return user;
	}

	/*
	 * Update user
	 */
	@PutMapping("/users/update")
	@Transactional
	public void updateUser(@RequestBody UserDTO udto) {
		System.out.println("/users/update POST called.");
		User user = roleRepository.findByAlias(udto.alias());
		
		if(user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist.");
		}else {
			String newPassword = encoder.encodedPassword(udto.password());
			user.setPassword(newPassword);
			user.setRole(udto.role());
			
			roleRepository.save(user);
		}
	}

	/*
	 * Delete user
	 */
	@DeleteMapping("/users/delete")
	@Transactional
	public void deleteUser(@RequestBody UserDTO udto) {
		System.out.println("/users/delete POST called.");
		User user = roleRepository.findByAlias(udto.alias());
		
		if(user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist.");
		}else {
			roleRepository.delete(user);
		}
	}

}
