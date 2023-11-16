package com.cst438.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends CrudRepository <User, Integer> {
	
	User findByAlias(String alias);
	
	User[] findByAliasStartsWith(String alias);
	
	@Query(value="select * from user", nativeQuery= true)
	User[] getAllAliases();
	
	@Query(value="select * from user where alias = :username", nativeQuery= true)
	User findByUsername(@Param("username") String username);
}
