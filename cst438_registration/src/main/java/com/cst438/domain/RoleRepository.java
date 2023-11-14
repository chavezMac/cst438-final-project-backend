package com.cst438.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository <User, Integer> {
	
	User findByAlias(String alias);
	
	User[] findByAliasStartsWith(String alias);
	
	@Query(value="select * from user", nativeQuery= true)
	User[] getAllAliases();
	
}
