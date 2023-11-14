package com.cst438.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AddedCitiesRepository extends CrudRepository <City, Integer>{
	@Query(value="select * from city where city_id in (select city_id from added where user_id = :user_id)", nativeQuery = true)
	City[] getAddedCities(int user_id);
	
	@Modifying
	@Query(value="INSERT INTO added (user_id, city_id, name, temperature, max, min, icon) VALUES (:user_id, :city_id, :name, :temperature, :max_temperature, :min_temperature, :icon)", nativeQuery = true)
	void insertCity(int user_id, int city_id, String name, int temperature, int max_temperature, int min_temperature, String icon);

	@Modifying
	@Query(value="DELETE FROM added WHERE user_id = :user_id AND city_id = :city_id", nativeQuery = true)
	void deleteCity(int user_id, int city_id);

	@Modifying
	@Query(value="UPDATE added SET name = :name, temperature = :temperature, max = :max_temperature, min = :min_temperature, icon = :icon WHERE user_id = :user_id AND city_id = :city_id", nativeQuery = true)
	void updateCity(int user_id, int city_id, String name, int temperature, int max_temperature, int min_temperature, String icon);

}
