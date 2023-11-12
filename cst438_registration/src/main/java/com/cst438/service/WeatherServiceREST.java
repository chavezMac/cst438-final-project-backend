package com.cst438.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class WeatherServiceREST implements WeatherService{

	@Override
	public void addCity(String name, int temperature, int max_temperature, int min_temperatute, String icon) {
		// TODO Auto-generated method stub
		
	}

}
