package com.cst438.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.City;
import com.cst438.domain.CityRepository;

@RestController
@CrossOrigin 
public class WeatherController {
	
	@Autowired
	CityRepository cityRepository;
	
	
	/*
	 * get list of cities for user.
	 */
	@GetMapping("/city")
	public City[] getCities( ) {
		System.out.println("/city called.");
		City[] cities = null;
		
		return cities;
	}
	
	
}
