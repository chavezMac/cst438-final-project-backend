package com.cst438.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
	
	public String encodedPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encrpted_password = encoder.encode(password);
		
		return encrpted_password;
	}
	 public static void main(String[] args) {
	     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	     String password = "admin";
	     String encrpted_password = encoder.encode(password);
	     System.out.println(encrpted_password);
	 }
	 
	 
}