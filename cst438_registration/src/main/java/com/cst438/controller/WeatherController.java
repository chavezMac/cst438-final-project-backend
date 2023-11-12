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

import com.cst438.domain.AddedCitiesRepository;
import com.cst438.domain.City;
import com.cst438.domain.CityRepository;

@RestController
@CrossOrigin 
public class WeatherController {
	
	@Autowired
	CityRepository cityRepository;
	

	@Autowired
	AddedCitiesRepository addedCitiesRepository;
	
	/*
	 * get list of cities for that match user_id in added table.
	 */
	@GetMapping("/city/{user_id}")
	public City[] getAddedCities(@PathVariable("user_id") int user_id) {
		System.out.println("/city/{user_id} called.");
		City[] cities = addedCitiesRepository.getAddedCities(user_id);
		
		//Print out the cities
		for (City city : cities) {
			System.out.println(city.toString());
		}
		
		return cities;
	}
	
	
	/*
	 * add city to user list, need user_id
	 */
	@PostMapping("/city/{user_id}")
	@Transactional
	public void addCity(@RequestParam("name") String name, @PathVariable("user_id") int user_id) {
		System.out.println("/city POST called.");
		City city = cityRepository.findByName(name);

		if (city == null) {
			// city does not exist in available cities, return error to user
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not found.");
		}
		else {
			//Add city to added table
			addedCitiesRepository.insertCity(city.getCity_id(), user_id, city.getName(), city.getTemp(), city.getMax(), city.getMin(), city.getIcon());

		}
	}

	/*
	 * Get list of all cities
	 */
	@GetMapping("/city")
	public City[] getCities( ) {
		System.out.println("/city called.");
		City[] cities = cityRepository.getAllCities();
		
		//Print out the cities
		for (City city : cities) {
			System.out.println(city.toString());
		}
		
		return cities;
	}
	
}
