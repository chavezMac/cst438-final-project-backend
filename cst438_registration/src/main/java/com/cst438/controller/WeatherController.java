package com.cst438.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		System.out.println("/city/" + user_id + "called.");
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
	public void addCity(@RequestBody Map<String, String> requestPayload, String name, @PathVariable("user_id") int user_id) {
		System.out.println("/city POST called.");
		City city = cityRepository.findByName(requestPayload.get("name"));

		if (city == null) {
			// city does not exist in available cities, return error to user
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not found.");
		}
		else {
			addedCitiesRepository.insertCity(user_id, city.getCity_id(), city.getName(), city.getTemp(), city.getMax(), city.getMin(), city.getIcon());

		}
	}

	/*
	 * delete city from user list, need user_id
	 */
	@DeleteMapping("/city/{user_id}")
	@Transactional
	public void deleteCity(@RequestParam("name") String name, @PathVariable("user_id") int user_id) {
		System.out.println("/city DELETE called.");
		City city = cityRepository.findByName(name);

		if (city == null) {
			// city does not exist in available cities, return error to user
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not found.");
		}
		else {
			//Delete city from added table
			addedCitiesRepository.deleteCity(city.getCity_id(), user_id);

		}
	}

	/*
	 * Update city in user list, need user_id
	 */
	@PutMapping("/city/update/{user_id}")
	@Transactional
	public void updateCity(@RequestParam("name") String name, @RequestParam("city_id") int city_id, @PathVariable("user_id") int user_id) {
		System.out.println("/city/update POST called.");
		City city = cityRepository.findByName(name);

		if (city == null) {
			// city does not exist in available cities, return error to user
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not found.");
		}
		else {
			//Update city in added table
			City[] listOfUserCities = addedCitiesRepository.getAddedCities(user_id);
			boolean cityFound = false;
			
			for(int i = 0; i < listOfUserCities.length; i++) {
				if(listOfUserCities[i].getCity_id() == city_id) {
					cityFound = true;
					break;
				}
			}
			
			if(cityFound) {
				addedCitiesRepository.deleteCity(user_id, city_id);
			}
			
			addedCitiesRepository.insertCity(user_id, city.getCity_id(), city.getName(),city.getTemp(),city.getMax(),city.getMin(),city.getIcon());

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
