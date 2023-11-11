package com.cst438.domain;

import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository <City, Integer> {
	
	City findByName(String name);
	
	City[] findByNameStartsWith(String name);

}