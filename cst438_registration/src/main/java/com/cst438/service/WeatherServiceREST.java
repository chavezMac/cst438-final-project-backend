package com.cst438.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.controller.WeatherController;
import com.cst438.domain.City;
import com.cst438.domain.CityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
@RestController
public class WeatherServiceREST implements WeatherService{
	private final String API_KEY = "PUT_API_KEY";
	
	@Autowired
	CityRepository cityRepository;
	
	@GetMapping(value="/admin/addCity")
	public void addCityToUserList(@RequestParam("name") String cityName) {
		String tempCity = cityName;
		
		String url = "https://api.openweathermap.org/data/2.5/weather?q="+ tempCity +"&units=imperial&appid=" + API_KEY;
		RestTemplate restTemplate = new RestTemplate();

		String city = restTemplate.getForObject(url, String.class);
		
		double temperature = 0.0;
		double max = 0.0;
		double min = 0.0;
		String newIcon = "";
		
		if(city == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid coordinates!!");
		}
		
		System.out.println(city);
		StringTokenizer st = new StringTokenizer(city, ",");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			if(token.contains("temp")) {
				temperature = Double.parseDouble(token.substring(token.lastIndexOf(":") + 1));
			}
			if(token.contains("temp_min")) {
				min = Double.parseDouble(token.substring(token.lastIndexOf(":") + 1));
			}
			if(token.contains("temp_max")) {
				max = Double.parseDouble(token.substring(token.lastIndexOf(":") + 1));
			}
			if(token.contains("icon")) {
				newIcon = token.substring(token.lastIndexOf(":") + 1);
			}
			
			
			
		}
		
 		int newTemp = (int)Math.round(temperature);
 		int newMaxTemp = (int)(max);
 		int newMinTemp = (int)min;
 		newIcon = newIcon.substring(1,4);

 		System.out.println("City: " + tempCity);
 		System.out.println("Temperature: " + newTemp);
 		System.out.println("Max: " + newMaxTemp);
 		System.out.println("Min: " + newMinTemp);
 		System.out.println("Icon: " + newIcon);
	
		addCity(tempCity, newTemp, newMaxTemp, newMinTemp, newIcon);
	}
	


	@Override
	public void addCity(String name, int temperature, int max_temperature, int min_temperature, String icon) {
		// TODO Auto-generated method stub
		City temp = cityRepository.findByName(name);
		
		if(temp != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City already exists.");
		}else {
			temp = new City();
			temp.setName(name);
			temp.setTemp(temperature);
			temp.setMax(max_temperature);
			temp.setMin(min_temperature);
			temp.setIcon(icon);
			
			cityRepository.save(temp);
		}
		
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T  fromJsonString(String str, Class<T> valueType ) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

}
