package com.cst438.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends CrudRepository <City, Integer> {
	
	City findByName(String name);
	
	City[] findByNameStartsWith(String name);

	@Query(value="select * from city", nativeQuery = true)
	City[] getAllCities();
	
	@Modifying
	@Query(value="INSERT INTO city (city_id, name, temperature, max, min, icon) VALUES (:city_id, :name, :temperature, :max_temperature, :min_temperature, :icon)", nativeQuery = true)
	void addCity(int city_id, String name, int temperature, int max_temperature, int min_temperature, String icon);
	
	//Delete city by name
	@Modifying
	@Query(value="DELETE FROM city WHERE name = :name", nativeQuery = true)
	void deleteCity(@Param("name") String name);

}
