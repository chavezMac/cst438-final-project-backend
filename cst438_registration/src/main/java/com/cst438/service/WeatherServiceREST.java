package com.cst438.service;

import java.awt.datatransfer.SystemFlavorMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	private final String API_KEY = "9dfcf3d38c22678d0271e9cd75713ef6";
	
	@Autowired
	CityRepository cityRepository;
	
	public Map<String, String> weatherDayIcons = new HashMap<String, String>() {{
		put("01d.png", "clear sky");
		put("02d.png", "few clouds");
		put("03d.png", "scattered clouds");
		put("04d.png", "broken clouds");
		put("09d.png", "shower rain");
		put("10d.png", "rain");
		put("11d.png", "thunderstorm");
		put("13d.png", "snow");
		put("50d.png", "mist");
	}};
	
	public Map<String, String> weatherNightIcons = new HashMap<String, String>() {{
		put("01n.png", "clear sky");
		put("02n.png", "few clouds");
		put("03n.png", "scattered clouds");
		put("04n.png", "broken clouds");
		put("09n.png", "shower rain");
		put("10n.png", "rain");
		put("11n.png", "thunderstorm");
		put("13n.png", "snow");
		put("50n.png", "mist");
	}};
	
	@GetMapping(value="/admin/addCity")
	public City addCityToUserList(@RequestParam("name") String cityName) {
		String tempCity = cityName;
		City myCity = null;
		
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
 		newIcon = newIcon + ".png";

 		System.out.println("City: " + tempCity);
 		System.out.println("Temperature: " + newTemp);
 		System.out.println("Max: " + newMaxTemp);
 		System.out.println("Min: " + newMinTemp);
 		System.out.println("Icon: " + newIcon);
 		
 		String weather = "";
 		
 		
 		System.out.println(newIcon.charAt(2));
 		if(newIcon.charAt(2) == 'd') {
			weather = weatherDayIcons.get(newIcon);
		}else {
			weather = weatherNightIcons.get(newIcon);
		}
 		System.out.println(weather);
 		
		addCity(tempCity, newTemp, newMaxTemp, newMinTemp, weather);
		
		myCity = cityRepository.findByName(tempCity);
		
		return myCity; 
	}
	
	@GetMapping(value="/admin/update")
	public void updateCity(@RequestParam("name") String cityName) {
		City cityToCompare = cityRepository.findByName(cityName);
		
		if(cityToCompare != null) {
			
			String url = "https://api.openweathermap.org/data/2.5/weather?q="+ cityName +"&units=imperial&appid=" + API_KEY;
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
	 		
//	 		if(cityToCompare.getTemp() != newTemp) {
//	 			cityToCompare.setTemp(newTemp);
//	 		}
//	 		
//	 		if(cityToCompare.getMax() != newMaxTemp) {
//	 			cityToCompare.setMax(newMaxTemp);
//	 		}
//	 		
//	 		if(cityToCompare.getMin() != newMinTemp) {
//	 			cityToCompare.setMin(newMinTemp);
//	 		}
//	 		
//	 		if(!cityToCompare.getIcon().equals(newIcon)) {
//	 			cityToCompare.setIcon(newIcon);
//	 		}
	 		
	 		newIcon = newIcon + ".png";
	 		String weather = "";
	 		
	 		System.out.println(newIcon.charAt(2));
	 		if(newIcon.charAt(2) == 'd') {
				weather = weatherDayIcons.get(newIcon);
			}else {
				weather = weatherNightIcons.get(newIcon);
			}
	 		System.out.println(weather);
	 		
			updateCity(cityName, newTemp, newMaxTemp, newMinTemp, weather);
	 		
	 		
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City does not exists.");
		}
 		
		
	}

	@GetMapping(value="/admin/updateCities")
	public void updateCityInfo(String name) {
		System.out.println("Updating " + name);
		City temp = cityRepository.findByName(name);
		
		if(temp == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City doesn't exist. Cannot update.");
		}
		
		String url = "https://api.openweathermap.org/data/2.5/weather?q="+ temp.getName() +"&units=imperial&appid=" + API_KEY;
		RestTemplate restTemplate = new RestTemplate();

		String city = restTemplate.getForObject(url, String.class);
		
		double temperature = 0.0;
		double max = 0.0;
		double min = 0.0;
		String newIcon = "";
		
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
 		
 		if(temp.getTemp() != newTemp) {
 			temp.setTemp(newTemp);
 		}
 		
 		if(temp.getMax() != newMaxTemp) {
 			temp.setMax(newMaxTemp);
 		}
 		
 		if(temp.getMin() != newMinTemp) {
 			temp.setMin(newMinTemp);
 		}
 		
 		if(!(temp.getIcon().equals(newIcon))) {
 			temp.setIcon(newIcon);
 		}
		
 		cityRepository.save(temp);
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
	
	public void updateCity(String name,int temperature, int max_temperature, int min_temperature, String icon) {
		City cityToChange = cityRepository.findByName(name);
		
		if(cityToChange == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City does not exists.");
		}else {
			cityToChange.setName(name);
			cityToChange.setTemp(temperature);
			cityToChange.setMax(max_temperature);
			cityToChange.setMin(min_temperature);
			cityToChange.setIcon(icon);
			
			cityRepository.save(cityToChange);
		}
	}
	
	@DeleteMapping(value="/admin/delete") 
	@Transactional
	public void deleteCityFromList(@RequestParam("name") String cityName) {
		System.out.println("Delete called");
		City temp = cityRepository.findByName(cityName);
		
		if(temp == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City doesn't exist. Cannot delete.");
		}
		
		cityRepository.deleteCity(cityName);
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
